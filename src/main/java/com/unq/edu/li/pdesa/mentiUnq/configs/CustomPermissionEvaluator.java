package com.unq.edu.li.pdesa.mentiUnq.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator//, ApplicationContextAware
{
	//private ApplicationContext applicationContext;

	private final Logger logger = LoggerFactory.getLogger(CustomPermissionEvaluator.class);

	//TODO:revisar esto, no se usa en OAuth
	/*@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		this.applicationContext = applicationContext;
	}*/

	@Override
	public boolean hasPermission(Authentication authentication, Object privilegeRequired, Object scopeRequired)
	{
		logger.debug("Privilege required : {}", privilegeRequired);
		logger.debug("Scope required     : {}", scopeRequired);

		if ((authentication == null)
				|| !(privilegeRequired instanceof String)
				|| !(scopeRequired instanceof String))
		{
			return false;
		}

		return isAuthorized( (String) privilegeRequired, authentication);
		//TODO: revisar, esto no se usa
				//|| isScopeAuthorized((String) scopeRequired, authentication);
	}

	private boolean isAuthorized(String privilegeRequired, Authentication authentication)
	{
		for (GrantedAuthority grantedAuth : authentication.getAuthorities())
		{
			if (grantedAuth.getAuthority().equalsIgnoreCase(privilegeRequired))
			{
				return true;
			}
		}
		return false;
	}

	/*private boolean isScopeAuthorized(String scopeRequired, Authentication authentication)
	{
		if (authentication instanceof OAuth2Authentication)
		{
			Set<String> scopes = ((OAuth2Authentication) authentication).getOAuth2Request().getScope();
			if (scopes != null && scopes.size() > 0)
			{
				for (String scope : scopes)
				{
					if (scope.equalsIgnoreCase(scopeRequired))
					{
						return true;
					}
				}
			}
			return false;
		}
		return true;
	}*/

	@Override
	public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
		return false;
	}
}
