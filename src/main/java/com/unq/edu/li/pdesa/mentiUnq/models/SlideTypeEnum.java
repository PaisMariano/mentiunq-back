package com.unq.edu.li.pdesa.mentiUnq.models;

import lombok.Getter;

@Getter
public enum SlideTypeEnum
{
	OPEN("Abierta"),
	CLOSE("Cerrada"),
	CONTENT("Contenido");

	private final String slideType;

	SlideTypeEnum(String slideType)
	{
		this.slideType = slideType;
	}
}
