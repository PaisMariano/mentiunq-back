package com.unq.edu.li.pdesa.mentiUnq.repositories;

import com.unq.edu.li.pdesa.mentiUnq.models.MentiUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<MentiUser, Long> {

	@Query("SELECT u FROM MentiUser u WHERE u.userName = :username")
	public MentiUser getUserByUsername(@Param("username") String username);
}