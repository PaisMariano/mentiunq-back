package com.unq.edu.li.pdesa.mentiUnq.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration
{
	private CustomPermissionEvaluator customPermissionEvaluator;

	@Autowired
	public void setCustomPermissionEvaluator(CustomPermissionEvaluator customPermissionEvaluator) {
		this.customPermissionEvaluator = customPermissionEvaluator;
	}

	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
		expressionHandler.setPermissionEvaluator(customPermissionEvaluator);
		return expressionHandler;
	}
}