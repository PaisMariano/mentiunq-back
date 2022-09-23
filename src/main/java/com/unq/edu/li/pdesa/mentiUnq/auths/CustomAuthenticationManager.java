package com.unq.edu.li.pdesa.mentiUnq.auths;

import com.unq.edu.li.pdesa.mentiUnq.services.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager
{
	private CustomUserDetailsService userService;

	public CustomAuthenticationManager( CustomUserDetailsService userService)
	{
		this.userService = userService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{
		String userName = authentication.getName();
		//String password = authentication.getCredentials().toString();

		userService.loadUserByUsername(userName);

		return authentication;
	}
}
