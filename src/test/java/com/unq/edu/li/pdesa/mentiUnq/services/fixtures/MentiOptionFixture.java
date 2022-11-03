package com.unq.edu.li.pdesa.mentiUnq.services.fixtures;

import com.unq.edu.li.pdesa.mentiUnq.models.MentiOption;
import com.unq.edu.li.pdesa.mentiUnq.models.Question;


public class MentiOptionFixture {
    private final Long id = 1l;
    private final String name = "NAME";
    private final Integer score = 1;
    private final Question question = QuestionFixture.withDefaults();

    public static MentiOption withDefaults() {
        return new MentiOptionFixture().build();
    }

    private MentiOption build() {
        return new MentiOption(id, name, score, question);
    }
}
