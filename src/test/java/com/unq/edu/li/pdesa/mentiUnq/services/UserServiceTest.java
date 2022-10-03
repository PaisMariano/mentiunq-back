package com.unq.edu.li.pdesa.mentiUnq.services;

import com.unq.edu.li.pdesa.mentiUnq.controllers.fixtures.ResponseUnitFixture;
import com.unq.edu.li.pdesa.mentiUnq.exceptions.EntityNotFoundException;
import com.unq.edu.li.pdesa.mentiUnq.models.Form;
import com.unq.edu.li.pdesa.mentiUnq.models.MentiUser;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.repositories.UserRepository;
import com.unq.edu.li.pdesa.mentiUnq.services.fixtures.FormFixture;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends AbstractServiceTest
{
	@InjectMocks
	private UserService service;

	@Mock
	private UserRepository userRepository;

	Long userId;

	@BeforeEach
	public void setUp(){
		init();
		userId = 1l;
	}

	@Test
	public void whenGetAllFormsByIdAndUserIdExistThenReturnsResponseUnitWithForms() throws EntityNotFoundException
	{
		MentiUser mentiUser = mock(MentiUser.class);
		Form aForm = FormFixture.withDefaults();
		Optional<MentiUser> optionalMentiUser = Optional.of(mentiUser);
		List<Form> forms = Arrays.asList(aForm);

		when(userRepository.findById(anyLong())).thenReturn(optionalMentiUser);
		when(mentiUser.getForms()).thenReturn(forms);

		ResponseUnit responseUnit = service.getAllFormsById(userId);

		assertNotNull(responseUnit);
		verify(userRepository).findById(anyLong());
	}

	@Test
	public void whenGetAllFormsByIdAndUserDoesNotExistThenReturnsEntityNotFoundException()
	{
		String expectedMessage = "Entity '" + userId + "' not found";

		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

		EntityNotFoundException exception = assertThrows(
				EntityNotFoundException.class,
				()-> service.getAllFormsById(userId),
				expectedMessage);

		assertNotNull(exception);
		assertEquals(expectedMessage, exception.getMessage());
		verify(userRepository).findById(anyLong());
	}
}
