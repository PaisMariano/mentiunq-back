package com.unq.edu.li.pdesa.mentiUnq.repositories;

import com.unq.edu.li.pdesa.mentiUnq.models.MentiOption;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<MentiOption, Long> {

    List<MentiOption> findAllByQuestionId(Long questionId);
}
