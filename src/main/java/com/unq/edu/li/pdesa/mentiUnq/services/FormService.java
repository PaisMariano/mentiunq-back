package com.unq.edu.li.pdesa.mentiUnq.services;

import com.unq.edu.li.pdesa.mentiUnq.controllers.request.AnswerRequest;
import com.unq.edu.li.pdesa.mentiUnq.controllers.request.FormNameRequest;
import com.unq.edu.li.pdesa.mentiUnq.controllers.request.QuestionRequest;
import com.unq.edu.li.pdesa.mentiUnq.controllers.response.ResultResponse;
import com.unq.edu.li.pdesa.mentiUnq.exceptions.BadRequestException;
import com.unq.edu.li.pdesa.mentiUnq.exceptions.EntityNotFoundException;
import com.unq.edu.li.pdesa.mentiUnq.models.*;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.protocols.Status;
import com.unq.edu.li.pdesa.mentiUnq.repositories.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        String codeShare = generateCodeShareByCode(code);

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
        question.setIsCurrent(Boolean.TRUE);
        question.setForm(form);

        form.setQuestions(Arrays.asList(questionRepository.save(question)));

        return new ResponseUnit(Status.SUCCESS, "", form);
    }

	private static String generateCodeShareByCode(String code)
	{
		return Base64.getEncoder().encodeToString(code.getBytes(StandardCharsets.UTF_8)).substring(0, 8).toUpperCase();
	}

	@Transactional
    public ResponseUnit addQuestion(Long id, QuestionRequest question) throws Exception {
        Form foundForm = getFormById(id);

        Question tempQuestion = new Question();

        if(question.getSlideId()!=null && (StringUtils.isNotBlank(question.getQuestion()))) {
            tempQuestion.setQuestion(question.getQuestion());
            tempQuestion.setForm(foundForm);
            tempQuestion.setSlide(getSlide(question.getSlideId()));
			tempQuestion.setIsCurrent(false);
        } else {
            throw BadRequestException.createWith("");
        }

        return new ResponseUnit(Status.SUCCESS, "", questionRepository.save(tempQuestion));
    }

    public ResponseUnit getAnswersByQuestionId(Long questionId) {
        return new ResponseUnit(Status.SUCCESS, "", answerRepository.findAllByQuestionId(questionId));
    }

    @Transactional
    public ResponseUnit addAnswer(Long formId, Long questionId, AnswerRequest answer) throws Exception {
        //validate existence
        getFormById(formId);

        return new ResponseUnit(Status.SUCCESS, "", addAnswer(questionId, answer, 0));
    }

    @Transactional
    public ResponseUnit addAnswer(String codeShare, Long questionId, AnswerRequest answer) throws Exception {
        //validate existence
        getFormByCodeShare(codeShare);

        return new ResponseUnit(Status.SUCCESS, "", addAnswer(questionId, answer, 1));
    }

    public ResponseUnit deleteFormById(Long formId) throws EntityNotFoundException {
        Form aForm = getFormById(formId);
        formRepository.delete(aForm);

        return new ResponseUnit(Status.SUCCESS, "", String.format("Form with id %s deleted successfully", formId));
    }

    @Transactional
    public ResponseUnit deleteQuestionById(Long formId, Long questionId) throws EntityNotFoundException, BadRequestException
    {
        Form aForm = getFormById(formId);
        Question question =  getQuestion(questionId);
        if (aForm.getQuestions().size() == 1)
            throw BadRequestException.createWith(formId.toString());

		List<Question> questions = aForm.getQuestions();

		List<Question> tempList = new ArrayList<>();

		for(Question _question : questions){
			if(_question.getId() != questionId){
				tempList.add(_question);
			}else{
				questionRepository.delete(_question);
			}
		}

		aForm.setQuestions(tempList);

		if (question.getIsCurrent()){
			aForm.getQuestions().get(0).setIsCurrent(true);
		}

		formRepository.save(aForm);

        return new ResponseUnit(Status.SUCCESS, "", String.format("Question with id %s from Form with id %s deleted successful", questionId, formId) );
    }

    public ResponseUnit deleteOptionById(Long formId, Long optionId) throws EntityNotFoundException
    {
		Form aForm = getFormById(formId);

        MentiOption mentiOption = getMentiOptionById(optionId);

		List<Question> tempList = new ArrayList<>();
		List<Question> currentQuestions = aForm.getQuestions();

		for(Question _question : currentQuestions){
			Boolean isRemoved = _question.getMentiOptions().removeIf(_option -> _option.getId() == optionId);
			tempList.add(_question);
			if(isRemoved){
				answerRepository.delete(mentiOption);
				break;
			}
		}

		aForm.setQuestions(tempList);

        return new ResponseUnit(Status.SUCCESS, "", formRepository.save(aForm));
    }

    public ResponseUnit getQuestionsById(Long formId) throws EntityNotFoundException
    {
        Form aForm = getFormById(formId);

        return new ResponseUnit(Status.SUCCESS, "", aForm.getQuestions() );
    }

    public ResponseUnit getFormByCode(String code) throws EntityNotFoundException
    {
        Form aForm = formRepository.findByCode(code).orElseThrow(
                ()-> EntityNotFoundException.createWith(code)
        );

        return new ResponseUnit(Status.SUCCESS, "", aForm);
    }

    public ResponseUnit getQuestionByCodeShare(String codeShare) throws EntityNotFoundException {
        Form form = getFormByCodeShare(codeShare);

        return new ResponseUnit(Status.SUCCESS, "", getCurrentQuestion(form));
    }

    public ResponseUnit updateCurrentQuestion(Long formId, Long questionId) throws EntityNotFoundException
    {
        Form aForm = getFormById(formId);
        Question aQuestion = getQuestion(questionId);

        List<Question> questions = aForm.getQuestions();

        questions.forEach(question -> {
            question.setIsCurrent(false);

            if(question.getId() == questionId){
                aQuestion.setIsCurrent(true);
            }
        });


        return new ResponseUnit(Status.SUCCESS, "", formRepository.save(aForm));
    }

    public ResponseUnit vote(String codeShare, Long questionId, Long optionId) throws EntityNotFoundException
    {
        Form aForm = getFormByCodeShare(codeShare);
        Question aQuestion = getQuestion(questionId);

        aQuestion.getMentiOptions().forEach(mentiOption -> {
            if(mentiOption.getId() == optionId){
                mentiOption.setScore(mentiOption.getScore() + 1);
            }
        });

        return new ResponseUnit(Status.SUCCESS, "", formRepository.save(aForm));
    }

    private Question getCurrentQuestion(Form form) {
        Question tempQuestion = form.getQuestions().get(0);
        for (Question question : form.getQuestions()) {
            if (question.getIsCurrent()) {
                tempQuestion = question;
            }
        }
        return tempQuestion;
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

    private Form getFormById(Long formId) throws EntityNotFoundException {
        return formRepository.findById(formId).orElseThrow(
                ()-> EntityNotFoundException.createWith(formId.toString())
        );
    }

    private Form getFormByCodeShare(String codeShare) throws EntityNotFoundException {
        return formRepository.findByCodeShare(codeShare).orElseThrow(
                ()-> EntityNotFoundException.createWith(codeShare)
        );
    }

    private Form getFormByModelCode(String code) throws EntityNotFoundException {
        return formRepository.findByCode(code).orElseThrow(
                ()-> EntityNotFoundException.createWith(code)
        );
    }

    private Question getQuestion(Long questionId) throws EntityNotFoundException {
        return questionRepository.findById(questionId).orElseThrow(
                ()-> EntityNotFoundException.createWith(questionId.toString())
        );
    }

    private MentiOption getMentiOptionById(Long optionId) throws EntityNotFoundException {
        return answerRepository.findById(optionId).orElseThrow(
                () -> EntityNotFoundException.createWith(optionId.toString())
        );
    }


    private Slide getSlide(Long questionId) throws EntityNotFoundException
	{
		return slideRepository.findById(questionId).orElseThrow(
				() -> EntityNotFoundException.createWith(questionId.toString())
		);
	}

    public ResponseUnit renameForm(Long formId, FormNameRequest nameRequest) throws EntityNotFoundException {
        Form aForm = getFormById(formId);

        aForm.setName(nameRequest.getName());

        return new ResponseUnit(Status.SUCCESS, "", formRepository.save(aForm));
    }

	public ResponseUnit updateNameQuestion(Long formId, Long questionId,
										   QuestionRequest request) throws EntityNotFoundException
	{
		getFormById(formId);
		Question question = getQuestion(questionId);

		question.setQuestion(request.getQuestion());

		return new ResponseUnit(Status.SUCCESS, "", questionRepository.save(question));
	}

	public ResponseUnit updateNameOption(Long formId, Long optionId, AnswerRequest request) throws EntityNotFoundException
	{
		getFormById(formId);
		MentiOption option = getMentiOptionById(optionId);

		option.setName(request.getOption());

		return new ResponseUnit(Status.SUCCESS, "", answerRepository.save(option));
	}

    public ResponseUnit getResultsByFormCode(String formCode) throws EntityNotFoundException {
        Form aForm = getFormByModelCode(formCode);

        ResultResponse resultResponse = new ResultResponse();

        resultResponse.setSlides(aForm.getQuestions().size());

        for (Question q : aForm.getQuestions()){
            for(MentiOption mo : q.getMentiOptions()){
                resultResponse.setVotes(resultResponse.getVotes() + mo.getScore());
            }
            resultResponse.setCloseSlides(resultResponse.getCloseSlides()+1);
        }

        return new ResponseUnit(Status.SUCCESS, "", resultResponse);
    }

	public ResponseUnit duplicate(Long formId) throws EntityNotFoundException
	{
		Form aForm = getFormById(formId);
		Form aNewForm = new Form();
		String code = UUID.randomUUID().toString();
		String codeShare = generateCodeShareByCode(code);

		/*List<Question> newQuestions = aForm.getQuestions().stream().map(_question->{
			Question newQuestion = new Question();
			if(_question.getSlide().getSlydeType().getName().equals("Abierta")){
				//duplicar solo preguntas
			}

			if(_question.getSlide().getSlydeType().getName().equals("Cerrada")){
				//duplicar solo inlcuido las opciones pero con score en 0
			}

			if(_question.getSlide().getSlydeType().getName().equals("Contenido")){
				//duplicar t0do en base a
			}
		}).collect(Collectors.toList());
		*/
		aNewForm.setMentiUser(aForm.getMentiUser());
		aNewForm.setName(aForm.getName());
		aNewForm.setCode(code);
		aNewForm.setCodeShare(codeShare);
		aNewForm.setCreationDate(LocalDateTime.now());
		aNewForm.setUpdateDate(LocalDateTime.now());
		//aNewForm.setQuestions(newQuestions);
		return new ResponseUnit(Status.SUCCESS, "", formRepository.save(aNewForm));
	}
}
