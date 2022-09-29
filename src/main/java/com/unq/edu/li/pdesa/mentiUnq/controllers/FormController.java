package com.unq.edu.li.pdesa.mentiUnq.controllers;

import com.unq.edu.li.pdesa.mentiUnq.exceptions.EntityNotFoundException;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.services.FormService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/forms")
@Tag(name = "Foms Controller.")
public class FormController {

	private final FormService service;

	@Autowired
	public FormController(FormService service){
		this.service = service;
	}

	@Operation(summary = "Get form by id service", description = "Get a specific form by userId", operationId = "getByUserId")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
			@ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content(schema = @Schema(implementation = ResponseUnit.class)))
	})
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseUnit> getByUserId(@PathVariable("id") Long id) throws Exception
	{
		return new ResponseEntity<ResponseUnit>(service.findById(id), HttpStatus.OK);
	}

	@Operation(summary = "Create a form", description = "Create a new form", operationId = "create")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
			@ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content(schema = @Schema(implementation = ResponseUnit.class)))
	})
	@PostMapping(path = "{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseUnit> create(
			@Parameter(description = "User ID", required = true) @PathVariable("userId") Long userId) throws EntityNotFoundException
	{
		return new ResponseEntity<>(service.create(userId), HttpStatus.OK);
	}
}
