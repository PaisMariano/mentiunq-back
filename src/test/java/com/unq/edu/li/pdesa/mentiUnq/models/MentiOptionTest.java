package com.unq.edu.li.pdesa.mentiUnq.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class MentiOptionTest {
    MentiOption mentiOption;

    @BeforeEach
    public void setUp(){
        mentiOption = new MentiOption();
    }

    @Test
    public void testGetter(){
        assertNull(mentiOption.getId());
        assertNull(mentiOption.getScore());
        assertNull(mentiOption.getName());
        assertNull(mentiOption.getQuestion());
    }
}
