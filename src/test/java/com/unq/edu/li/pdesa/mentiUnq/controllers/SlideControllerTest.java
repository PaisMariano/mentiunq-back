package com.unq.edu.li.pdesa.mentiUnq.controllers;

import com.unq.edu.li.pdesa.mentiUnq.controllers.fixtures.ResponseUnitFixture;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.services.FormService;
import com.unq.edu.li.pdesa.mentiUnq.services.SlideService;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class SlideControllerTest extends AbstractControllerTest
{
	@InjectMocks
	private SlideController controller;

	@Mock
	private SlideService slideService;

	@BeforeEach
	public void setUp(){
		init(controller);
	}

	@Test
	public void whenTriesToGetAllSlidesAndServiceReturnsContentThenControllerReturns200WithContent() throws Exception
	{
		ResponseUnit responseUnit = ResponseUnitFixture.withOkResponseSlides();

		when(slideService.findAll()).thenReturn(responseUnit);

		final MvcResult result = mockMvc.perform(get("/api/slide").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String response = result.getResponse().getContentAsString();
		ResponseUnit returnedResponse = gson.fromJson(response, ResponseUnit.class);
		ResponseUnit expectedResponse = ResponseUnitFixture.withOkResponseSlides();

		assertNotNull(result);
		assertNotNull(response);
		assertEquals(expectedResponse.getMessage(), returnedResponse.getMessage() );
		assertEquals(expectedResponse.getStatus(), returnedResponse.getStatus() );
		assertEquals(expectedResponse.getPayload(), returnedResponse.getPayload() );
	}

	@Test
	public void whenTriesToGetAllSlidesAndServiceReturnsEmptyContentThenControllerReturns200EmptyPayloadContent() throws Exception
	{
		ResponseUnit responseUnit = ResponseUnitFixture.withDefaults();

		when(slideService.findAll()).thenReturn(responseUnit);

		final MvcResult result = mockMvc.perform(get("/api/slide").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String response = result.getResponse().getContentAsString();
		ResponseUnit returnedResponse = gson.fromJson(response, ResponseUnit.class);
		ResponseUnit expectedResponse = ResponseUnitFixture.withDefaults();

		assertNotNull(result);
		assertNotNull(response);
		assertEquals(expectedResponse.getMessage(), returnedResponse.getMessage() );
		assertEquals(expectedResponse.getStatus(), returnedResponse.getStatus() );
		assertEquals(Strings.EMPTY, returnedResponse.getPayload() );
	}

	@Test
	public void whenTriesToGetSlideAndServiceReturnsContentThenControllerReturns200WithContent() throws Exception
	{
		ResponseUnit responseUnit = ResponseUnitFixture.withOkResponseSlide();
		Long slideId = 1l;

		when(slideService.findById(anyLong())).thenReturn(responseUnit);

		final MvcResult result = mockMvc.perform(get("/api/slide/{id}", slideId).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String response = result.getResponse().getContentAsString();
		ResponseUnit returnedResponse = gson.fromJson(response, ResponseUnit.class);
		ResponseUnit expectedResponse = ResponseUnitFixture.withOkResponseSlide();

		assertNotNull(result);
		assertNotNull(response);
		assertEquals(expectedResponse.getMessage(), returnedResponse.getMessage() );
		assertEquals(expectedResponse.getStatus(), returnedResponse.getStatus() );
		assertEquals(expectedResponse.getPayload(), returnedResponse.getPayload() );
	}
}
