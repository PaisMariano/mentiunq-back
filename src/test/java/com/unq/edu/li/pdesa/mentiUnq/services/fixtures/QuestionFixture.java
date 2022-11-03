package com.unq.edu.li.pdesa.mentiUnq.services.fixtures;

import com.unq.edu.li.pdesa.mentiUnq.models.Form;
import com.unq.edu.li.pdesa.mentiUnq.models.MentiOption;
import com.unq.edu.li.pdesa.mentiUnq.models.Question;
import com.unq.edu.li.pdesa.mentiUnq.models.Slide;

import java.util.ArrayList;
import java.util.List;

public class QuestionFixture {

    private final Long id = 1l;
    private final String question = "QUESTION";
    private final Boolean isCurrent = true;
    private final Slide slide = SlideFixture.withDefaults();
    private final Form form = FormFixture.withDefaults();
    private final List<MentiOption> mentiOptions = new ArrayList<>();

    public static Question withDefaults() {
        return new QuestionFixture().build();
    }

    private Question build() {
        return new Question(id, question, isCurrent, slide, form, mentiOptions);
    }
}
