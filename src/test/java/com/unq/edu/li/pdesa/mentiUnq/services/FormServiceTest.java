package com.unq.edu.li.pdesa.mentiUnq.services;

import com.unq.edu.li.pdesa.mentiUnq.models.Form;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.repositories.FormRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FormServiceTest
{
	@InjectMocks
	private FormService service;

	@Mock
	private FormRepository formRepository;

	//@Test
	//TODO REVISAR ESTO AL ESTAR EN OTRO BRANCH
	public void testWhenTriesToCreateFormReturnsResponseUnitWithPayloadAsStringWithFormData(){

		Form form = mock(Form.class);

		when(formRepository.save(any(Form.class))).thenReturn(form);

		ResponseUnit responseUnit = service.createForm(userId);

		assertNotNull(responseUnit);
	}
}
