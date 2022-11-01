package com.unq.edu.li.pdesa.mentiUnq.controllers.fixtures;

import com.unq.edu.li.pdesa.mentiUnq.controllers.request.QuestionRequest;

public class QuestionRequestFixture {

    private final String question = "QUESTION";
    private final Long slideId = 1l;

    public static QuestionRequest withDefaults() {
        return new QuestionRequestFixture().build();
    }

    private QuestionRequest build() {
        return new QuestionRequest(question, slideId);
    }
}
