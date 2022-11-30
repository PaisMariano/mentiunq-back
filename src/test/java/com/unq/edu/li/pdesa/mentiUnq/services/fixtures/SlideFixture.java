package com.unq.edu.li.pdesa.mentiUnq.services.fixtures;

import com.unq.edu.li.pdesa.mentiUnq.models.Slide;
import com.unq.edu.li.pdesa.mentiUnq.models.SlideType;

public class SlideFixture
{
	private final Long id = 1l;
	private final String name = "Multiple Choice";
	private final SlideType slideType = SlideTypeFixture.withDefaults();

	public static Slide withDefaults()
	{
		return new SlideFixture().build();
	}

	private Slide build()
	{
		return new Slide(id, name, slideType);
	}
}
