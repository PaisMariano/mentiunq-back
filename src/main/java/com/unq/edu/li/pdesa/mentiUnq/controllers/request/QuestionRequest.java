package com.unq.edu.li.pdesa.mentiUnq.controllers.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class QuestionRequest {
    private String question;
    @NotNull
    private Long slideId;
}
