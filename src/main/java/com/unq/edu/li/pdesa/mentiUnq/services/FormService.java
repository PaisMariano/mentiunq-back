package com.unq.edu.li.pdesa.mentiUnq.services;

import com.unq.edu.li.pdesa.mentiUnq.controllers.request.FormRequest;
import com.unq.edu.li.pdesa.mentiUnq.controllers.request.QuestionRequest;
import com.unq.edu.li.pdesa.mentiUnq.exceptions.BadRequestException;
import com.unq.edu.li.pdesa.mentiUnq.exceptions.EntityNotFoundException;
import com.unq.edu.li.pdesa.mentiUnq.models.Form;
import com.unq.edu.li.pdesa.mentiUnq.models.MentiUser;
import com.unq.edu.li.pdesa.mentiUnq.models.Question;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.protocols.Status;
import com.unq.edu.li.pdesa.mentiUnq.repositories.FormRepository;
import com.unq.edu.li.pdesa.mentiUnq.repositories.SlideRepository;
import com.unq.edu.li.pdesa.mentiUnq.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class FormService {
    private final FormRepository formRepository;
    private final UserRepository userRepository;
    private final SlideRepository slideRepository;

    public FormService(FormRepository formRepository,
                       UserRepository userRepository,
                       SlideRepository slideRepository) {
        this.formRepository = formRepository;
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
    public ResponseUnit updateForm(Long id, FormRequest form) throws Exception
    {
        Form foundForm = formRepository.findById(id).orElseThrow(
                ()-> EntityNotFoundException.createWith(id.toString())
        );
        List<Question> questions = new ArrayList<>();

        for (QuestionRequest q : form.getQuestions()) {
            if(q.getSlideId()!=null && q.getQuestion()!=null) {
                Question question = new Question();
                question.setQuestion(q.getQuestion());
                question.setForm(foundForm);
                question.setSlide(slideRepository.findById(q.getSlideId()).orElseThrow(
                        () -> EntityNotFoundException.createWith(q.getSlideId().toString())
                ));
                questions.add(question);
            } else {
                throw BadRequestException.createWith("");
            }
        }
        foundForm.setQuestions(questions);

        return new ResponseUnit(Status.SUCCESS, "", formRepository.save(foundForm));
    }
}


