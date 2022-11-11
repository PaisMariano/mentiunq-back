package com.unq.edu.li.pdesa.mentiUnq.controllers.fixtures;

import com.unq.edu.li.pdesa.mentiUnq.controllers.request.FormNameRequest;

public class FormNameRequestFixture
{
	private static String name = "NAME";

	public static FormNameRequest withDefaults()
	{
		return new FormNameRequestFixture().build();
	}

	private FormNameRequest build()
	{
		FormNameRequest request = new FormNameRequest();
		request.setName(name);
		return request;
	}
}
