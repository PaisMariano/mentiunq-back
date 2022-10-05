package com.unq.edu.li.pdesa.mentiUnq.handlers;

import com.unq.edu.li.pdesa.mentiUnq.controllers.UserController;
import com.unq.edu.li.pdesa.mentiUnq.exceptions.EntityNotFoundException;
import com.unq.edu.li.pdesa.mentiUnq.exceptions.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ResponseHandlerTest
{
	private MockMvc mockMvc;
	private ResponseHandler responseHandler;
	private Long entityId;

	@Mock
	private UserController controller;

	@BeforeEach
	public void setUp()
	{
		responseHandler = new ResponseHandler();
		mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(responseHandler).build();

		entityId = 1l;
	}

	@Test
	public void whenGetAllFormsByIdAndNoExistEntityThenRaiseEntityNotFoundError() throws Exception
	{
		doThrow(EntityNotFoundException.createWith(entityId.toString())).when(controller).getById(entityId);

		mockMvc.perform(
						get("/api/user/forms/{id}", entityId.toString())
								.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isNotFound()).andReturn();

	}

	@Test
	public void whenGetAllFormsByIdAndIsNoAuthorizedtEntityThenRaiseEntityNotFoundError() throws Exception
	{
		doThrow(UnauthorizedException.createWith(HttpStatus.UNAUTHORIZED.toString())).when(controller).getById(entityId);

		mockMvc.perform(
						get("/api/user/forms/{id}", entityId.toString())
								.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isUnauthorized()).andReturn();

	}

	@Test
	public void whenGetAllFormsByIdAndIsNoAuthorizedEntityThenRaiseAccessDeniedException() throws Exception
	{
		doThrow(new AccessDeniedException(HttpStatus.UNAUTHORIZED.toString())).when(controller).getById(entityId);

		mockMvc.perform(
						get("/api/user/forms/{id}", entityId.toString())
								.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isUnauthorized()).andReturn();

	}
}
