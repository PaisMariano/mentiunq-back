package com.unq.edu.li.pdesa.mentiUnq.services;

import com.unq.edu.li.pdesa.mentiUnq.configs.AuthorizationServerConfig;
import com.unq.edu.li.pdesa.mentiUnq.controllers.request.OAuthRequest;
import com.unq.edu.li.pdesa.mentiUnq.controllers.response.UserResponse;
import com.unq.edu.li.pdesa.mentiUnq.exceptions.UnauthorizedException;
import com.unq.edu.li.pdesa.mentiUnq.models.LoginProvider;
import com.unq.edu.li.pdesa.mentiUnq.models.MailingWhiteList;
import com.unq.edu.li.pdesa.mentiUnq.models.MentiUser;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.protocols.Status;
import com.unq.edu.li.pdesa.mentiUnq.repositories.MailingRepository;
import com.unq.edu.li.pdesa.mentiUnq.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthorizationServerConfig authenticationManager;
    private final JwtTokenService tokenService;
    private final MailingRepository mailingRepository;

    public AuthService(UserRepository userRepository,
                       AuthorizationServerConfig authenticationManager,
                       CustomUserDetailsService customUserDetailsService,
                       JwtTokenService tokenService,
                       MailingRepository mailingRepository)
    {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.tokenService = tokenService;
        this.mailingRepository = mailingRepository;
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
        authenticateWhiteListEmail(authenticationRequest.getEmail());
        MentiUser user = processOAuthPostLogin(authenticationRequest.getEmail(), authenticationRequest.getIdToken());

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUserName(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("USER"))));
        } catch (final BadCredentialsException ex) {
            throw UnauthorizedException.createWith(HttpStatus.UNAUTHORIZED.toString());
        }

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        return new ResponseUnit(
                Status.SUCCESS,
                "",
                new UserResponse(
                        user.getId(),
                        tokenService.generateToken(userDetails)
                )
        );
    }

    public void createWhiteListEmail(String email) {
        MailingWhiteList mailingWhiteList = new MailingWhiteList(email);
        mailingRepository.save(mailingWhiteList);
    }

    private void authenticateWhiteListEmail(String email) throws UnauthorizedException {
        mailingRepository.getMailingWhiteListByEmail(email).orElseThrow(
                () -> UnauthorizedException.createWith("No se encuentra el mail registrado en la whitelist.")
        );
    }


}
