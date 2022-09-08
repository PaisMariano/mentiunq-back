package com.unq.edu.li.pdesa.mentiUnq.services;

import com.unq.edu.li.pdesa.mentiUnq.models.Slide;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.protocols.Status;
import com.unq.edu.li.pdesa.mentiUnq.repositories.SlideRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SlideService {
    private final SlideRepository slideRepository;

    public SlideService(SlideRepository slideRepository) {
        this.slideRepository = slideRepository;
    }
    @Transactional
    public ResponseUnit create(Slide slide) {
        Slide createdSlide = slideRepository.save(slide);

        return new ResponseUnit(Status.SUCCESS, "", createdSlide);
    }

    public ResponseUnit findAll() {
        return new ResponseUnit(Status.SUCCESS, "", slideRepository.findAll());
    }
}
