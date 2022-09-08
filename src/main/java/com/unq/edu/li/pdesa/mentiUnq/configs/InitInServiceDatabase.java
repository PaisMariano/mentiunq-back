package com.unq.edu.li.pdesa.mentiUnq.configs;

import com.unq.edu.li.pdesa.mentiUnq.models.Slide;
import com.unq.edu.li.pdesa.mentiUnq.services.FormService;
import com.unq.edu.li.pdesa.mentiUnq.services.SlideService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static java.lang.Long.valueOf;

@Component
public class InitInServiceDatabase {
    @Value("${spring.datasource.driverClassName:org.h2.Driver}")
    private String className;
    //private final FormService formService;
    private final SlideService slideService;

    public InitInServiceDatabase(SlideService slideService){
        //this.formService = formService;
        this.slideService = slideService;
    }

    @PostConstruct
    public void initialize() throws Exception {
        if (className.equals("org.h2.Driver")) {
            fireInitialSlides();
        }
    }

    private void fireInitialSlides() throws Exception {
        slideService.create(new Slide(1L,"Slide 1"));

    }
}
