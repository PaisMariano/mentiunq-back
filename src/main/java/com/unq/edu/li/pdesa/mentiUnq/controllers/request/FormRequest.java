package com.unq.edu.li.pdesa.mentiUnq.controllers.request;

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
    private List<Question> questions;
}
