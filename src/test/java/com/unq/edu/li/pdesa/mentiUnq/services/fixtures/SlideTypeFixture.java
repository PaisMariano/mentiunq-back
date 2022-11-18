package com.unq.edu.li.pdesa.mentiUnq.services.fixtures;

import com.unq.edu.li.pdesa.mentiUnq.models.SlideType;

public class SlideTypeFixture
{
	private final Long id = 1l;
	private final String name = "NAME";

	public static SlideType withDefaults()
	{
		return new SlideTypeFixture().build();
	}

	private SlideType build()
	{
		return new SlideType(id, name);
	}
}
