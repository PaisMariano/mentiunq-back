package com.unq.edu.li.pdesa.mentiUnq.services;

import com.unq.edu.li.pdesa.mentiUnq.exceptions.EntityNotFoundException;
import com.unq.edu.li.pdesa.mentiUnq.models.Form;
import com.unq.edu.li.pdesa.mentiUnq.models.MentiUser;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.protocols.Status;
import com.unq.edu.li.pdesa.mentiUnq.repositories.FormRepository;
import com.unq.edu.li.pdesa.mentiUnq.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Service
public class FormService {
    private final FormRepository formRepository;
	private final UserRepository userRepository;
	private final ModelMapper mapper;

    public FormService(FormRepository formRepository, UserRepository userRepository) {
        this.formRepository = formRepository;
		this.userRepository = userRepository;
		mapper = new ModelMapper();
    }

    public ResponseUnit findById(Long id) throws Exception {
        return new ResponseUnit(Status.SUCCESS, "", formRepository.findById(id).orElseThrow(
                () -> EntityNotFoundException.createWith(id.toString())));
    }

	public ResponseUnit create(Long userId) throws EntityNotFoundException
	{
		String code = UUID.randomUUID().toString();
		MentiUser user = userRepository.findById(userId).orElseThrow(
				() -> EntityNotFoundException.createWith(userId.toString()));

		Form form = Form.builder()
				.code(code)
				.codeShare(Base64.getEncoder().encodeToString(code.getBytes(StandardCharsets.UTF_8)).substring(0, 8).toUpperCase())
				.build();

		//form = formRepository.save(form);
		user.setForm(form);
		user = userRepository.save(user);

		return new ResponseUnit(Status.SUCCESS, "", user);
	}
}
