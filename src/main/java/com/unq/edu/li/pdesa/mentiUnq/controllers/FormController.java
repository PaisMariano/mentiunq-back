package com.unq.edu.li.pdesa.mentiUnq.controllers;

import com.unq.edu.li.pdesa.mentiUnq.controllers.request.FormRequest;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.services.FormService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Form Controller.")
@Controller
@RequestMapping("/api/form")
public class FormController {
    private final FormService formService;

    public FormController(FormService formService) { this.formService = formService; }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Create a form", description = "Create a form in the database with his own structure", operationId = "createForm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content(schema = @Schema(implementation = ResponseUnit.class)))
    })
    @PostMapping(path = "/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> create(@Parameter(description = "User Id", required = true)@PathVariable("userId") Long userId) throws Exception {
        ResponseUnit createdForm = formService.createForm(userId);

        return ResponseEntity.ok(createdForm);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Update a form", description = "Create a form in the database with his own structure", operationId = "createForm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content(schema = @Schema(implementation = ResponseUnit.class)))
    })

    @PatchMapping(path = "/{formId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity update(
            @Parameter(description = "Form body", required = true)
            @PathVariable("formId") Long id,
            @RequestBody FormRequest form) throws Exception {
        ResponseUnit createdForm = formService.updateForm(id, form);

        return ResponseEntity.ok(createdForm);
    }

}
