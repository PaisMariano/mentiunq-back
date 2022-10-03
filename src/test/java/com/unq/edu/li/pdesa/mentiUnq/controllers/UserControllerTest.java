package com.unq.edu.li.pdesa.mentiUnq.controllers;

import com.unq.edu.li.pdesa.mentiUnq.controllers.fixtures.ResponseUnitFixture;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.services.UserService;
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
public class UserControllerTest extends AbstractControllerTest
{
	@InjectMocks
	private UserController controller;

	@Mock
	private UserService userService;

	@BeforeEach
	public void setUp(){
		init(controller);
	}

	@Test
	public void whenGetAllFormsByIdAndServiceReturnsEmptyContentThenControllerReturnsA200ResponseWithEmptyContent() throws Exception
	{
		final ResponseUnit responseUnit = ResponseUnitFixture.withOkResponseFormsUser();
		final Long userId = 1l;
		when(userService.getAllFormsById(anyLong())).thenReturn(responseUnit);

		final MvcResult result = mockMvc.perform(get("/api/user/forms/{id}", userId).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String response = result.getResponse().getContentAsString();
		ResponseUnit returnedResponse = gson.fromJson(response, ResponseUnit.class);
		ResponseUnit expectedResponse = ResponseUnitFixture.withOkResponseFormsUser();

		assertNotNull(result);
		assertNotNull(response);
		assertEquals(expectedResponse.getMessage(), returnedResponse.getMessage() );
		assertEquals(expectedResponse.getStatus(), returnedResponse.getStatus() );
		assertEquals(expectedResponse.getPayload(), returnedResponse.getPayload() );
	}

	@Test
	public void whenGetAllFormsByIdAndServiceReturnsEmptyContentThenControllerReturnsA404BadRequest() throws Exception
	{
		final MvcResult result = mockMvc.perform(get("/api/user/forms/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();

		assertNotNull(result);
	}
}
