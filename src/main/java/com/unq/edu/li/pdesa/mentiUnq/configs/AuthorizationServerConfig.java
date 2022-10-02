package com.unq.edu.li.pdesa.mentiUnq.configs;

import com.unq.edu.li.pdesa.mentiUnq.services.CustomUserDetailsService;
import com.unq.edu.li.pdesa.mentiUnq.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAuthorizationServer
@Order(SecurityProperties.BASIC_AUTH_ORDER - 1)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter
{
	private String	verifier = "G5*Nls/B/-yi82nB5Jtytbj2jajsjj2jDecCfTjURDFjIDESEu==(.";
	private int expirationGrantPassword = 600;
	private int expirationGrantClientCredential = 600;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ClientDetailsService clientDetailsService;
	@Autowired
	private CustomUserDetailsService userDetailsService;
	@Autowired
	private AuthService userService;
	@Autowired
	protected HttpServletRequest requestHttp;

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		//return new BCryptPasswordEncoder();
		return new PasswordEncoder() {
			@Override
			public String encode(CharSequence rawPassword) {
				return rawPassword.toString();
			}
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return rawPassword.toString().equals(encodedPassword);
			}
		};
	}

	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception
	{
		endpoints
				.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
				.tokenGranter(tokenGranter());
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security)
	{
		security
				.tokenKeyAccess("permitAll()")
				.checkTokenAccess("isAuthenticated()")
				.allowFormAuthenticationForClients();
	}

	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("accounts_read_user")
				.authorizedGrantTypes("password")
				.authorities("ACCOUNTS_READ_PRIVILEGE")
				.resourceIds("oauth2-resource")
				.scopes("webservices")

				.and()
				.withClient("accounts_read_scope_user")
				.authorizedGrantTypes("password")
				.authorities("authorities")
				.resourceIds("oauth2-resource")
				.scopes("GET@/services/accounts")

				.and()
				.withClient("accounts_read_cash_manager_user")
				.authorizedGrantTypes("password")
				.authorities("ACCOUNTS_READ_CASH_MANAGER_PRIVILEGE")
				.resourceIds("oauth2-resource")
				.scopes("webservices")

				.and()
				.withClient("accounts_read_cash_manager_scope_user")
				.authorizedGrantTypes("password")
				.authorities("authorities")
				.resourceIds("oauth2-resource")
				.scopes("GET@/services/accounts/getByAccountNumber")

				.and()
				.withClient("accounts_create_user")
				.authorizedGrantTypes("password")
				.authorities("ACCOUNTS_CREATE_PRIVILEGE")
				.resourceIds("oauth2-resource")
				.scopes("webservices")

				.and()
				.withClient("accounts_create_scope_user")
				.authorizedGrantTypes("password")
				.authorities("authorities")
				.resourceIds("oauth2-resource")
				.scopes("POST@/services/accounts")

				.and()
				.withClient("accounts_transaction_user")
				.authorizedGrantTypes("password")
				.authorities("ACCOUNTS_TRANSACTION")
				.resourceIds("oauth2-resource")
				.scopes("webservices")

				.and()
				.withClient("accounts_transaction_scope_user")
				.authorizedGrantTypes("password")
				.authorities("authorities")
				.resourceIds("oauth2-resource")
				.scopes("POST@/services/accounts")

				.and()
				.withClient("onboarding_user")
				.authorizedGrantTypes("password")
				.authorities("CREATE_CLIENT_AND_USER_PRIVILEGE")
				.resourceIds("oauth2-resource")
				.scopes("webservices")

				.and()
				.withClient("onboarding_scope_user")
				.authorizedGrantTypes("password")
				.authorities("authorities")
				.resourceIds("oauth2-resource")
				.scopes("POST@/services/onboarding/registerClient")

				.and()
				.withClient("clients_read_user")
				.authorizedGrantTypes("password")
				.authorities("CLIENTS_READ_PRIVILEGE")
				.resourceIds("oauth2-resource")
				.scopes("webservices")

				.and()
				.withClient("clients_read_scope_user")
				.authorizedGrantTypes("password")
				.authorities("authorities")
				.resourceIds("oauth2-resource")
				.scopes("GET@/services/clients")

				.and()
				.withClient("clients_update_user")
				.authorizedGrantTypes("password")
				.authorities("CLIENTS_UPDATE_PRIVILEGE")
				.resourceIds("oauth2-resource")
				.scopes("webservices")

				.and()
				.withClient("clients_update_scope_user")
				.authorizedGrantTypes("password")
				.authorities("authorities")
				.resourceIds("oauth2-resource")
				.scopes("PUT@/services/clients")

				.and()
				.withClient("myclientwithout")
				.authorizedGrantTypes("password")
				.authorities("myauthorities")
				.resourceIds("oauth2-resource")
				.scopes("webservices");

	}

	@Bean
	public TokenStore tokenStore()
	{
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter()
	{
		JwtAccessTokenConverter jwt = new JwtAccessTokenConverter();
		jwt.setSigningKey(verifier);
		//jwt.setVerifierKey(verifier); //TODO ver si esto es necesario
		return jwt;
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServicesResourceOwner()
	{
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setAccessTokenValiditySeconds(expirationGrantPassword);
		defaultTokenServices.setTokenEnhancer(accessTokenConverter());
		return defaultTokenServices;
	}

	@Bean
	public DefaultTokenServices tokenServicesClientCredentials()
	{
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setAccessTokenValiditySeconds(expirationGrantClientCredential);
		defaultTokenServices.setTokenEnhancer(accessTokenConverter());
		return defaultTokenServices;
	}

	@Bean
	public DefaultTokenServices tokenServicesRefresh()
	{
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setTokenEnhancer(accessTokenConverter());
		return defaultTokenServices;
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}


	@Bean
	@Primary
	public OAuth2RequestFactory requestFactory() {
		return new DefaultOAuth2RequestFactory(clientDetailsService);
	}

	private TokenGranter tokenGranter()
	{
		OAuth2RequestFactory requestFactory = requestFactory();

		ClientCredentialsTokenGranter clientCredentials =
				new ClientCredentialsTokenGranter(tokenServicesClientCredentials(), clientDetailsService, requestFactory);
		ResourceOwnerPasswordTokenGranter resourcePassword =
				new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServicesResourceOwner(),
						clientDetailsService, requestFactory);
		RefreshTokenGranter refreshToken =
				new RefreshTokenGranter(tokenServicesRefresh(), clientDetailsService, requestFactory);

		List<TokenGranter> tokenGranters = new ArrayList<>();

		tokenGranters.add(refreshToken);
		tokenGranters.add(clientCredentials);
		tokenGranters.add(resourcePassword);

		return new CompositeTokenGranter(tokenGranters);
	}

	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{
		String userName = authentication.getName();
		String password = authentication.getCredentials().toString();

		UserDetails user = userDetailsService.loadUserByUsername(userName);
		return new UsernamePasswordAuthenticationToken(userName, password, user.getAuthorities());
	}
}

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER - 2)
class AuthServerConfig extends WebSecurityConfigurerAdapter
{
	/*@Autowired
	public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception
	{
		auth.inMemoryAuthentication()
				.withUser("user").password("password").authorities("user").and()
				.withUser("alice").password("password").authorities("user").and()
				.withUser("bob").password("password").authorities("user").and()
				.withUser("eve").password("password").authorities("user");
	}*/

	@Bean
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception
	{
		return super.userDetailsServiceBean();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}
}