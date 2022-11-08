package com.unq.edu.li.pdesa.mentiUnq.controllers.request;

import com.unq.edu.li.pdesa.mentiUnq.models.MentiOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequest {
    private String question;
    @NotNull
	private Long slideId;
	private List<MentiOption> options;
}
