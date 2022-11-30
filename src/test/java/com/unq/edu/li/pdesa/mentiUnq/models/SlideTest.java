package com.unq.edu.li.pdesa.mentiUnq.models;

import com.unq.edu.li.pdesa.mentiUnq.services.fixtures.SlideFixture;
import com.unq.edu.li.pdesa.mentiUnq.services.fixtures.SlideTypeFixture;
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
		slide = SlideFixture.withDefaults();
    }

	@Test
	public void testGetterAndSetters(){

		assertNotNull(slide.getNombre());
		assertNotNull(slide.getId());
		assertNotNull(slide.getSlideType());
		assertEquals("Multiple Choice", slide.getNombre());

		slide.setNombre("name");
		slide.setId(2l);
		slide.setSlideType(null);

		assertEquals("name", slide.getNombre());
		assertEquals(2l, slide.getId());
		assertNull(slide.getSlideType());
	}

	@Test
	public void testDefaultConstructor(){
		slide = new Slide(1l, "name");

		assertNotNull(slide);
		assertNotNull(slide.getId());
		assertNotNull(slide.getNombre());
		assertNull(slide.getSlideType());
		assertEquals("name", slide.getNombre());
	}

	@Test
	public void testNonArgsConstructor(){
		slide = new Slide();

		assertNotNull(slide);
		assertNull(slide.getId());
		assertNull(slide.getNombre());
	}
}
