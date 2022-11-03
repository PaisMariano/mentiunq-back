package com.unq.edu.li.pdesa.mentiUnq.controllers.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class FormNameRequestTest
{
	private FormNameRequest request;

	@BeforeEach
	public void setUp(){
		request = new FormNameRequest();
	}

	@Test
	public void testGettersAndSetters(){
		String newName = "NAME";

		assertNotNull(request);
		assertNull(request.getName());
		request.setName(newName);
		assertEquals(newName, request.getName());
	}
}
