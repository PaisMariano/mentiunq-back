package com.unq.edu.li.pdesa.mentiUnq.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FormTest {

    private Form aForm;

    @BeforeEach
    public void setUp(){
        aForm = new Form();
    }

    @Test
    public void testGetters(){
        assertNotNull(aForm);
        assertNull(aForm.getId());
        assertNull(aForm.getCode());
        assertNull(aForm.getCodeShare());
        assertNull(aForm.getName());
        assertNull(aForm.getCreationDate());
        assertNull(aForm.getUpdateDate());
        assertNull(aForm.getQuestions());
        assertNull(aForm.getMentiUser());
    }
}
