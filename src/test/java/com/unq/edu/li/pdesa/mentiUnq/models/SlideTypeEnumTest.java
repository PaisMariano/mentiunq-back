package com.unq.edu.li.pdesa.mentiUnq.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class SlideTypeEnumTest
{
	@Test
	public void verifyEnumNotNull(){
		assertNotNull(SlideTypeEnum.OPEN);
		assertNotNull(SlideTypeEnum.CLOSE);
		assertNotNull(SlideTypeEnum.CONTENT);
	}

	@Test
	public void verifyEnumGetName(){
		assertNotNull(SlideTypeEnum.OPEN.getSlideType());
		assertNotNull(SlideTypeEnum.CLOSE.getSlideType());
		assertNotNull(SlideTypeEnum.CONTENT.getSlideType());
	}
}
