package com.unq.edu.li.pdesa.mentiUnq.controllers.fixtures;

import com.unq.edu.li.pdesa.mentiUnq.controllers.request.AnswerRequest;

public class AnswerRequestFixture {
    private final String option = "OPTION";

    public static AnswerRequest withDefaults() {
        return new AnswerRequestFixture().build();
    }

    private AnswerRequest build() {
        return new AnswerRequest(option);
    }
}
