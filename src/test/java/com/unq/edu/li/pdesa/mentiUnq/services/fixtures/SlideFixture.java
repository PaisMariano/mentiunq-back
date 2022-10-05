package com.unq.edu.li.pdesa.mentiUnq.services.fixtures;

import com.unq.edu.li.pdesa.mentiUnq.models.Slide;

public class SlideFixture
{
	private final Long id = 1l;
	private final String name = "Multiple Choice";

	public static Slide withDefaults()
	{
		return new SlideFixture().build();
	}

	private Slide build()
	{
		return new Slide(id, name);
	}
}
