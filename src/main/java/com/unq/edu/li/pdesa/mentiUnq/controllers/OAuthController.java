package com.unq.edu.li.pdesa.mentiUnq.controllers;

import com.unq.edu.li.pdesa.mentiUnq.controllers.request.OAuthRequest;
import com.unq.edu.li.pdesa.mentiUnq.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("api/oauth")
@Controller
public class OAuthController
{
	private final UserService userService;

	@Autowired
	public OAuthController(UserService userService)
	{
		this.userService = userService;
	}

	@PostMapping(path = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity authenticate(@RequestBody final OAuthRequest authenticationRequest) throws Exception {
		return ResponseEntity.ok(userService.authenticate(authenticationRequest));
	}
}
