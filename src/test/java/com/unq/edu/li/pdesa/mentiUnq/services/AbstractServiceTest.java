package com.unq.edu.li.pdesa.mentiUnq.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class AbstractServiceTest
{
	protected Gson gson;

	protected void init(){
		gson = new GsonBuilder().create();
	}
}
