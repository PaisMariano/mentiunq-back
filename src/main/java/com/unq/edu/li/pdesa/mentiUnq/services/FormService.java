package com.unq.edu.li.pdesa.mentiUnq.services;

import com.unq.edu.li.pdesa.mentiUnq.exceptions.EntityNotFoundException;
import com.unq.edu.li.pdesa.mentiUnq.models.Form;
import com.unq.edu.li.pdesa.mentiUnq.models.MentiUser;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.protocols.Status;
import com.unq.edu.li.pdesa.mentiUnq.repositories.FormRepository;
import com.unq.edu.li.pdesa.mentiUnq.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
public class FormService {
    private final FormRepository formRepository;
    private final UserRepository userRepository;

    public FormService(FormRepository formRepository, UserRepository userRepository) {
        this.formRepository = formRepository;
        this.userRepository = userRepository;
    }

    public ResponseUnit createForm(Long userId) throws EntityNotFoundException
    {
        MentiUser user = userRepository.findById(userId).orElseThrow(
                ()-> EntityNotFoundException.createWith(userId.toString())
        );

        String code = UUID.randomUUID().toString();
        String codeShare = Base64.getEncoder().encodeToString(code.getBytes(StandardCharsets.UTF_8)).substring(0, 8).toUpperCase();

        Form form = new Form();
        form.setCode(code);
        form.setCodeShare(codeShare);
        form.setName("Formulario "+codeShare);
        form.setCreationDate(LocalDateTime.now());
        form.setUpdateDate(LocalDateTime.now());
        form.setMentiUser(user);

        return new ResponseUnit(Status.SUCCESS, "", formRepository.save(form));
    }
}


