package com.unq.edu.li.pdesa.mentiUnq;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.unq.edu.li.pdesa.mentiUnq.*")
public class MentiUnqApplication {

	public static void main(String[] args) {
		SpringApplication.run(MentiUnqApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("${project.version}") String appVersion)
	{
		final String securitySchemeName = "OAuth2";

		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
				.info(new Info().title("MentiUNQ API documentation")
						.description("Documentation about endpoints, requests and response from MentiUNQ")
						.version(appVersion));
	}

}