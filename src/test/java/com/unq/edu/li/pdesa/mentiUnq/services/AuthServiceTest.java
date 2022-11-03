package com.unq.edu.li.pdesa.mentiUnq.services;

import com.unq.edu.li.pdesa.mentiUnq.configs.AuthorizationServerConfig;
import com.unq.edu.li.pdesa.mentiUnq.controllers.request.OAuthRequest;
import com.unq.edu.li.pdesa.mentiUnq.exceptions.UnauthorizedException;
import com.unq.edu.li.pdesa.mentiUnq.models.MailingWhiteList;
import com.unq.edu.li.pdesa.mentiUnq.models.MentiUser;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.protocols.Status;
import com.unq.edu.li.pdesa.mentiUnq.repositories.MailingRepository;
import com.unq.edu.li.pdesa.mentiUnq.repositories.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest
{
	@InjectMocks
	private AuthService authService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private CustomUserDetailsService customUserDetailsService;

	@Mock
	private AuthorizationServerConfig authenticationManager;

	@Mock
	private JwtTokenService tokenService;

	@Mock
	private MailingRepository mailingRepository;

	@Mock
	MentiUser mockMentiUser;

	private String username;
	private String password;
	private String email;

	@BeforeEach
	public void setUp(){
		this.username = "username";
		this.password = "password";
		this.email = "test@gmail.com";
	}

	@Test
	public void whenTriesToProcessOAuthPostLoginAndUserRepositoryReturnsNullUserRepositorySaveOnceAndReturnsUser()
	{

		when(userRepository.getUserByUsername(username)).thenReturn(null);
		when(userRepository.save(any(MentiUser.class))).thenReturn(mockMentiUser);

		MentiUser mentiUser = authService.processOAuthPostLogin(username, password);

		assertNotNull(mentiUser);
		verify(userRepository).getUserByUsername(username);
		verify(userRepository).save(any(MentiUser.class));
	}

	@Test
	public void whenTriesToAuthenticateAndRequestHasNotAnEmailThrowUnauthorizedException() throws UnauthorizedException
	{
		String expectedMessage = "No se encuentra el mail registrado en la whitelist.";
		OAuthRequest request = mock(OAuthRequest.class);

		UnauthorizedException exception = assertThrows(
				UnauthorizedException.class,
				()-> authService.authenticate(request),
				expectedMessage);

		assertNotNull(exception);
		assertEquals(expectedMessage, exception.getMessage());
		verify(mailingRepository).getMailingWhiteListByEmail(any());
	}

	@Test
	public void whenTriesToAuthenticateAndRequestHasAnEmailAndManagerReturnsBadCredentialsExceptionThenServiceThrowUnauthorizedException()
	{
		String expectedMessage = "401 UNAUTHORIZED";
		OAuthRequest request = mock(OAuthRequest.class);
		MailingWhiteList mailingWhiteList = mock(MailingWhiteList.class);
		Optional<MailingWhiteList> optionalMailingWhiteList = Optional.of(mailingWhiteList);

		when(request.getEmail()).thenReturn(username);
		when(mailingRepository.getMailingWhiteListByEmail(anyString())).thenReturn(optionalMailingWhiteList);
		when(userRepository.getUserByUsername(username)).thenReturn(mockMentiUser);
		when(mockMentiUser.getUserName()).thenReturn(username);
		when(authenticationManager.authenticate(any(Authentication.class))).thenThrow(BadCredentialsException.class);


		UnauthorizedException exception = assertThrows(
				UnauthorizedException.class,
				()-> authService.authenticate(request),
				expectedMessage);

		assertNotNull(exception);
		assertEquals(expectedMessage, exception.getMessage());
		verify(mailingRepository).getMailingWhiteListByEmail(any());
		verify(userRepository).getUserByUsername(username);
		verify(authenticationManager).authenticate(any(Authentication.class));
	}

	@Test
	public void whenTriesToAuthenticateAndRequestHasAnEmailAndManagerReturnsValidUserThenServiceReturnAValidResponseUnit() throws UnauthorizedException
	{

		OAuthRequest request = mock(OAuthRequest.class);
		MailingWhiteList mailingWhiteList = mock(MailingWhiteList.class);
		Optional<MailingWhiteList> optionalMailingWhiteList = Optional.of(mailingWhiteList);
		Authentication authentication = mock(UsernamePasswordAuthenticationToken.class);
		UserDetails userDetails = mock(User.class);
		String expectedToken = "TOKEN";

		when(request.getEmail()).thenReturn(username);
		when(mailingRepository.getMailingWhiteListByEmail(anyString())).thenReturn(optionalMailingWhiteList);
		when(userRepository.getUserByUsername(username)).thenReturn(mockMentiUser);
		when(mockMentiUser.getUserName()).thenReturn(username);
		when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
		when(customUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
		when(tokenService.generateToken(any(UserDetails.class))).thenReturn(expectedToken);

		ResponseUnit responseUnit = authService.authenticate(request);

		assertNotNull(responseUnit);
		assertEquals(Status.SUCCESS, responseUnit.getStatus());
		assertEquals(Strings.EMPTY, responseUnit.getMessage());
		assertEquals("{\"id\":0,\"accessToken\":\"TOKEN\"}", responseUnit.getPayload());
		verify(mailingRepository).getMailingWhiteListByEmail(any());
		verify(userRepository).getUserByUsername(username);
		verify(authenticationManager).authenticate(any(Authentication.class));
		verify(tokenService).generateToken(any(UserDetails.class));
	}

	@Test
	public void whenCreateWhiteListEmailWithValidEmailThenMailingRepositoryWasCalledOnce(){

		authService.createWhiteListEmail(email);

		verify(mailingRepository).save(any(MailingWhiteList.class));
	}
}
