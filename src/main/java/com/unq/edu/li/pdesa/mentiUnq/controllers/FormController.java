package com.unq.edu.li.pdesa.mentiUnq.controllers;

import com.unq.edu.li.pdesa.mentiUnq.controllers.request.AnswerRequest;
import com.unq.edu.li.pdesa.mentiUnq.controllers.request.FormNameRequest;
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
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
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
            @Parameter(description = "Formd id", required = true) @PathVariable("formId") Long id,
            @Parameter(description = "Form body", required = true) @RequestBody QuestionRequest question) throws Exception {
        ResponseUnit createdForm = formService.addQuestion(id, question);

        return ResponseEntity.ok(createdForm);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Renames a form", description = "rename a form in the database ", operationId = "renameForm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content(schema = @Schema(implementation = ResponseUnit.class)))
    })
    @PatchMapping(path = "/{formId}/rename", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity renameForm(
            @Parameter(description = "", required = true)@PathVariable("formId") Long id,
            @RequestBody FormNameRequest nameRequest) throws Exception {
        ResponseUnit createdForm = formService.renameForm(id, nameRequest);

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
            @Parameter(description = "Form ID", required = true) @PathVariable("formId") Long formId,
            @Parameter(description = "Question id", required = true) @PathVariable("questionId") Long questionId,
            @Parameter(description = "Answer request", required = true) @RequestBody AnswerRequest answer
    ) throws Exception {
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

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Delete form by id", description = "Delete a form from database.", operationId = "deleteFormById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content(schema = @Schema(implementation = ResponseUnit.class)))
    })
    @DeleteMapping(path = "/{formId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> deleteFormById(@Parameter(description = "form Id", required = true)@PathVariable("formId") Long formId) throws Exception {

        return ResponseEntity.ok(formService.deleteFormById(formId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Delete question by id", description = "Delete a question from database.", operationId = "deleteQuestionById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content(schema = @Schema(implementation = ResponseUnit.class)))
    })
    @DeleteMapping(path = "/{formId}/question/{questionId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> deleteQuestionById(@Parameter(description = "Form Id", required = true)@PathVariable("formId") Long formId,
                                                @Parameter(description = "Question Id", required = true)@PathVariable("questionId") Long questionId) throws Exception {

        return ResponseEntity.ok(formService.deleteQuestionById(formId, questionId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Delete option by id", description = "Delete a question from database.", operationId = "deleteOptionById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content(schema = @Schema(implementation = ResponseUnit.class)))
    })
    @DeleteMapping(path = "/{formId}/option/{optionId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> deleteOptionById(@Parameter(description = "Slide Id", required = true)@PathVariable("formId") Long formId,
                                              @Parameter(description = "MentiOption Id", required = true)@PathVariable("optionId") Long optionId) throws Exception {

        return ResponseEntity.ok(formService.deleteOptionById(formId, optionId));
    }


    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get questions by form id", description = "Get questions by form id", operationId = "getQuestionsById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content(schema = @Schema(implementation = ResponseUnit.class)))
    })
    @GetMapping(path = "/{formId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getQuestionsById(@Parameter(description = "user Id", required = true)@PathVariable("formId") Long formId) throws Exception {
        return ResponseEntity.ok(formService.getQuestionsById(formId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get form by code", description = "Get form by code", operationId = "getFormByCode")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content(schema = @Schema(implementation = ResponseUnit.class)))
    })
    @GetMapping(path = "/code/{code}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getFormByCode(@Parameter(description = "Code", required = true)@PathVariable("code") String code) throws Exception {
        return ResponseEntity.ok(formService.getFormByCode(code));
    }

	@PreAuthorize("hasAuthority('USER')")
	@Operation(summary = "Update current question", description = "Update current question", operationId = "updateCurrentQuestion")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
			@ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content(schema = @Schema(implementation = ResponseUnit.class)))
	})
	@PatchMapping(path = "/{formId}/current/question/{questionId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity updateCurrentQuestion(
			@Parameter(description = "Form Id", required = true) @PathVariable("formId") Long formId,
			@Parameter(description = "Question id", required = true) @PathVariable("questionId") Long questionId
	) throws Exception {
		ResponseUnit createdForm = formService.updateCurrentQuestion(formId, questionId);

		return ResponseEntity.ok(createdForm);
	}

	@PreAuthorize("hasAuthority('USER')")
	@Operation(summary = "Update name question", description = "Update current name question ", operationId = "updateNameQuestion")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
			@ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content(schema = @Schema(implementation = ResponseUnit.class)))
	})
	@PatchMapping(path = "/{formId}/update/question/{questionId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity updateNameQuestion(
			@Parameter(description = "Form Id", required = true) @PathVariable("formId") Long formId,
			@Parameter(description = "Question id", required = true) @PathVariable("questionId") Long questionId,
			@Parameter(description = "Question request", required = true) @RequestBody QuestionRequest request
	) throws Exception {
		ResponseUnit currentQuestion = formService.updateNameQuestion(formId, questionId, request);

		return ResponseEntity.ok(currentQuestion);
	}

	@PreAuthorize("hasAuthority('USER')")
	@Operation(summary = "Update name option", description = "Update current name option ", operationId = "updateNameOption")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
			@ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content(schema = @Schema(implementation = ResponseUnit.class)))
	})
	@PatchMapping(path = "/{formId}/update/option/{optionId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity updateNameOption(
			@Parameter(description = "Form Id", required = true) @PathVariable("formId") Long formId,
			@Parameter(description = "Option id", required = true) @PathVariable("optionId") Long optionId,
			@Parameter(description = "Question request", required = true) @RequestBody AnswerRequest request
	) throws Exception {
		ResponseUnit currentQuestion = formService.updateNameOption(formId, optionId, request);

		return ResponseEntity.ok(currentQuestion);
	}

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get results by form code", description = "Get results by form code", operationId = "getResultsByFormCode")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content(schema = @Schema(implementation = ResponseUnit.class)))
    })
    @GetMapping(path = "/code/{formCode}/results", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getResultsByFormCode(@Parameter(description = "form Code", required = true)@PathVariable("formCode") String formCode) throws Exception {
        return ResponseEntity.ok(formService.getResultsByFormCode(formCode));
    }

	@PreAuthorize("hasAuthority('USER')")
	@Operation(summary = "Update content from a question form", description = "Update content from a question in the database ", operationId = "updateContent")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
			@ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content(schema = @Schema(implementation = ResponseUnit.class)))
	})
	@PatchMapping(path = "/updateContent/{formCode}/question/{questionId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity updateContent(
			@Parameter(description = "Form code", required = true) @PathVariable("formCode") String formCode,
			@Parameter(description = "Question id", required = true) @PathVariable("questionId") Long questionId,
			@Parameter(description = "Form body", required = true) @RequestBody QuestionRequest question) throws Exception {

		ResponseUnit responseQuestion = formService.updateContent(formCode, questionId, question);

		return ResponseEntity.ok(responseQuestion);
	}
}
