package com.unq.edu.li.pdesa.mentiUnq.services;

import com.unq.edu.li.pdesa.mentiUnq.models.MentiUser;
import com.unq.edu.li.pdesa.mentiUnq.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest
{
	@InjectMocks
	private CustomUserDetailsService customUserDetailsService;

	@Mock
	private UserRepository userRepository;

	private String username;
	private String password;

	@BeforeEach
	public void setUp(){
		username = "username";
		password = "password";
	}
	@Test
	public void whenLoadUserByUsernameAndRepositoryReturnsNullClientThenRaiseUsernameNotFoundException(){
		String expectedMessage = "User " + username + " not found";

		when(userRepository.getUserByUsername(username)).thenReturn(null);

		UsernameNotFoundException exception = assertThrows(
				UsernameNotFoundException.class,
				()-> customUserDetailsService.loadUserByUsername(username),
				expectedMessage);

		assertNotNull(exception);
		assertEquals(expectedMessage, exception.getMessage());
		verify(userRepository).getUserByUsername(username);
	}

	@Test
	public void whenLoadUserByUsernameAndRepositoryReturnsNotNullClientThenReturnAValidUserDetails(){

		MentiUser user = mock(MentiUser.class);

		when(userRepository.getUserByUsername(anyString())).thenReturn(user);
		when(user.getPassword()).thenReturn(password);

		UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

		assertNotNull(userDetails);
		assertEquals(username, userDetails.getUsername());
		assertEquals(password, userDetails.getPassword());
		assertFalse(userDetails.getAuthorities().isEmpty());
		assertTrue(userDetails.getAuthorities().size() == 1);
		assertTrue(userDetails.getAuthorities().toString().equals("[USER]"));
		verify(userRepository).getUserByUsername(anyString());
	}
}
