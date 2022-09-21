package com.unq.edu.li.pdesa.mentiUnq.controllers.response;

import com.unq.edu.li.pdesa.mentiUnq.models.BaseModel;
import lombok.Builder;

@Builder
public class OAuthResponse extends BaseModel
{
	private String accessToken;
}
