package com.unq.edu.li.pdesa.mentiUnq.repositories;

import com.unq.edu.li.pdesa.mentiUnq.models.MentiOption;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<MentiOption, Long> {
    //@Query("SELECT mo, sum(mo.score) as score FROM MentiOption mo WHERE mo.question.id = :questionId GROUP BY mo.name")
    List<MentiOption> findAllByQuestionId(Long questionId);
}
