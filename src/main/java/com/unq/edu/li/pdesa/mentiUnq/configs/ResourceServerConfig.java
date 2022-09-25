package com.unq.edu.li.pdesa.mentiUnq.configs;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter
{
	private final String verifierKey = "G5*Nls/B/-yi82nB5Jtytbj2jajsjj2jDecCfTjURDFjIDESEu==(.";

	private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
		new AntPathRequestMatcher("/oauth/authenticate"));

	private static final RequestMatcher PROTECTED_URLS = new OrRequestMatcher(
			new AntPathRequestMatcher("/api/**"));

	/*@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
*/
	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().authorizeRequests().requestMatchers(PUBLIC_URLS).permitAll()
				.and().authorizeRequests().requestMatchers(PROTECTED_URLS).authenticated()
				.and().csrf().disable();
	}

	/*@Override
	public void configure(ResourceServerSecurityConfigurer config)
	{
		config.resourceId("oauth2-resource").tokenServices(tokenServices());
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices()
	{
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		return defaultTokenServices;
	}

	@Bean
	public TokenStore tokenStore()
	{
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter()
	{
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(verifierKey);
		converter.setVerifierKey(verifierKey);
		return converter;
	}*/

	/*@Bean
	public FilterRegistrationBean corsFilter()
	{
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}*/
}
