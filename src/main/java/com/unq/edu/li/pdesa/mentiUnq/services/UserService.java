package com.unq.edu.li.pdesa.mentiUnq.services;

import com.unq.edu.li.pdesa.mentiUnq.exceptions.EntityNotFoundException;
import com.unq.edu.li.pdesa.mentiUnq.models.Form;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.protocols.Status;
import com.unq.edu.li.pdesa.mentiUnq.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseUnit getAllFormsById(Long id) throws EntityNotFoundException {
        List<Form> forms = userRepository.getFullUserById(id).orElseThrow(
                        () -> EntityNotFoundException.createWith(id.toString()))
                .getForms();

        return new ResponseUnit(Status.SUCCESS, "", forms);
    }

}