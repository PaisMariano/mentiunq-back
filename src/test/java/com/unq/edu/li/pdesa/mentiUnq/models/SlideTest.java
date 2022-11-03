package com.unq.edu.li.pdesa.mentiUnq.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SlideTest {
    private Slide slide;

    @BeforeEach
    public void setUp(){
        slide = new Slide();
    }

    @Test
    public void testGetter(){
        assertNotNull(slide);
        assertNull(slide.getId());
        assertNull(slide.getNombre());
        slide.setNombre("name");
        assertNotNull(slide.getNombre());
        assertEquals("name", slide.getNombre());
    }
}
