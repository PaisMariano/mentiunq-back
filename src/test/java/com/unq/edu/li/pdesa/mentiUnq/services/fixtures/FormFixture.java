package com.unq.edu.li.pdesa.mentiUnq.services.fixtures;

import com.unq.edu.li.pdesa.mentiUnq.models.Form;

public class FormFixture
{
	private final  String code = "CODE";
	private final  String codeShare = "CODE_SHARE";

	public static Form withDefaults()
	{
		return new FormFixture().build();
	}

	private Form build()
	{
		Form aForm = new Form();
		aForm.setCode(code);
		aForm.setCodeShare(codeShare);
		return aForm;
	}
}
