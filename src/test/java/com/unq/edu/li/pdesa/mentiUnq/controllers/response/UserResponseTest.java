package com.unq.edu.li.pdesa.mentiUnq.controllers.response;

import com.unq.edu.li.pdesa.mentiUnq.controllers.request.OAuthRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserResponseTest {
    private UserResponse userResponse;
    private String access_token;

    @BeforeEach
    public void setUp(){
        userResponse = new UserResponse();
        access_token = "ACCESS_TOKEN";
    }

    @Test
    public void testGettersAndSetters() {
        assertNotNull(userResponse);
        assertNull(userResponse.getAccessToken());
        userResponse.setAccessToken(access_token);
        assertNotNull(userResponse.getAccessToken());
        assertEquals(access_token, userResponse.getAccessToken());
        assertNull(userResponse.getId());
    }

    @Test
    public void testAllArgs() {
        userResponse = new UserResponse(1l, access_token);

        assertNotNull(userResponse);
        assertEquals(access_token, userResponse.getAccessToken());
        assertEquals(1l, userResponse.getId());
    }
}
