package com.unq.edu.li.pdesa.mentiUnq.controllers.fixtures;

import com.unq.edu.li.pdesa.mentiUnq.controllers.request.QuestionRequest;
import org.apache.logging.log4j.util.Strings;

public class QuestionRequestFixture {

    private final String question = "QUESTION";
    private final Long slideId = 1l;

    public static QuestionRequest withDefaults() {
        return new QuestionRequestFixture().build();
    }

    public static QuestionRequest withSlideIdNull() {
        QuestionRequest request = QuestionRequestFixture.withDefaults();
        request.setSlideId(null);
        return request;
    }

    public static QuestionRequest withQuestionEmpty() {
        QuestionRequest request = QuestionRequestFixture.withDefaults();
        request.setQuestion(Strings.EMPTY);
        return request;
    }

    private QuestionRequest build() {
        return new QuestionRequest(question, slideId);
    }
}
