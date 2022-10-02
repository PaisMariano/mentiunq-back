package com.unq.edu.li.pdesa.mentiUnq.services;

import com.unq.edu.li.pdesa.mentiUnq.exceptions.EntityNotFoundException;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.protocols.Status;
import com.unq.edu.li.pdesa.mentiUnq.repositories.FormRepository;
import org.springframework.stereotype.Service;

@Service
public class FormService {
    private final FormRepository formRepository;

    public FormService(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    public ResponseUnit findById(Long id) throws Exception {
        return new ResponseUnit(Status.SUCCESS, "", formRepository.findById(id).orElseThrow(
                () -> EntityNotFoundException.createWith(id.toString())));
    }

}
