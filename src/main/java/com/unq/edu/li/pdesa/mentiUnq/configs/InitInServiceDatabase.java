package com.unq.edu.li.pdesa.mentiUnq.configs;

import com.unq.edu.li.pdesa.mentiUnq.models.MentiUser;
import com.unq.edu.li.pdesa.mentiUnq.models.Slide;
import com.unq.edu.li.pdesa.mentiUnq.repositories.UserRepository;
import com.unq.edu.li.pdesa.mentiUnq.services.FormService;
import com.unq.edu.li.pdesa.mentiUnq.services.SlideService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitInServiceDatabase {
    @Value("${spring.datasource.driverClassName:org.h2.Driver}")
    private String className;
    private final UserRepository userRepository;
    private final SlideService slideService;

    public InitInServiceDatabase(SlideService slideService, UserRepository userRepository){
        this.userRepository = userRepository;
        this.slideService = slideService;
    }

    @PostConstruct
    public void initialize() throws Exception {
        if (className.equals("org.h2.Driver")) {
            fireInitialSlides();

            userRepository.save(MentiUser.builder().userName("pepito").build());
        }
    }

    private void fireInitialSlides() throws Exception {
        slideService.create(new Slide(1L,"Multiple Choice"));
        slideService.create(new Slide(2L,"World Cloud"));
        slideService.create(new Slide(3L, "Open Ended"));
        slideService.create(new Slide(4L, "Scales"));
        slideService.create(new Slide(5L, "Ranking"));
        slideService.create(new Slide(6L, "Q&A"));
        slideService.create(new Slide(7L, "Select Answer"));
        slideService.create(new Slide(8L, "Type Answer"));
        slideService.create(new Slide(9L, "Heading"));
        slideService.create(new Slide(10L, "Paragraph"));
        slideService.create(new Slide(11L, "Bullets"));

    }
}
