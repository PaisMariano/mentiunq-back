package com.unq.edu.li.pdesa.mentiUnq.services;

import com.unq.edu.li.pdesa.mentiUnq.controllers.fixtures.AnswerRequestFixture;
import com.unq.edu.li.pdesa.mentiUnq.controllers.fixtures.FormNameRequestFixture;
import com.unq.edu.li.pdesa.mentiUnq.controllers.fixtures.QuestionRequestFixture;
import com.unq.edu.li.pdesa.mentiUnq.controllers.fixtures.ResponseUnitFixture;
import com.unq.edu.li.pdesa.mentiUnq.controllers.request.AnswerRequest;
import com.unq.edu.li.pdesa.mentiUnq.controllers.request.FormNameRequest;
import com.unq.edu.li.pdesa.mentiUnq.controllers.request.QuestionRequest;
import com.unq.edu.li.pdesa.mentiUnq.exceptions.BadRequestException;
import com.unq.edu.li.pdesa.mentiUnq.exceptions.EntityNotFoundException;
import com.unq.edu.li.pdesa.mentiUnq.models.Form;
import com.unq.edu.li.pdesa.mentiUnq.models.MentiOption;
import com.unq.edu.li.pdesa.mentiUnq.models.MentiUser;
import com.unq.edu.li.pdesa.mentiUnq.models.Question;
import com.unq.edu.li.pdesa.mentiUnq.models.Slide;
import com.unq.edu.li.pdesa.mentiUnq.models.SlideType;
import com.unq.edu.li.pdesa.mentiUnq.models.SlideTypeEnum;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.repositories.AnswerRepository;
import com.unq.edu.li.pdesa.mentiUnq.repositories.FormRepository;
import com.unq.edu.li.pdesa.mentiUnq.repositories.QuestionRepository;
import com.unq.edu.li.pdesa.mentiUnq.repositories.SlideRepository;
import com.unq.edu.li.pdesa.mentiUnq.repositories.UserRepository;
import com.unq.edu.li.pdesa.mentiUnq.services.fixtures.MentiOptionFixture;
import com.unq.edu.li.pdesa.mentiUnq.services.fixtures.QuestionFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FormServiceTest
{
	@InjectMocks
	private FormService service;

	@Mock
	private FormRepository formRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private QuestionRepository questionRepository;

	@Mock
	private SlideRepository slideRepository;

	@Mock
	private AnswerRepository answerRepository;

	@Mock
	private Form aForm;

	Long userId = 1l;
	Long formId = 1l;
	Long questionId = 1l;
	String codeShare = "CODE";
	Long optionId = 1l;

	@Test
	public void testWhenTriesToCreateFormReturnsResponseUnitWithPayloadAsStringWithFormData() throws EntityNotFoundException {

		Form form = mock(Form.class);
		Optional<MentiUser> userOptional = Optional.of(mock(MentiUser.class));
		Optional<Slide> slideOptional = Optional.of(mock(Slide.class));

		when(userRepository.findById(anyLong())).thenReturn(userOptional);
		when(formRepository.save(any(Form.class))).thenReturn(form);
		when(slideRepository.findById(anyLong())).thenReturn(slideOptional);

		ResponseUnit responseUnit = service.createForm(userId);

		assertNotNull(responseUnit);

		verify(userRepository).findById(anyLong());
		verify(formRepository).save(any(Form.class));
		verify(questionRepository).save(any(Question.class));
	}

	@Test
	public void testWhenTriesToCreateFormAndUserDoestNotExistRaiseEntityNotFoundException() {

		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

		Exception exception = assertThrows(EntityNotFoundException.class, () -> {
			service.createForm(userId);
		});

		String expectedMessage = "Entity '" + userId + "' not found";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
		verify(userRepository).findById(anyLong());
		verify(formRepository, times(0)).save(any(Form.class));
		verify(questionRepository, times(0)).save(any(Question.class));
	}

	@Test
	public void testWhenTriesToCreateFormAndSlideDoestNotExistRaiseEntityNotFoundException() {

		Optional<MentiUser> userOptional = Optional.of(mock(MentiUser.class));

		when(userRepository.findById(anyLong())).thenReturn(userOptional);
		when(slideRepository.findById(anyLong())).thenReturn(Optional.empty());

		Exception exception = assertThrows(EntityNotFoundException.class, () -> {
			service.createForm(userId);
		});

		assertEquals("Entity '" + userId + "' not found", exception.getMessage());
		verify(userRepository).findById(anyLong());
		verify(formRepository).save(any(Form.class));
		verify(questionRepository, times(0)).save(any(Question.class));
	}

	@Test
	public void testWhenAddQuestionWithValidRequestValidResponseIsNotNull() throws Exception {
		Form form = mock(Form.class);
		Optional<Form> formOptional = Optional.of(form);
		Optional<Slide> slideOptional = Optional.of(mock(Slide.class));
		QuestionRequest request = QuestionRequestFixture.withDefaults();

		when(formRepository.findById(anyLong())).thenReturn(formOptional);
		when(slideRepository.findById(anyLong())).thenReturn(slideOptional);

		ResponseUnit responseUnit = service.addQuestion(formId, request);

		assertNotNull(responseUnit);
		verify(formRepository).findById(anyLong());
		verify(slideRepository).findById(anyLong());
	}

	@Test
	public void testWhenAddQuestionWithQuestionRequestWithSlideIdNullThenRaiseBadRequestException(){
		Form form = mock(Form.class);
		Optional<Form> formOptional = Optional.of(form);
		QuestionRequest request = QuestionRequestFixture.withSlideIdNull();

		when(formRepository.findById(anyLong())).thenReturn(formOptional);

		Exception exception = assertThrows(BadRequestException.class, () -> {
			service.addQuestion(formId, request);
		});

		assertEquals("Request body is wrong", exception.getMessage());
		verify(formRepository).findById(anyLong());
	}

	@Test
	public void testWhenAddQuestionWithQuestionRequestWitQuestionEmptyThenRaiseBadRequestException(){
		Form form = mock(Form.class);
		Optional<Form> formOptional = Optional.of(form);
		QuestionRequest request = QuestionRequestFixture.withQuestionEmpty();

		when(formRepository.findById(anyLong())).thenReturn(formOptional);

		Exception exception = assertThrows(BadRequestException.class, () -> {
			service.addQuestion(formId, request);
		});

		assertEquals("Request body is wrong", exception.getMessage());
		verify(formRepository).findById(anyLong());
	}

	@Test
	public void testWhenGetAnswersByQuestionIdReturnsEmptyContent(){
		ResponseUnit responseUnit = service.getAnswersByQuestionId(questionId);

		assertNotNull(responseUnit);
		verify(answerRepository).findAllByQuestionId(anyLong());
	}

	@Test
	public void testAddAnswerByFormId() throws Exception {
		AnswerRequest requestFixture = AnswerRequestFixture.withDefaults();
		Optional<Question> question = Optional.of(mock(Question.class));

		when(formRepository.findById(anyLong())).thenReturn(Optional.of(mock(Form.class)));
		when(questionRepository.findById(anyLong())).thenReturn(question);

		ResponseUnit responseUnit = service.addAnswer(formId, questionId, requestFixture);

		assertNotNull(responseUnit);
		verify(formRepository).findById(anyLong());
		verify(questionRepository).findById(anyLong());
	}

	@Test
	public void testAddAnswerByFormIdAndDoesNotFoundFormReturnsEntityNotFoundException(){
		AnswerRequest requestFixture = AnswerRequestFixture.withDefaults();

		when(formRepository.findById(anyLong())).thenReturn(Optional.empty());

		Exception exception = assertThrows(EntityNotFoundException.class, () -> {
			service.addAnswer(formId, questionId, requestFixture);
		});

		assertEquals("Entity '1' not found", exception.getMessage());
		verify(formRepository).findById(anyLong());
	}

	@Test
	public void testAddAnswerByCodeShare() throws Exception {
		AnswerRequest requestFixture = AnswerRequestFixture.withDefaults();
		Optional<Question> question = Optional.of(mock(Question.class));

		when(formRepository.findByCodeShare(anyString())).thenReturn(Optional.of(mock(Form.class)));
		when(questionRepository.findById(anyLong())).thenReturn(question);

		ResponseUnit responseUnit = service.addAnswer(codeShare, questionId, requestFixture);

		assertNotNull(responseUnit);
		verify(formRepository).findByCodeShare(anyString());
		verify(questionRepository).findById(anyLong());
	}

	@Test
	public void testAddAnswerByCodeShareAndDoesNotFoundFormReturnsEntityNotFoundException(){
		AnswerRequest requestFixture = AnswerRequestFixture.withDefaults();

		when(formRepository.findByCodeShare(anyString())).thenReturn(Optional.empty());

		Exception exception = assertThrows(EntityNotFoundException.class, () -> {
			service.addAnswer(codeShare, questionId, requestFixture);
		});

		assertEquals("Entity 'CODE' not found", exception.getMessage());
		verify(formRepository).findByCodeShare(anyString());
	}

	@Test
	public void testDeletesQuestionByIdAndFormHasOneQuestionThenRaiseBadRequestException(){

		Form aForm = mock(Form.class);
		Optional<Question> questionOptional = Optional.of(mock(Question.class));
		Optional<Form> formOptional = Optional.of(aForm);

		when(formRepository.findById(anyLong())).thenReturn(formOptional);
		when(questionRepository.findById(anyLong())).thenReturn(questionOptional);
		when(aForm.getQuestions()).thenReturn(Arrays.asList(QuestionFixture.withDefaults()));

		Exception exception = assertThrows(BadRequestException.class, () -> {
			service.deleteQuestionById(formId, questionId);
		});

		assertEquals("Request body is wrong", exception.getMessage());
		verify(formRepository).findById(anyLong());
		verify(questionRepository).findById(anyLong());
	}

	@Test
	public void testDeletesQuestionByIdAndFormHasMoreThanOneQuestionThenProcessOk() throws BadRequestException, EntityNotFoundException {

		Form aForm = mock(Form.class);
		Optional<Question> questionOptional = Optional.of(mock(Question.class));
		Optional<Form> formOptional = Optional.of(aForm);

		when(formRepository.findById(anyLong())).thenReturn(formOptional);
		when(questionRepository.findById(anyLong())).thenReturn(questionOptional);
		when(aForm.getQuestions()).thenReturn(Arrays.asList(QuestionFixture.withDefaults(), QuestionFixture.withDefaults()));

		service.deleteQuestionById(formId, questionId);

		verify(formRepository).findById(anyLong());
		verify(questionRepository).findById(anyLong());
		verify(formRepository).save(any(Form.class));
	}

	@Test
	public void testDeletesQuestionByIdAndFormHasMoreThanOneQuestionAndHasDifferenteIdThenProcessOk() throws BadRequestException, EntityNotFoundException {

		Form aForm = mock(Form.class);
		Question aQuestion = mock(Question.class);
		Optional<Question> questionOptional = Optional.of(aQuestion);
		Optional<Form> formOptional = Optional.of(aForm);
		Question oneQuestion = QuestionFixture.withDefaults();
		oneQuestion.setId(2l);

		when(formRepository.findById(anyLong())).thenReturn(formOptional);
		when(questionRepository.findById(anyLong())).thenReturn(questionOptional);
		when(aForm.getQuestions()).thenReturn(Arrays.asList(oneQuestion, QuestionFixture.withDefaults()));
		when(aQuestion.getIsCurrent()).thenReturn(true);

		ResponseUnit responseUnit = service.deleteQuestionById(formId, questionId);

		assertNotNull(responseUnit);
		verify(formRepository).findById(anyLong());
		verify(questionRepository).findById(anyLong());
		verify(formRepository).save(any(Form.class));
	}

	@Test
	public void testDeletesOptionByIdAndOptionIdDoesNotExistThenRaiseEntityNotFoundException(){
		when(formRepository.findById(anyLong())).thenReturn(Optional.of(mock(Form.class)));
		when(answerRepository.findById(anyLong())).thenReturn(Optional.empty());

		Exception exception = assertThrows(EntityNotFoundException.class, () -> {
			service.deleteOptionById(formId, optionId);
		});

		assertEquals("Entity '1' not found", exception.getMessage());
		verify(formRepository).findById(anyLong());
		verify(answerRepository).findById(anyLong());
	}

	@Test
	public void testDeletesOptionByIdAndOptionIdExistThenProcessOk() throws EntityNotFoundException {
		when(formRepository.findById(anyLong())).thenReturn(Optional.of(mock(Form.class)));
		when(answerRepository.findById(anyLong())).thenReturn(Optional.of(mock(MentiOption.class)));

		ResponseUnit responseUnit = service.deleteOptionById(formId, optionId);

		assertNotNull(responseUnit);
		verify(formRepository).findById(anyLong());
		verify(answerRepository).findById(anyLong());
	}

	@Test
	public void testGetQuestionByIdThenProcessOk() throws EntityNotFoundException {
		when(formRepository.findById(anyLong())).thenReturn(Optional.of(mock(Form.class)));

		ResponseUnit responseUnit = service.getQuestionsById(formId);

		assertNotNull(responseUnit);
		verify(formRepository).findById(anyLong());
	}

	@Test
	public void testGetFormByCodeThenProcessOk() throws EntityNotFoundException {
		when(formRepository.findByCode(anyString())).thenReturn(Optional.of(mock(Form.class)));

		ResponseUnit responseUnit = service.getFormByCode(codeShare);

		assertNotNull(responseUnit);
		verify(formRepository).findByCode(anyString());
	}

	@Test
	public void testGetFormByCodeAndDoesNotExistThenReturnEntityNotFoundException(){
		when(formRepository.findByCode(anyString())).thenReturn(Optional.empty());

		Exception exception = assertThrows(EntityNotFoundException.class, () -> {
			service.getFormByCode(codeShare);
		});

		assertEquals("Entity 'CODE' not found", exception.getMessage());
		verify(formRepository).findByCode(anyString());
	}

	@Test
	public void testGetFormByCodeShareThenProcessOk() throws EntityNotFoundException {
		Form aForm = mock(Form.class);
		List<Question> questions = Arrays.asList(QuestionFixture.withDefaults());

		when(formRepository.findByCodeShare(anyString())).thenReturn(Optional.of(aForm));
		when(aForm.getQuestions()).thenReturn(questions);

		ResponseUnit responseUnit = service.getQuestionByCodeShare(codeShare);

		assertNotNull(responseUnit);
		verify(formRepository).findByCodeShare(anyString());
	}

	@Test
	public void testUpdateCurrentQuestionThenProcessOk() throws EntityNotFoundException {
		Form aForm = mock(Form.class);
		List<Question> questions = Arrays.asList(QuestionFixture.withDefaults());

		when(formRepository.findById(anyLong())).thenReturn(Optional.of(aForm));
		when(questionRepository.findById(anyLong())).thenReturn(Optional.of(mock(Question.class)));
		when(aForm.getQuestions()).thenReturn(questions);

		ResponseUnit responseUnit = service.updateCurrentQuestion(formId, questionId);

		assertNotNull(responseUnit);
		verify(formRepository).findById(anyLong());
		verify(questionRepository).findById(anyLong());
	}

	@Test
	public void testUpdateCurrentQuestionAndIsNotCurrentThenProcessOk() throws EntityNotFoundException {
		Form aForm = mock(Form.class);
		List<Question> questions = Arrays.asList(QuestionFixture.withDefaults());

		when(formRepository.findById(anyLong())).thenReturn(Optional.of(aForm));
		when(questionRepository.findById(anyLong())).thenReturn(Optional.of(mock(Question.class)));
		when(aForm.getQuestions()).thenReturn(questions);

		ResponseUnit responseUnit = service.updateCurrentQuestion(formId, 2l);

		assertNotNull(responseUnit);
		verify(formRepository).findById(anyLong());
		verify(questionRepository).findById(anyLong());
	}

	@Test
	public void testVoteProcessOk() throws EntityNotFoundException {
		Question aQuestion = mock(Question.class);
		List<MentiOption> options = Arrays.asList(MentiOptionFixture.withDefaults());

		when(formRepository.findByCodeShare(anyString())).thenReturn(Optional.of(mock(Form.class)));
		when(questionRepository.findById(anyLong())).thenReturn(Optional.of(aQuestion));
		when(aQuestion.getMentiOptions()).thenReturn(options);

		ResponseUnit responseUnit = service.vote(codeShare, questionId, optionId);

		assertNotNull(responseUnit);
		verify(formRepository).findByCodeShare(anyString());
		verify(formRepository).save(any(Form.class));
		verify(questionRepository).findById(anyLong());
	}

	@Test
	public void testVoteWithDifferentOptionIdProcessOk() throws EntityNotFoundException {
		Question aQuestion = mock(Question.class);
		List<MentiOption> options = Arrays.asList(MentiOptionFixture.withDefaults());

		when(formRepository.findByCodeShare(anyString())).thenReturn(Optional.of(mock(Form.class)));
		when(questionRepository.findById(anyLong())).thenReturn(Optional.of(aQuestion));
		when(aQuestion.getMentiOptions()).thenReturn(options);

		ResponseUnit responseUnit = service.vote(codeShare, questionId, 2l);

		assertNotNull(responseUnit);
		verify(formRepository).findByCodeShare(anyString());
		verify(formRepository).save(any(Form.class));
		verify(questionRepository).findById(anyLong());
	}

	@Test
	public void testDeleteFormByIdWhenFormIdExistReturnValidResponseUnit() throws EntityNotFoundException
	{
		Optional<Form> formOptional = Optional.of(mock(Form.class));

		when(formRepository.findById(anyLong())).thenReturn(formOptional);

		ResponseUnit responseUnit = service.deleteFormById(formId);
		ResponseUnit expected = ResponseUnitFixture.withOkDeleteForm();

		assertNotNull(responseUnit);
		assertEquals(expected.getStatus(), responseUnit.getStatus());
		assertEquals(expected.getMessage(), responseUnit.getMessage());
		verify(formRepository).findById(anyLong());
	}

	@Test
	public void testDeletesOptionByIdAndOptionIdExistAndFormHasQuestionThenProcessOk() throws EntityNotFoundException {
		Form form = mock(Form.class);
		Question aQuestion = mock(Question.class);
		MentiOption option = mock(MentiOption.class);
		MentiOption anotherOption = mock(MentiOption.class);
		List<Question> questions = Arrays.asList(aQuestion);
		List<MentiOption> options = new ArrayList<>();
		options.add(option);
		options.add(anotherOption);

		when(formRepository.findById(anyLong())).thenReturn(Optional.of(form));
		when(answerRepository.findById(anyLong())).thenReturn(Optional.of(mock(MentiOption.class)));
		when(aQuestion.getMentiOptions()).thenReturn(options);
		when(form.getQuestions()).thenReturn(questions);
		when(option.getId()).thenReturn(optionId);

		ResponseUnit responseUnit = service.deleteOptionById(formId, optionId);

		assertNotNull(responseUnit);
		verify(formRepository).findById(anyLong());
		verify(answerRepository).findById(anyLong());
		verify(answerRepository).delete(any(MentiOption.class));
	}

	@Test
	public void testRenameFormWhenFormIdExistThenReturnsAValidResponse() throws EntityNotFoundException
	{
		FormNameRequest request = FormNameRequestFixture.withDefaults();

		when(formRepository.findById(anyLong())).thenReturn(Optional.of(mock(Form.class)));

		ResponseUnit responseUnit = service.renameForm(formId, request);

		assertNotNull(responseUnit);
		verify(formRepository).findById(anyLong());
	}

	@Test
	public void whenUpdateNameQuestionAndFormIdExistThenReturnAValidResponse() throws EntityNotFoundException
	{
		QuestionRequest request = QuestionRequestFixture.withDefaults();

		when(formRepository.findById(anyLong())).thenReturn(Optional.of(mock(Form.class)));
		when(questionRepository.findById(anyLong())).thenReturn(Optional.of(mock(Question.class)));

		ResponseUnit responseUnit = service.updateNameQuestion(formId, questionId, request);

		assertNotNull(responseUnit);
		verify(formRepository).findById(anyLong());
		verify(questionRepository).findById(anyLong());
	}

	@Test
	public void whenUpdateNameOptionAndFormIdExistThenReturnAValidResponse() throws EntityNotFoundException
	{
		AnswerRequest request = AnswerRequestFixture.withDefaults();

		when(formRepository.findById(anyLong())).thenReturn(Optional.of(mock(Form.class)));
		when(answerRepository.findById(anyLong())).thenReturn(Optional.of(mock(MentiOption.class)));

		ResponseUnit responseUnit = service.updateNameOption(formId, questionId, request);

		assertNotNull(responseUnit);
		verify(formRepository).findById(anyLong());
		verify(answerRepository).findById(anyLong());

	}

	@Test
	public void whenTryToGetResultsByFormCodeAndCodeDoesNotExistThenRaiseEntityNotFoundException(){
		when(formRepository.findByCode(anyString())).thenReturn(Optional.empty());

		Exception exception = assertThrows(EntityNotFoundException.class, () -> {
			service.getResultsByFormCode(codeShare);
		});

		assertEquals("Entity 'CODE' not found", exception.getMessage());
		verify(formRepository).findByCode(anyString());
	}

	@Test
	public void whenTryToGetResultsByFormCodeAndCodeExistThenReturnAValidResponseUnit() throws EntityNotFoundException
	{
		MentiOption option = mock(MentiOption.class);
		Question aQuestion = mock(Question.class);
		Slide aSlide = mock(Slide.class);
		SlideType slideType = mock(SlideType.class);
		List<Question> questions = Arrays.asList(aQuestion, aQuestion, aQuestion);
		List<MentiOption> options = Arrays.asList(option);

		when(formRepository.findByCode(anyString())).thenReturn(Optional.of(aForm));
		when(aForm.getQuestions()).thenReturn(questions);
		when(aQuestion.getMentiOptions()).thenReturn(options);
		when(option.getScore()).thenReturn(1);
		when(aQuestion.getSlide()).thenReturn(aSlide);
		when(aSlide.getSlideType()).thenReturn(slideType);
		when(slideType.getName()).thenReturn("Abierta").thenReturn("Cerrada").thenReturn("Contenido");

		ResponseUnit responseUnit = service.getResultsByFormCode(codeShare);
		ResponseUnit expected = ResponseUnitFixture.withOkResultsFormByCode();

		assertNotNull(responseUnit);
		assertEquals(expected.getMessage(), responseUnit.getMessage());
		assertEquals(expected.getStatus(), responseUnit.getStatus());
		verify(formRepository).findByCode(anyString());
	}

	@Test
	public void whenTryToAddResponseInAQuestionThenReturnAValidResponseUnit() throws EntityNotFoundException
	{
		Question aQuestion = mock(Question.class);
		AnswerRequest request = AnswerRequestFixture.withDefaults();

		when(formRepository.findByCodeShare(anyString())).thenReturn(Optional.of(aForm));
		when(questionRepository.findById(anyLong())).thenReturn(Optional.of(aQuestion));

		ResponseUnit responseUnit = service.addResponse(codeShare, questionId, request);
		ResponseUnit expected = ResponseUnitFixture.withOkQuestion();

		assertNotNull(responseUnit);
		assertEquals(expected.getMessage(), responseUnit.getMessage());
		assertEquals(expected.getStatus(), responseUnit.getStatus());
		verify(formRepository).findByCodeShare(anyString());
		verify(questionRepository).findById(anyLong());
		verify(answerRepository).save(any(MentiOption.class));
	}

	@Test
	public void whenTryToUpdateContentInAQuestionAndHasNotAnOptionThenReturnAValidResponseUnit() throws EntityNotFoundException
	{
		Question aQuestion = mock(Question.class);
		QuestionRequest request = QuestionRequestFixture.withDefaults();

		when(formRepository.findByCodeShare(anyString())).thenReturn(Optional.of(aForm));
		when(questionRepository.findById(anyLong())).thenReturn(Optional.of(aQuestion));
		when(aQuestion.getMentiOptions()).thenReturn(new ArrayList<>());

		ResponseUnit responseUnit = service.updateContent(codeShare, questionId, request);
		ResponseUnit expected = ResponseUnitFixture.withOkQuestion();

		assertNotNull(responseUnit);
		assertEquals(expected.getMessage(), responseUnit.getMessage());
		assertEquals(expected.getStatus(), responseUnit.getStatus());
		verify(formRepository).findByCodeShare(anyString());
		verify(questionRepository).findById(anyLong());
		verify(answerRepository).save(any(MentiOption.class));
	}

	@Test
	public void whenTryToUpdateContentInAQuestionAndHasAnOptionThenReturnAValidResponseUnit() throws EntityNotFoundException
	{
		Question aQuestion = mock(Question.class);
		QuestionRequest request = QuestionRequestFixture.withDefaults();
		MentiOption mentiOption = mock(MentiOption.class);

		when(formRepository.findByCodeShare(anyString())).thenReturn(Optional.of(aForm));
		when(questionRepository.findById(anyLong())).thenReturn(Optional.of(aQuestion));
		when(aQuestion.getMentiOptions()).thenReturn(Arrays.asList(mentiOption));

		ResponseUnit responseUnit = service.updateContent(codeShare, questionId, request);
		ResponseUnit expected = ResponseUnitFixture.withOkQuestion();

		assertNotNull(responseUnit);
		assertEquals(expected.getMessage(), responseUnit.getMessage());
		assertEquals(expected.getStatus(), responseUnit.getStatus());
		verify(formRepository).findByCodeShare(anyString());
		verify(questionRepository).findById(anyLong());
		verify(answerRepository).save(any(MentiOption.class));
	}

	@Test
	public void whenTryToEndFormThenReturnAValidResponseUnit() throws EntityNotFoundException {
		when(formRepository.findById(anyLong())).thenReturn(Optional.of(mock(Form.class)));

		ResponseUnit responseUnit = service.endForm(formId);

		assertNotNull(responseUnit);
		verify(formRepository).findById(anyLong());
		verify(formRepository).save(any(Form.class));
	}

	@Test
	public void whenDuplicateFormAndOneQuestionAndIsNotCloseSlideThenReturnAFormWithOneQuestion() throws EntityNotFoundException {

		Question aQuestion = mock(Question.class);
		Slide aSlide = mock(Slide.class);
		SlideType aSlideType = mock(SlideType.class);
		List<Question> questions = Arrays.asList(aQuestion);

		when(formRepository.findById(anyLong())).thenReturn(Optional.of(aForm));
		when(aForm.getQuestions()).thenReturn(questions);
		when(aQuestion.getQuestion()).thenReturn("QUESTION");
		when(aQuestion.getSlide()).thenReturn(aSlide);
		when(aQuestion.getIsCurrent()).thenReturn(true);
		when(aSlide.getSlideType()).thenReturn(aSlideType);
		when(aSlideType.getName()).thenReturn(SlideTypeEnum.OPEN.getSlideType());

		ResponseUnit responseUnit = service.duplicate(formId);

		assertNotNull(responseUnit);
		verify(formRepository).findById(anyLong());
		verify(formRepository).save(any(Form.class));
	}

	@Test
	public void whenDuplicateFormAndOneQuestionAndIsCloseSlideThenReturnAFormWithOneQuestion() throws EntityNotFoundException {

		Question aQuestion = mock(Question.class);
		Slide aSlide = mock(Slide.class);
		SlideType aSlideType = mock(SlideType.class);
		List<Question> questions = Arrays.asList(aQuestion);
		MentiOption mentiOption = mock(MentiOption.class);
		List<MentiOption> mentiOptions = Arrays.asList(mentiOption);

		when(formRepository.findById(anyLong())).thenReturn(Optional.of(aForm));
		when(aForm.getQuestions()).thenReturn(questions);
		when(aQuestion.getQuestion()).thenReturn("QUESTION");
		when(aQuestion.getSlide()).thenReturn(aSlide);
		when(aQuestion.getIsCurrent()).thenReturn(true);
		when(aSlide.getSlideType()).thenReturn(aSlideType);
		when(aSlideType.getName()).thenReturn(SlideTypeEnum.CLOSE.getSlideType());
		when(aQuestion.getMentiOptions()).thenReturn(mentiOptions);
		when(mentiOption.getName()).thenReturn("NAME");

		ResponseUnit responseUnit = service.duplicate(formId);

		assertNotNull(responseUnit);
		verify(formRepository).findById(anyLong());
		verify(formRepository).save(any(Form.class));
	}

	@Test
	public void testGetFormByShareCodeThenProcessOk() throws EntityNotFoundException {
		when(formRepository.findByCodeShare(anyString())).thenReturn(Optional.of(mock(Form.class)));

		ResponseUnit responseUnit = service.getFormByFormCode(codeShare);

		assertNotNull(responseUnit);
		verify(formRepository).findByCodeShare(anyString());
	}
}
