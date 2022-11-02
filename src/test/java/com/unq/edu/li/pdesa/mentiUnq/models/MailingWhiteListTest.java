package com.unq.edu.li.pdesa.mentiUnq.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MailingWhiteListTest {

    private MailingWhiteList mailingWhiteList;

    @BeforeEach
    public void setUp(){
        mailingWhiteList = new MailingWhiteList();
    }

    @Test
    public void testSetterAndGetter(){
        assertNotNull(mailingWhiteList);
        assertNull(mailingWhiteList.getEmail());
        mailingWhiteList.setEmail("test");
        assertNotNull(mailingWhiteList.getEmail());
    }
}
