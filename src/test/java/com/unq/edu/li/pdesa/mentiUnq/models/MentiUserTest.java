package com.unq.edu.li.pdesa.mentiUnq.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class MentiUserTest {
    private MentiUser mentiUser;

    @BeforeEach
    public void setUp(){
        mentiUser = new MentiUser();
    }

    @Test
    public void testGetters(){
        assertNotNull(mentiUser);
        assertNull(mentiUser.getEnabled());
        assertNull(mentiUser.getUserName());
        assertNull(mentiUser.getForms());
        assertNull(mentiUser.getId());
        assertNull(mentiUser.getPassword());
        assertNull(mentiUser.getProvider());
    }

    @Test
    public void testSetters(){
        assertNotNull(mentiUser);
        assertNull(mentiUser.getEnabled());
        mentiUser.setEnabled(true);
        assertNotNull(mentiUser.getEnabled());
    }
}
