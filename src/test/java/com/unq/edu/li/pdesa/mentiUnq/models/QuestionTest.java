package com.unq.edu.li.pdesa.mentiUnq.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class QuestionTest {
    private Question question;

    @BeforeEach
    public void setUp(){
        question = new Question();
    }

    @Test
    public void testGetters(){
        assertNotNull(question);
        assertNull(question.getQuestion());
        question.setQuestion("name");
        assertNotNull(question.getQuestion());
        assertNull(question.getForm());
        assertNull(question.getId());
        assertNull(question.getIsCurrent());
        assertNull(question.getMentiOptions());
        assertNull(question.getSlide());
    }
}
