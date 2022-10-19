package com.unq.edu.li.pdesa.mentiUnq.controllers;

import com.unq.edu.li.pdesa.mentiUnq.controllers.request.AnswerRequest;
import com.unq.edu.li.pdesa.mentiUnq.controllers.request.QuestionRequest;
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
    @PostMapping(path = "/user/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> create(@Parameter(description = "User Id", required = true)@PathVariable("userId") Long userId) throws Exception {
        ResponseUnit createdForm = formService.createForm(userId);

        return ResponseEntity.ok(createdForm);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Add a question to a form", description = "Update a form in the database ", operationId = "updateForm")
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
            @RequestBody QuestionRequest question) throws Exception {
        ResponseUnit createdForm = formService.addQuestion(id, question);

        return ResponseEntity.ok(createdForm);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Add answer to a question", description = "Create a form in the database with his own structure", operationId = "createForm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content(schema = @Schema(implementation = ResponseUnit.class)))
    })
    @PatchMapping(path = "/{formId}/question/{questionId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity update(
            @Parameter(description = "Answer body", required = true)
            @PathVariable("formId") Long formId,
            @PathVariable("questionId") Long questionId,
            @RequestBody AnswerRequest answer) throws Exception {
        ResponseUnit createdForm = formService.addAnswer(formId, questionId, answer);

        return ResponseEntity.ok(createdForm);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get all answers by question id", description = "Get all answers from one particular question", operationId = "getAllAnswers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content(schema = @Schema(implementation = ResponseUnit.class)))
    })
    @GetMapping(path = "/question/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getById(@Parameter(description = "question Id", required = true)@PathVariable("id") Long id) throws Exception {
        ResponseUnit forms = formService.getAnswersByQuestionId(id);

        return ResponseEntity.ok(forms);
    }

}
