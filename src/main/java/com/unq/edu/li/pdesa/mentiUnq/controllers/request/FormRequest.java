package com.unq.edu.li.pdesa.mentiUnq.controllers.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unq.edu.li.pdesa.mentiUnq.models.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class FormRequest
{
    @JsonProperty("questions")
    private List<Question> questions;
}
