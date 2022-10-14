package com.unq.edu.li.pdesa.mentiUnq.configs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class CustomPermissionEvaluatorTest
{

	private CustomPermissionEvaluator customPermissionEvaluator;
	private String username;
	private String password;

	@BeforeEach
	public void setUp(){
		customPermissionEvaluator = new CustomPermissionEvaluator();
		username = "username";
		password = "password";
	}

	@Test
	public void hasPermissionWithNullAuthentication()
	{
		boolean response = customPermissionEvaluator.hasPermission(null, "PRIVILEGE", "SCOPE");

		assertFalse(response);
	}

	@Test
	public void hasPermissionWithAuthenticationAndIntegerPrivilege()
	{
		boolean response = customPermissionEvaluator.hasPermission(mock(Authentication.class), new Integer(1), "SCOPE");

		assertFalse(response);
	}

	@Test
	public void hasPermissionWithAuthenticationAndIntegerScope()
	{
		boolean response = customPermissionEvaluator.hasPermission(mock(Authentication.class), "PRIVILEGE", new Integer(1));

		assertFalse(response);
	}

	@Test
	public void hasPermissionByAuthenticationAndSerializableAndStringAndObject()
	{
		boolean response = customPermissionEvaluator.hasPermission(null, null, "scope", null);

		assertFalse(response);
	}

	@Test
	public void hasPermissionAndAuthenticationIsUsernamePasswordAuthenticationTokenAndHasPrivilegeNotRequiredReturnsFalse(){
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				username, password, Collections.singletonList(new SimpleGrantedAuthority("USER")));

		boolean response = customPermissionEvaluator.hasPermission(authentication, "privilege", "scope");

		assertFalse(response);
	}

	@Test
	public void hasPermissionAndAuthenticationIsUsernamePasswordAuthenticationTokenHasPrivilegeRequiredReturnsFalse(){
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				username, password, Collections.singletonList(new SimpleGrantedAuthority("USER")));

		boolean response = customPermissionEvaluator.hasPermission(authentication, "USER", "scope");

		assertTrue(response);
	}

}
