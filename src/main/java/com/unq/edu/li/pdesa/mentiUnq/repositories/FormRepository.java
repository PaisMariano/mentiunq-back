package com.unq.edu.li.pdesa.mentiUnq.repositories;

import com.unq.edu.li.pdesa.mentiUnq.models.Form;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormRepository extends CrudRepository<Form, Long> {
    Optional<Form> findByCodeShare(String codeShare);
    Optional<Form> findByCode(String code);
}
