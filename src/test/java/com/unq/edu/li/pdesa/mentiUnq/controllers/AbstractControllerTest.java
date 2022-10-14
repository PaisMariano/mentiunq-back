package com.unq.edu.li.pdesa.mentiUnq.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unq.edu.li.pdesa.mentiUnq.handlers.ResponseHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
}
