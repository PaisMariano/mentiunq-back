package com.unq.edu.li.pdesa.mentiUnq.controllers;

import com.unq.edu.li.pdesa.mentiUnq.controllers.fixtures.AnswerRequestFixture;
import com.unq.edu.li.pdesa.mentiUnq.controllers.fixtures.QuestionRequestFixture;
import com.unq.edu.li.pdesa.mentiUnq.controllers.fixtures.ResponseUnitFixture;
import com.unq.edu.li.pdesa.mentiUnq.controllers.request.AnswerRequest;
import com.unq.edu.li.pdesa.mentiUnq.controllers.request.QuestionRequest;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class FormControllerTest extends AbstractControllerTest
{
	@InjectMocks
	private FormController controller;

	@Mock
	private FormService formService;

	Long userId = 1l;
	Long formId = 1l;
	Long questionId = 1l;
	Long optionId = 1l;
	String code = "CODE";

	@BeforeEach
	public void setUp(){
		init(controller);
	}

	@Test
	public void whenCreateFormWithoutRequestThenControllerReturnsA400BadRequest() throws Exception
	{
		final MvcResult result = mockMvc.perform(post("/api/form/user/{userId}{userId}", userId, "userId")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();

		assertNotNull(result);
	}

	@Test
	public void whenCreateFormWithValidRequestThenControllerReturnsA200Ok() throws Exception
	{
		ResponseUnit responseUnit = ResponseUnitFixture.withOkResponseCreateForm();

		when(formService.createForm(userId)).thenReturn(responseUnit);

		final MvcResult result = mockMvc.perform(post("/api/form/user/{userId}", userId)
				.content(asJsonString(responseUnit))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();


		String response = result.getResponse().getContentAsString();
		ResponseUnit returnedResponse = gson.fromJson(response, ResponseUnit.class);
		ResponseUnit expectedResponse = ResponseUnitFixture.withOkResponseCreateForm();

		assertNotNull(result);
		assertNotNull(response);
		verifyReturnedAndExpectedResponse(returnedResponse, expectedResponse);
	}

	@Test
	public void whenAddAQuestionToAFormWithValidRequestThenControllerReturnsA200Ok() throws Exception
	{
		ResponseUnit responseUnit = ResponseUnitFixture.withOkResponseCreateForm();
		QuestionRequest request = QuestionRequestFixture.withDefaults();

		when(formService.addQuestion(anyLong(), any(QuestionRequest.class))).thenReturn(responseUnit);

		final MvcResult result = mockMvc.perform(patch("/api/form/{formId}", formId)
				.content(asJsonString(request))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();


		String response = result.getResponse().getContentAsString();
		ResponseUnit returnedResponse = gson.fromJson(response, ResponseUnit.class);
		ResponseUnit expectedResponse = ResponseUnitFixture.withOkResponseCreateForm();

		assertNotNull(result);
		assertNotNull(response);
		verifyReturnedAndExpectedResponse(returnedResponse, expectedResponse);
	}

	@Test
	public void whenAddAnAnswerToAFormWithValidRequestThenControllerReturnsA200Ok() throws Exception
	{
		ResponseUnit responseUnit = ResponseUnitFixture.withOkResponseCreateForm();
		AnswerRequest request = AnswerRequestFixture.withDefaults();

		when(formService.addAnswer(anyLong(),anyLong(), any(AnswerRequest.class))).thenReturn(responseUnit);

		final MvcResult result = mockMvc.perform(patch("/api/form/{formId}/question/{questionId}", formId, questionId)
				.content(asJsonString(request))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();


		String response = result.getResponse().getContentAsString();
		ResponseUnit returnedResponse = gson.fromJson(response, ResponseUnit.class);
		ResponseUnit expectedResponse = ResponseUnitFixture.withOkResponseCreateForm();

		assertNotNull(result);
		assertNotNull(response);
		verifyReturnedAndExpectedResponse(returnedResponse, expectedResponse);
	}

	@Test
	public void whenGetAllAnswersByQuestionIdWithValidRequestThenControllerReturnsA200Ok() throws Exception
	{
		ResponseUnit responseUnit = ResponseUnitFixture.withOkGetAnswersByQuestionId();

		when(formService.getAnswersByQuestionId(anyLong())).thenReturn(responseUnit);

		final MvcResult result = mockMvc.perform(get("/api/form/question/{id}", formId, questionId)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();


		String response = result.getResponse().getContentAsString();
		ResponseUnit returnedResponse = gson.fromJson(response, ResponseUnit.class);
		ResponseUnit expectedResponse = ResponseUnitFixture.withOkGetAnswersByQuestionId();

		assertNotNull(result);
		assertNotNull(response);
		verifyReturnedAndExpectedResponse(returnedResponse, expectedResponse);
	}

	@Test
	public void whenDeleteQuestionByIdWithValidRequestThenControllerReturnsA200Ok() throws Exception
	{
		ResponseUnit responseUnit = ResponseUnitFixture.withOkDeleteQuestionById();

		when(formService.deleteQuestionById(anyLong(), anyLong())).thenReturn(responseUnit);

		final MvcResult result = mockMvc.perform(delete("/api/form/{formId}?questionId={questionId}", formId, questionId)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();


		String response = result.getResponse().getContentAsString();
		ResponseUnit returnedResponse = gson.fromJson(response, ResponseUnit.class);
		ResponseUnit expectedResponse = ResponseUnitFixture.withOkDeleteQuestionById();

		assertNotNull(result);
		assertNotNull(response);
		verifyReturnedAndExpectedResponse(returnedResponse, expectedResponse);
	}

	@Test
	public void whenDeleteOptionByIdWithValidRequestThenControllerReturnsA200Ok() throws Exception
	{
		ResponseUnit responseUnit = ResponseUnitFixture.withOkDeleteOptionById();

		when(formService.deleteOptionById(anyLong(), anyLong())).thenReturn(responseUnit);

		final MvcResult result = mockMvc.perform(delete("/api/form/{formId}/option?optionId={optionId}", formId, optionId)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();


		String response = result.getResponse().getContentAsString();
		ResponseUnit returnedResponse = gson.fromJson(response, ResponseUnit.class);
		ResponseUnit expectedResponse = ResponseUnitFixture.withOkDeleteOptionById();

		assertNotNull(result);
		assertNotNull(response);
		verifyReturnedAndExpectedResponse(returnedResponse, expectedResponse);
	}

	@Test
	public void whenGetQuestionsByFormIdWithValidRequestThenControllerReturnsA200Ok() throws Exception
	{
		ResponseUnit responseUnit = ResponseUnitFixture.withOkGetQuestionsById();

		when(formService.getQuestionsById(anyLong())).thenReturn(responseUnit);

		final MvcResult result = mockMvc.perform(get("/api/form/{formId}", formId)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();


		String response = result.getResponse().getContentAsString();
		ResponseUnit returnedResponse = gson.fromJson(response, ResponseUnit.class);
		ResponseUnit expectedResponse = ResponseUnitFixture.withOkGetQuestionsById();

		assertNotNull(result);
		assertNotNull(response);
		verifyReturnedAndExpectedResponse(returnedResponse, expectedResponse);
	}

	@Test
	public void whenGetFormByCodeWithValidRequestThenControllerReturnsA200Ok() throws Exception
	{
		ResponseUnit responseUnit = ResponseUnitFixture.withOkGetFormByCode();

		when(formService.getFormByCode(anyString())).thenReturn(responseUnit);

		final MvcResult result = mockMvc.perform(get("/api/form/code/{code}", code)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();


		String response = result.getResponse().getContentAsString();
		ResponseUnit returnedResponse = gson.fromJson(response, ResponseUnit.class);
		ResponseUnit expectedResponse = ResponseUnitFixture.withOkGetFormByCode();

		assertNotNull(result);
		assertNotNull(response);
		verifyReturnedAndExpectedResponse(returnedResponse, expectedResponse);
	}

	@Test
	public void whenUpdateCurrentQuestionWithValidRequestThenControllerReturnsA200Ok() throws Exception
	{
		ResponseUnit responseUnit = ResponseUnitFixture.withOkResponseCreateForm();
		QuestionRequest request = QuestionRequestFixture.withDefaults();

		when(formService.updateCurrentQuestion(anyLong(), anyLong())).thenReturn(responseUnit);

		final MvcResult result = mockMvc.perform(patch("/api/form/{formId}/current/question/{questionId}", formId, questionId)
				.content(asJsonString(request))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();


		String response = result.getResponse().getContentAsString();
		ResponseUnit returnedResponse = gson.fromJson(response, ResponseUnit.class);
		ResponseUnit expectedResponse = ResponseUnitFixture.withOkResponseCreateForm();

		assertNotNull(result);
		assertNotNull(response);
		verifyReturnedAndExpectedResponse(returnedResponse, expectedResponse);
	}
}
