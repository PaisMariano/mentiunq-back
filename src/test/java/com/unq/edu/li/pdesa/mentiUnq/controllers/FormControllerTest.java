package com.unq.edu.li.pdesa.mentiUnq.controllers;

import com.unq.edu.li.pdesa.mentiUnq.controllers.fixtures.ResponseUnitFixture;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.services.FormService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class FormControllerTest extends AbstractControllerTest
{
	@InjectMocks
	private FormController controller;

	@Mock
	private FormService formService;

	@BeforeEach
	public void setUp(){
		init(controller);
	}

	@Test
	public void whenCreateFormWithoutRequestThenControllerReturnsA400BadRequest() throws Exception
	{
		final MvcResult result = mockMvc.perform(post("/api/form/form")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();

		assertNotNull(result);
	}

	@Test
	public void whenCreateFormWithValidRequestThenControllerReturnsA200BadRequest() throws Exception
	{
		ResponseUnit responseUnit = ResponseUnitFixture.withOkResponseCreateForm();

		when(formService.createForm(userId)).thenReturn(responseUnit);

		final MvcResult result = mockMvc.perform(post("/api/form/form")
						.content(asJsonString(responseUnit))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();


		String response = result.getResponse().getContentAsString();
		ResponseUnit returnedResponse = gson.fromJson(response, ResponseUnit.class);
		ResponseUnit expectedResponse = ResponseUnitFixture.withOkResponseCreateForm();

		assertNotNull(result);
		assertNotNull(response);
		assertEquals(expectedResponse.getStatus(), returnedResponse.getStatus());
		assertEquals(expectedResponse.getMessage(), returnedResponse.getMessage());
		assertEquals(expectedResponse.getPayload(), returnedResponse.getPayload());
	}
}
