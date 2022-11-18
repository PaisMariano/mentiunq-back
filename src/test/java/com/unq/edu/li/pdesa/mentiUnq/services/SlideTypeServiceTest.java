package com.unq.edu.li.pdesa.mentiUnq.services;

import com.unq.edu.li.pdesa.mentiUnq.models.SlideType;
import com.unq.edu.li.pdesa.mentiUnq.repositories.SlideTypeRepository;
import com.unq.edu.li.pdesa.mentiUnq.services.fixtures.SlideTypeFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SlideTypeServiceTest
{
	@InjectMocks
	SlideTypeService service;

	@Mock
	SlideTypeRepository slideTypeRepository;

	@Test
	public void testCreateSlideType(){
		SlideType slideType = SlideTypeFixture.withDefaults();

		service.create(slideType);

		verify(slideTypeRepository).save(slideType);
	}
}
