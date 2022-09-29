package com.unq.edu.li.pdesa.mentiUnq.repositories;

import com.unq.edu.li.pdesa.mentiUnq.models.MailingWhiteList;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MailingRepository extends CrudRepository<MailingWhiteList, Long> {
    @Query("SELECT e FROM MailingWhiteList e WHERE e.email = :email")
    public Optional<MailingWhiteList> getMailingWhiteListByEmail(@Param("email") String email);
}
