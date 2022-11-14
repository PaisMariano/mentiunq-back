package com.unq.edu.li.pdesa.mentiUnq.controllers.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ResultResponseTest
{
	private ResultResponse resultResponse;

	@BeforeEach
	public void setUp(){
		resultResponse = new ResultResponse();
	}

	@Test
	public void testDefaultConstructorReturnsZeroValues(){
		assertNotNull(resultResponse);
		assertEquals(resultResponse.getVotes(), 0);
		assertEquals(resultResponse.getCloseSlides(), 0);
		assertEquals(resultResponse.getOpenSlides(), 0);
		assertEquals(resultResponse.getSlides(), 0);
		assertEquals(resultResponse.getContentSlides(), 0);
	}

	@Test
	public void testDefaultConstructorAndSetValuesToOneReturnsValuesWithOne(){
		assertNotNull(resultResponse);
		resultResponse.setVotes(1);
		resultResponse.setCloseSlides(1);
		resultResponse.setOpenSlides(1);
		resultResponse.setContentSlides(1);
		resultResponse.setSlides(1);
		assertEquals(resultResponse.getVotes(), 1);
		assertEquals(resultResponse.getCloseSlides(), 1);
		assertEquals(resultResponse.getOpenSlides(), 1);
		assertEquals(resultResponse.getSlides(), 1);
		assertEquals(resultResponse.getContentSlides(), 1);
	}
}
