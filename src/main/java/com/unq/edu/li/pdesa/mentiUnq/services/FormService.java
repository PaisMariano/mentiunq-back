package com.unq.edu.li.pdesa.mentiUnq.services;

import com.unq.edu.li.pdesa.mentiUnq.exceptions.EntityNotFoundException;
import com.unq.edu.li.pdesa.mentiUnq.models.Form;
import com.unq.edu.li.pdesa.mentiUnq.models.MentiUser;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.protocols.Status;
import com.unq.edu.li.pdesa.mentiUnq.repositories.FormRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Service
public class FormService {
    private final FormRepository formRepository;
	private final ModelMapper mapper;

    public FormService(FormRepository formRepository) {
        this.formRepository = formRepository;
		mapper = new ModelMapper();
    }

    public ResponseUnit findById(Long id) throws Exception {
        return new ResponseUnit(Status.SUCCESS, "", formRepository.findById(id).orElseThrow(
                () -> EntityNotFoundException.createWith(id.toString())));
    }

	public ResponseUnit create()
	{
		//Type listType = new TypeToken<List<Question>>(){}.getType();
		String code = UUID.randomUUID().toString();
		MentiUser user = MentiUser.builder().name("pepito").build(); //TODO replace with select to DB from Token.id

		Form form = Form.builder()
				.code(code)
				.codeShare(Base64.getEncoder().encodeToString(code.getBytes(StandardCharsets.UTF_8)).substring(0, 8).toUpperCase())
				//.questions(mapper.map(request.getQuestions(), listType))
				//.mentiUser(user)
				.build();

		form = formRepository.save(form);

		return new ResponseUnit(Status.SUCCESS, "", form);
	}
}
