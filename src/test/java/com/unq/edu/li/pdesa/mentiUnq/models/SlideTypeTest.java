package com.unq.edu.li.pdesa.mentiUnq.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SlideTypeTest {
    private SlideType slideType;

    @BeforeEach
    public void setUp(){
        slideType = new SlideType();
    }

    @Test
    public void testGetter(){
        assertNotNull(slideType);
        assertNull(slideType.getId());
        assertNull(slideType.getName());
        slideType.setName("name");
        assertNotNull(slideType.getName());
        assertEquals("name", slideType.getName());
    }
}
