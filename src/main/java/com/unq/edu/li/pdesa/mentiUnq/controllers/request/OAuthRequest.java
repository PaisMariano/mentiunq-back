package com.unq.edu.li.pdesa.mentiUnq.controllers.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
@Getter
public class OAuthRequest
{
	@JsonProperty("email")
	private String email;

	@JsonProperty("id_token")
	private String idToken;
}
