package com.unq.edu.li.pdesa.mentiUnq.configs;

import com.unq.edu.li.pdesa.mentiUnq.exceptions.EntityNotFoundException;
import com.unq.edu.li.pdesa.mentiUnq.models.Slide;
import com.unq.edu.li.pdesa.mentiUnq.services.FormService;
import com.unq.edu.li.pdesa.mentiUnq.services.SlideService;
import com.unq.edu.li.pdesa.mentiUnq.services.AuthService;
import com.unq.edu.li.pdesa.mentiUnq.services.UserService;
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

    public InitInServiceDatabase(SlideService slideService,
                                 AuthService authService,
                                 FormService formService,
                                 UserService userService) {

        this.formService = formService;
        this.slideService = slideService;
        this.authService = authService;
        this.userService = userService;
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
    private void fireInitialUsers() throws Exception {
        authService.processOAuthPostLogin("paismariano@gmail.com", "123456789");
        authService.processOAuthPostLogin("pablo.g.marrero@gmail.com", "123456789");
    }

    private void fireInitialMailWhitelist() throws Exception {
        authService.createWhiteListEmail("paismarianoa@gmail.com");
    }

    private void fireInitialForms() throws EntityNotFoundException
    {
        formService.createForm(1l);
    }
}
