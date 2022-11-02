package com.unq.edu.li.pdesa.mentiUnq.controllers.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class OAuthRequestTest {
    private OAuthRequest authRequest;

    @BeforeEach
    public void setUp(){
        authRequest = new OAuthRequest();
    }

    @Test
    public void testGetters(){
        assertNotNull(authRequest);
        assertNull(authRequest.getEmail());
        assertNull(authRequest.getIdToken());
    }
}
