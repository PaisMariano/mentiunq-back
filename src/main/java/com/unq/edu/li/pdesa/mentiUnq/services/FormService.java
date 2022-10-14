package com.unq.edu.li.pdesa.mentiUnq.services;

import com.unq.edu.li.pdesa.mentiUnq.controllers.request.AnswerRequest;
import com.unq.edu.li.pdesa.mentiUnq.controllers.request.QuestionRequest;
import com.unq.edu.li.pdesa.mentiUnq.exceptions.BadRequestException;
import com.unq.edu.li.pdesa.mentiUnq.exceptions.EntityNotFoundException;
import com.unq.edu.li.pdesa.mentiUnq.models.Form;
import com.unq.edu.li.pdesa.mentiUnq.models.MentiOption;
import com.unq.edu.li.pdesa.mentiUnq.models.MentiUser;
import com.unq.edu.li.pdesa.mentiUnq.models.Question;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.protocols.Status;
import com.unq.edu.li.pdesa.mentiUnq.repositories.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
public class FormService {
    private final FormRepository formRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final SlideRepository slideRepository;

    public FormService(FormRepository formRepository,
                       QuestionRepository questionRepository,
                       AnswerRepository answerRepository,
                       UserRepository userRepository,
                       SlideRepository slideRepository) {
        this.formRepository = formRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.slideRepository = slideRepository;
    }

    @Transactional
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

    @Transactional
    public ResponseUnit addQuestion(Long id, QuestionRequest question) throws Exception {
        Form foundForm = formRepository.findById(id).orElseThrow(
                ()-> EntityNotFoundException.createWith(id.toString())
        );
        Question tempQuestion = new Question();

        if(question.getSlideId()!=null && (StringUtils.isNotBlank(question.getQuestion()))) {
            tempQuestion.setQuestion(question.getQuestion());
            tempQuestion.setForm(foundForm);
            tempQuestion.setSlide(slideRepository.findById(question.getSlideId()).orElseThrow(
                    () -> EntityNotFoundException.createWith(question.getSlideId().toString())
            ));
        } else {
            throw BadRequestException.createWith("");
        }

        return new ResponseUnit(Status.SUCCESS, "", questionRepository.save(tempQuestion));
    }

    @Transactional
    public ResponseUnit addAnswer(Long formId, Long questionId, AnswerRequest answer) throws Exception {
        getForm(formId);
        Question foundQuestion = getQuestion(questionId);

        MentiOption tempAnswer = new MentiOption();

        if(answer.getOption()!=null) {
            tempAnswer.setName(answer.getOption());
            tempAnswer.setScore(0);
            tempAnswer.setQuestion(foundQuestion);
        } else {
            throw BadRequestException.createWith("");
        }

        return new ResponseUnit(Status.SUCCESS, "", answerRepository.save(tempAnswer));
    }

    private Form getForm(Long formId) throws EntityNotFoundException {
        return formRepository.findById(formId).orElseThrow(
                ()-> EntityNotFoundException.createWith(formId.toString())
        );
    }

    private Question getQuestion(Long questionId) throws EntityNotFoundException {
        return questionRepository.findById(questionId).orElseThrow(
                ()-> EntityNotFoundException.createWith(questionId.toString())
        );
    }
}