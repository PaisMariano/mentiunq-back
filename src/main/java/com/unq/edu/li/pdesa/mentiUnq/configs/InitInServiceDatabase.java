package com.unq.edu.li.pdesa.mentiUnq.configs;

import com.unq.edu.li.pdesa.mentiUnq.exceptions.EntityNotFoundException;
import com.unq.edu.li.pdesa.mentiUnq.models.Slide;
import com.unq.edu.li.pdesa.mentiUnq.models.SlideType;
import com.unq.edu.li.pdesa.mentiUnq.models.SlideTypeEnum;
import com.unq.edu.li.pdesa.mentiUnq.services.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitInServiceDatabase {
    @Value("${spring.datasource.driverClassName:org.h2.Driver}")
    private String className;
    private final FormService formService;
    private final SlideService slideService;
    private final AuthService authService;
    private final UserService userService;
    private final SlideTypeService slideTypeService;

    public InitInServiceDatabase(SlideService slideService,
                                 AuthService authService,
                                 FormService formService,
                                 UserService userService,
                                 SlideTypeService slideTypeService) {

        this.formService = formService;
        this.slideService = slideService;
        this.authService = authService;
        this.userService = userService;
        this.slideTypeService = slideTypeService;
    }

    @PostConstruct
    public void initialize() throws Exception {
        if (className.equals("org.h2.Driver")) {
            fireInitialSlides();
            fireInitialUsers();
            fireInitialMailWhitelist();
            fireInitialForms();
        }
    }
    private void fireInitialSlides() throws Exception {
        SlideType openType = new SlideType(1L, SlideTypeEnum.OPEN.getSlideType());
        SlideType closeType = new SlideType(2L, SlideTypeEnum.CLOSE.getSlideType());
        SlideType contentType = new SlideType(3L, SlideTypeEnum.CONTENT.getSlideType());

        slideTypeService.create(openType);
        slideTypeService.create(closeType);
        slideTypeService.create(contentType);

        slideService.create(new Slide(1L,"Multiple Choice", closeType));
        slideService.create(new Slide(2L,"Word Cloud", openType));
        slideService.create(new Slide(3L, "Open Ended", openType));
        slideService.create(new Slide(4L, "Scales", openType));
        slideService.create(new Slide(5L, "Ranking", openType));
        slideService.create(new Slide(6L, "Q&A", openType));
        slideService.create(new Slide(7L, "Select Answer", closeType));
        slideService.create(new Slide(8L, "Type Answer", openType));
        slideService.create(new Slide(9L, "Heading", contentType));
        slideService.create(new Slide(10L, "Paragraph", contentType));
        slideService.create(new Slide(11L, "Bullets", contentType));

    }

    private void fireInitialUsers() throws Exception {
        authService.processOAuthPostLogin("paismariano@gmail.com", "123456789");
        authService.processOAuthPostLogin("pablo.g.marrero@gmail.com", "123456789");
    }

    private void fireInitialMailWhitelist() throws Exception {
        authService.createWhiteListEmail("paismarianoa@gmail.com");
        authService.createWhiteListEmail("frasespuras@gmail.com");
    }

    private void fireInitialForms() throws EntityNotFoundException
    {
        formService.createForm(1l);
    }
}
