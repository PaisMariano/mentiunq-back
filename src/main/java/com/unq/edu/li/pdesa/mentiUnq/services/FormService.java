package com.unq.edu.li.pdesa.mentiUnq.services;

import com.unq.edu.li.pdesa.mentiUnq.controllers.request.AnswerRequest;
import com.unq.edu.li.pdesa.mentiUnq.controllers.request.QuestionRequest;
import com.unq.edu.li.pdesa.mentiUnq.exceptions.BadRequestException;
import com.unq.edu.li.pdesa.mentiUnq.exceptions.EntityNotFoundException;
import com.unq.edu.li.pdesa.mentiUnq.models.Form;
import com.unq.edu.li.pdesa.mentiUnq.models.MentiOption;
import com.unq.edu.li.pdesa.mentiUnq.models.MentiUser;
import com.unq.edu.li.pdesa.mentiUnq.models.Question;
import com.unq.edu.li.pdesa.mentiUnq.models.Slide;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.protocols.Status;
import com.unq.edu.li.pdesa.mentiUnq.repositories.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
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

        form = formRepository.save(form);

        Question question = new Question();
        question.setSlide(getSlide(1l));
        question.setQuestion("Multiple Choice");
        question.setForm(form);

        form.setQuestions(Arrays.asList(questionRepository.save(question)));

        return new ResponseUnit(Status.SUCCESS, "", form);
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
            tempQuestion.setSlide(getSlide(question.getSlideId()));
        } else {
            throw BadRequestException.createWith("");
        }

        return new ResponseUnit(Status.SUCCESS, "", questionRepository.save(tempQuestion));
    }

    private Slide getSlide(Long questionId) throws EntityNotFoundException
    {
        return slideRepository.findById(questionId).orElseThrow(
                () -> EntityNotFoundException.createWith(questionId.toString())
        );
    }

    public ResponseUnit getAnswersByQuestionId(Long questionId) {
        return new ResponseUnit(Status.SUCCESS, "", answerRepository.findAllByQuestionId(questionId));
    }

    @Transactional
    public ResponseUnit addAnswer(Long formId, Long questionId, AnswerRequest answer) throws Exception {
        //validate existence
        getForm(formId);

        return new ResponseUnit(Status.SUCCESS, "", addAnswer(questionId, answer, 0));
    }

    @Transactional
    public ResponseUnit addAnswer(String codeShare, Long questionId, AnswerRequest answer) throws Exception {
        //validate existence
        getForm(codeShare);

        return new ResponseUnit(Status.SUCCESS, "", addAnswer(questionId, answer, 1));
    }

    @Transactional
    public ResponseUnit deleteQuestionById(Long formId, Long questionId) throws EntityNotFoundException, BadRequestException
    {
        Form aForm = getForm(formId);
        Question question =  getQuestion(questionId);
        if (aForm.getQuestions().size() == 1)
            throw BadRequestException.createWith(formId.toString());

        //List<Question> newQuestions = new ArrayList<>();

        //TODO: ver si hay una manera más óptima de borrar la pregunta, aunque no recorre la lista completamente, si lo encuentra terminar de iterar
        aForm.getQuestions().removeIf(aQuestion -> aQuestion.getId().equals(questionId));

        //aForm.setQuestions(newQuestions);

        //formRepository.save(aForm);
        questionRepository.delete(question);

        return new ResponseUnit(Status.SUCCESS, "", String.format("Question with id %s from Form with id %s deleted successful", questionId, formId) );
    }

    public ResponseUnit deleteOptionById(Long formId, Long optionId) throws EntityNotFoundException
    {
        Form aForm = getForm(formId);

        MentiOption mentiOption = answerRepository.findById(optionId).orElseThrow(
                ()-> EntityNotFoundException.createWith(optionId.toString())
        );

        //aForm.getQuestions().forEach(aQuestion -> deleteMentiOption(aQuestion, optionId));

        //formRepository.save(aForm);
        answerRepository.delete(mentiOption);
        return new ResponseUnit(Status.SUCCESS, "", String.format("Question with id %s from Form with id %s deleted successful", optionId, formId) );
    }

    public ResponseUnit getQuestionsById(Long formId) throws EntityNotFoundException
    {
        Form aForm = getForm(formId);

        return new ResponseUnit(Status.SUCCESS, "", aForm.getQuestions() );
    }

    public ResponseUnit getFormByCode(String code) throws EntityNotFoundException
    {
        Form aForm = formRepository.findByCode(code).orElseThrow(
                ()-> EntityNotFoundException.createWith(code)
        );

        return new ResponseUnit(Status.SUCCESS, "", aForm);
    }

    private MentiOption addAnswer(Long questionId, AnswerRequest answer, Integer score) throws Exception {
        Question foundQuestion = getQuestion(questionId);

        MentiOption tempAnswer = new MentiOption();

        if(answer.getOption()!=null) {
            tempAnswer.setName(answer.getOption());
            tempAnswer.setScore(score);
            tempAnswer.setQuestion(foundQuestion);
        } else {
            throw BadRequestException.createWith("");
        }

        return answerRepository.save(tempAnswer);
    }

    private Form getForm(Long formId) throws EntityNotFoundException {
        return formRepository.findById(formId).orElseThrow(
                ()-> EntityNotFoundException.createWith(formId.toString())
        );
    }

    private Form getForm(String codeShare) throws EntityNotFoundException {
        return formRepository.findByCodeShare(codeShare).orElseThrow(
                ()-> EntityNotFoundException.createWith(codeShare)
        );
    }

    private Question getQuestion(Long questionId) throws EntityNotFoundException {
        return questionRepository.findById(questionId).orElseThrow(
                ()-> EntityNotFoundException.createWith(questionId.toString())
        );
    }

    private void deleteMentiOption(Question aQuestion, Long optionId)
    {
        aQuestion.getMentiOptions().removeIf(mentiOption -> mentiOption.getId().equals(optionId));
    }

}