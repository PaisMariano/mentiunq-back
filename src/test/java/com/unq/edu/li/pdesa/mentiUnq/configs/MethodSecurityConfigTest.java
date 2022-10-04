package com.unq.edu.li.pdesa.mentiUnq.configs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

public class MethodSecurityConfigTest
{
	private MethodSecurityConfig methodSecurityConfig;

	@Mock
	private CustomPermissionEvaluator customPermissionEvaluator;

	@BeforeEach
	public void setUp(){
		methodSecurityConfig = new MethodSecurityConfig();
	}

	@Test
	public void testCreateExpressionHandlerReturnANotNullMethodSecurityExpressionHandler(){
		MethodSecurityExpressionHandler expressionHandler = methodSecurityConfig.createExpressionHandler();
		methodSecurityConfig.setCustomPermissionEvaluator(customPermissionEvaluator);

		assertNotNull(expressionHandler);
	}
}
