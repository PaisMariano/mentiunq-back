package com.unq.edu.li.pdesa.mentiUnq.controllers;

import com.unq.edu.li.pdesa.mentiUnq.controllers.fixtures.AnswerRequestFixture;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AnswerControllerTest extends AbstractControllerTest {

    @InjectMocks
    private AnswerController controller;

    @Mock
    private FormService formService;

    private final String codeShare = "CODE";
    private final Long questionId = 1l;
    private final Long optionId = 1l;

    @BeforeEach
    public void setUp(){
        init(controller);
    }

    @Test
    public void whenCreatesAnAnswerThenControllerReturnsA200Ok() throws Exception
    {
        ResponseUnit responseUnit = ResponseUnitFixture.withOkResponseCreateForm();
        AnswerRequest request = AnswerRequestFixture.withDefaults();

        when(formService.addAnswer(anyString(), anyLong(), any(AnswerRequest.class))).thenReturn(responseUnit);

        final MvcResult result = mockMvc.perform(post("/answer/form/{codeShare}/question/{questionId}", codeShare, questionId)
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
    public void whenGetQuestionByCodeShareThenControllerReturnsA200Ok() throws Exception
    {
        ResponseUnit responseUnit = ResponseUnitFixture.withOkResponseCreateForm();

        when(formService.getQuestionByCodeShare(anyString())).thenReturn(responseUnit);

        final MvcResult result = mockMvc.perform(get("/answer/form/{codeShare}/question/current", codeShare)
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
    public void whenVotesThenControllerReturnsA200Ok() throws Exception
    {
        ResponseUnit responseUnit = ResponseUnitFixture.withOkResponseCreateForm();

        when(formService.vote(anyString(), anyLong(), anyLong())).thenReturn(responseUnit);

        final MvcResult result = mockMvc.perform(
                    post("/answer/form/{codeShare}/question/{questionId}/option/{optionId}"
                        , codeShare, questionId, optionId)
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
