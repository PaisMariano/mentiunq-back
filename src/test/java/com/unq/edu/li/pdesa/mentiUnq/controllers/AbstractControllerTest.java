package com.unq.edu.li.pdesa.mentiUnq.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unq.edu.li.pdesa.mentiUnq.handlers.ResponseHandler;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractControllerTest
{
	protected Gson gson;
	protected MockMvc mockMvc;

	protected void init(Object controller){
		mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new ResponseHandler()).build();
		gson = new GsonBuilder().create();
	}

	protected String asJsonString(final Object obj)
	{
		return gson.toJson(obj);
	}

	protected void verifyReturnedAndExpectedResponse(ResponseUnit returnedResponse, ResponseUnit expectedResponse) {
		assertEquals(expectedResponse.getStatus(), returnedResponse.getStatus());
		assertEquals(expectedResponse.getMessage(), returnedResponse.getMessage());
		assertEquals(expectedResponse.getPayload(), returnedResponse.getPayload());
	}
}
