package com.unq.edu.li.pdesa.mentiUnq.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtTokenServiceTest
{
	private JwtTokenService jwtTokenService;

	@BeforeEach
	public void setUp(){
		jwtTokenService = new JwtTokenService();
	}

	@Test
	public void whenGenerateTokenReturnsStringWithValidJwtToken(){
		UserDetails userDetails = mock(User.class);

		when(userDetails.getUsername()).thenReturn("username");

		String token = jwtTokenService.generateToken(userDetails);

		DecodedJWT decodedJWT = JWT.decode(token);

		assertNotNull(token);
		assertNotNull(decodedJWT);
		assertEquals("HS256", decodedJWT.getAlgorithm());
		assertEquals("username", decodedJWT.getClaim("email").asString());
		assertEquals(null, decodedJWT.getClaim("authorities").asString());
	}
}
