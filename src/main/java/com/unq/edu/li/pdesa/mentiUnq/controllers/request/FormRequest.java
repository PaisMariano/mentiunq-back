package com.unq.edu.li.pdesa.mentiUnq.controllers.request;

import com.unq.edu.li.pdesa.mentiUnq.models.Question;
import com.unq.edu.li.pdesa.mentiUnq.models.Slide;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class FormRequest
{
    private List<QuestionRequest> questions;
}
