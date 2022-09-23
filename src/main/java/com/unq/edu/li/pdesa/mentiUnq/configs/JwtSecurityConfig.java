package com.unq.edu.li.pdesa.mentiUnq.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter
{

	public static final String USER = "USER";

	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	public JwtSecurityConfig(JwtRequestFilter jwtRequestFilter)
	{
		this.jwtRequestFilter = jwtRequestFilter;
	}

	/*@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/

	@Override
	public void configure(final HttpSecurity http) throws Exception {
		http.cors().and()
				.csrf().disable()
				.authorizeRequests().antMatchers("/api/oauth/authenticate").permitAll()
				.and().authorizeRequests().antMatchers("/api/slide/**").authenticated()
				//.anyRequest().authenticated()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);

		CorsFilter corsFilter = new CorsFilter(source);
		return corsFilter;
		//FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
		//bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		//return bean;
	}

}