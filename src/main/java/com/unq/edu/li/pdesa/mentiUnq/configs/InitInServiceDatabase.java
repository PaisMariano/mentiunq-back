package com.unq.edu.li.pdesa.mentiUnq.configs;

import com.unq.edu.li.pdesa.mentiUnq.controllers.request.AnswerRequest;
import com.unq.edu.li.pdesa.mentiUnq.controllers.request.QuestionRequest;
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
        authService.processOAuthPostLogin("paismarianoa@gmail.com", "123456789");
        authService.processOAuthPostLogin("pablo.g.marrero@gmail.com", "123456789");
    }

    private void fireInitialMailWhitelist() throws Exception {
        authService.createWhiteListEmail("paismarianoa@gmail.com");
        authService.createWhiteListEmail("frasespuras@gmail.com");
    }

    private void fireInitialForms() throws Exception
    {
        formService.createForm(1l);

        //HEADING
        QuestionRequest question1 = new QuestionRequest();
        question1.setSlideId(9L);
        question1.setQuestion("Presentación de Práctica de Desarrollo de Software");

        //MULTIPLE CHOICE
        QuestionRequest question2 = new QuestionRequest();
        question2.setSlideId(1L);
        question2.setQuestion("¿Es Gabi la mejor directora del mundo?");

        //OPCIONES
        AnswerRequest answer1 = new AnswerRequest();
        answer1.setOption("Sí, obvio");

        AnswerRequest answer2 = new AnswerRequest();
        answer2.setOption("Sí y se va a quedar por siempre");

        AnswerRequest answer3 = new AnswerRequest();
        answer3.setOption("No, para nada (Atenete a las consecuencias)");

        AnswerRequest answer4 = new AnswerRequest();
        answer4.setOption("Si, pero solo si me anota en Algoritmos");

        AnswerRequest answer5 = new AnswerRequest();
        answer5.setOption("NS / NC");


        //WORD CLOUD
        QuestionRequest question3 = new QuestionRequest();
        question3.setSlideId(2L);
        question3.setQuestion("¿Cúal fue el tema de la materia que más te costó integrar al tp / enseñar?");

        formService.addQuestion(5L, question1);
        formService.addQuestion(5L, question2);
        formService.addQuestion(5L, question3);

        formService.addAnswer(5L, 8L, answer1);
        formService.addAnswer(5L, 8L, answer2);
        formService.addAnswer(5L, 8L, answer3);
        formService.addAnswer(5L, 8L, answer4);
        formService.addAnswer(5L, 8L, answer5);

        formService.deleteQuestionById(5L, 6L);
    }
}
