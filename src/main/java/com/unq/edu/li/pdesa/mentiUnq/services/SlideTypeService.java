package com.unq.edu.li.pdesa.mentiUnq.services;

import com.unq.edu.li.pdesa.mentiUnq.models.SlideType;
import com.unq.edu.li.pdesa.mentiUnq.repositories.SlideTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class SlideTypeService {
    private final SlideTypeRepository slideTypeRepository;

    public SlideTypeService(SlideTypeRepository slideTypeRepository) {
        this.slideTypeRepository = slideTypeRepository;
    }

    public void create(SlideType slideType) {
        slideTypeRepository.save(slideType);
    }
}
