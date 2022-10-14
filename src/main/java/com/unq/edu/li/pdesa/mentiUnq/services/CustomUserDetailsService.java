package com.unq.edu.li.pdesa.mentiUnq.services;

import com.unq.edu.li.pdesa.mentiUnq.models.MentiUser;
import com.unq.edu.li.pdesa.mentiUnq.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
public class CustomUserDetailsService implements UserDetailsService
{
	public static final String USER = "USER";
	public static final String ADMIN = "ADMIN";
	private final UserRepository userRepository;

	@Autowired
	public CustomUserDetailsService(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(final String username) {
		final MentiUser client = userRepository.getUserByUsername(username);

		if (client == null)
		{
			throw new UsernameNotFoundException("User " + username + " not found");
		}
		return new User(username, client.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(USER)));
	}

}
