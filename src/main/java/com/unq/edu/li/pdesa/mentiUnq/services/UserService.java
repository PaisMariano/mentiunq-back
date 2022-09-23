package com.unq.edu.li.pdesa.mentiUnq.services;

import com.unq.edu.li.pdesa.mentiUnq.auths.CustomAuthenticationManager;
import com.unq.edu.li.pdesa.mentiUnq.controllers.request.OAuthRequest;
import com.unq.edu.li.pdesa.mentiUnq.controllers.response.OAuthResponse;
import com.unq.edu.li.pdesa.mentiUnq.exceptions.UnauthorizedException;
import com.unq.edu.li.pdesa.mentiUnq.models.LoginProvider;
import com.unq.edu.li.pdesa.mentiUnq.models.MentiUser;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.protocols.Status;
import com.unq.edu.li.pdesa.mentiUnq.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationManager authenticationManager;
    private final JwtTokenService tokenService;

    public UserService(UserRepository userRepository, CustomAuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService, JwtTokenService tokenService)
    {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.tokenService = tokenService;
    }

    public MentiUser processOAuthPostLogin(String username, String password) {
        MentiUser user = userRepository.getUserByUsername(username);

        if (user == null) {
            MentiUser newUser = new MentiUser();
            newUser.setUserName(username);
            newUser.setProvider(LoginProvider.GOOGLE);
            newUser.setEnabled(true);
            newUser.setPassword(password);

            user = userRepository.save(newUser);
        }

        return user;
    }

    public ResponseUnit authenticate(OAuthRequest authenticationRequest) throws UnauthorizedException
    {
        MentiUser user = processOAuthPostLogin(authenticationRequest.getEmail(), authenticationRequest.getIdToken());

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUserName(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("USER"))));
        } catch (final BadCredentialsException ex) {
            throw UnauthorizedException.createWith(HttpStatus.UNAUTHORIZED.toString());
        }

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        return new ResponseUnit(Status.SUCCESS, "", OAuthResponse.builder()
                .accessToken(tokenService.generateToken(userDetails))
                .build());
    }
}
