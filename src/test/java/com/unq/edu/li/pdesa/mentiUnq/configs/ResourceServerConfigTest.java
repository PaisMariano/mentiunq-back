package com.unq.edu.li.pdesa.mentiUnq.configs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.util.Map;

import static org.mockito.Mockito.mock;

public class ResourceServerConfigTest
{
	ResourceServerConfig resourceServerConfig;

	@BeforeEach
	public void setUp(){
		resourceServerConfig = new ResourceServerConfig();
	}

	@Test
	public void verifyConfig() throws Exception
	{
		ObjectPostProcessor objectPostProcessor = mock(ObjectPostProcessor.class);
		AuthenticationManagerBuilder authenticationManagerBuilder = mock(AuthenticationManagerBuilder.class);
		Map<Class<?>, Object> sharedObjects = mock(Map.class);

		HttpSecurity httpSecurity = new HttpSecurity(objectPostProcessor, authenticationManagerBuilder, sharedObjects);

		resourceServerConfig.configure(httpSecurity);
	}
}
