package com.unq.edu.li.pdesa.mentiUnq.services;

import com.unq.edu.li.pdesa.mentiUnq.controllers.fixtures.ResponseUnitFixture;
import com.unq.edu.li.pdesa.mentiUnq.exceptions.EntityNotFoundException;
import com.unq.edu.li.pdesa.mentiUnq.models.Slide;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.protocols.Status;
import com.unq.edu.li.pdesa.mentiUnq.repositories.SlideRepository;
import com.unq.edu.li.pdesa.mentiUnq.services.fixtures.SlideFixture;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SlideServiceTest extends AbstractServiceTest
{
	@InjectMocks
	private SlideService service;

	@Mock
	private SlideRepository slideRepository;

	private Long id;
	private Slide aSlide;

	@BeforeEach
	public void setUp(){
		init();
		id = 1l;
		aSlide = SlideFixture.withDefaults();
	}

	@Test
	public void whenGetAllSlidesAndRepositoryIsEmptyThenReturnsResponseUnitWithEmptyPayload()
	{
		ResponseUnit responseUnit = service.findAll();

		assertNotNull(responseUnit);
		assertEquals(Status.SUCCESS, responseUnit.getStatus());
		assertEquals(Strings.EMPTY, responseUnit.getMessage());
		assertEquals("[]", responseUnit.getPayload());
		verify(slideRepository).findAll();
	}

	@Test
	public void whenGetAllSlidesAndRepositoryIsNotEmptyThenReturnsResponseUnitWithPayload()
	{
		List<Slide> slides = Arrays.asList(aSlide);

		when(slideRepository.findAll()).thenReturn(slides);

		ResponseUnit responseUnit = service.findAll();
		ResponseUnit expectedResponse = ResponseUnitFixture.withOkResponseSlides();

		assertNotNull(responseUnit);
		assertEquals(expectedResponse.getStatus(), responseUnit.getStatus());
		assertEquals(expectedResponse.getMessage(), responseUnit.getMessage());
		assertEquals(expectedResponse.getPayload(), responseUnit.getPayload());
		verify(slideRepository).findAll();
	}

	@Test
	public void whenGetSlideByIdAndRepositoryIsNotEmptyThenReturnsResponseUnitWithPayload() throws EntityNotFoundException
	{
		Optional<Slide> optionalSlide = Optional.of(aSlide);

		when(slideRepository.findById(anyLong())).thenReturn(optionalSlide);

		ResponseUnit responseUnit = service.findById(id);
		ResponseUnit expectedResponse = ResponseUnitFixture.withOkResponseSlide();

		assertNotNull(responseUnit);
		assertEquals(expectedResponse.getStatus(), responseUnit.getStatus());
		assertEquals(expectedResponse.getMessage(), responseUnit.getMessage());
		assertEquals(expectedResponse.getPayload(), responseUnit.getPayload());
		verify(slideRepository).findById(anyLong());
	}

	@Test
	public void whenGetSlideByIdAndRepositoryIsEmptyThenReturnsResponseUnitWithPayload()
	{
		String expectedMessage = "Entity '" + id + "' not found";

		EntityNotFoundException exception = assertThrows(
				EntityNotFoundException.class,
				()-> service.findById(id),
				expectedMessage);

		assertNotNull(exception);
		assertEquals(expectedMessage, exception.getMessage());
		verify(slideRepository).findById(anyLong());
	}

	@Test
	public void whenCreateSlideThenRepositorySaveAndReturnAResponseUnit(){
		when(slideRepository.save(any(Slide.class))).thenReturn(aSlide);

		ResponseUnit responseUnit = service.create(aSlide);
		ResponseUnit expectedResponse = ResponseUnitFixture.withOkResponseSlide();

		assertNotNull(responseUnit);
		assertEquals(expectedResponse.getStatus(), responseUnit.getStatus());
		assertEquals(expectedResponse.getMessage(), responseUnit.getMessage());
		assertEquals(expectedResponse.getPayload(), responseUnit.getPayload());
		verify(slideRepository).save(any(Slide.class));
	}

}
