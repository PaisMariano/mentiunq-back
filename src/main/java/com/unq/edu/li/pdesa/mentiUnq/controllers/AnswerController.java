package com.unq.edu.li.pdesa.mentiUnq.controllers;

import com.unq.edu.li.pdesa.mentiUnq.controllers.request.AnswerRequest;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Answers controller without security for clients .")
@Controller
@RequestMapping("/answer")
public class AnswerController {
    private final FormService formService;

    public AnswerController(FormService formService) { this.formService = formService; }

    @Operation(summary = "Creates an answer", description = "Creates an answer to a already created form", operationId = "createAnswer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content(schema = @Schema(implementation = ResponseUnit.class)))
    })
    @PostMapping(path = "/form/{codeShare}/question/{questionId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> create(
            @Parameter(description = "code Share", required = true) @PathVariable("codeShare") String codeShare,
			@Parameter(description = "question Id", required = true) @PathVariable("questionId") Long questionId,
			@Parameter(description = "Answer Request", required = true) @RequestBody AnswerRequest answerRequest) throws Exception {

        ResponseUnit createdAnswer = formService.addAnswer(codeShare, questionId, answerRequest);

        return ResponseEntity.ok(createdAnswer);
    }

	@Operation(summary = "gets a question from formCode", description = "gets a question from formCode", operationId = "getQuestionByCodeShare")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
			@ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content(schema = @Schema(implementation = ResponseUnit.class)))
	})
	@GetMapping(path = "/form/{codeShare}/question/current", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getQuestionByCodeShare(
			@Parameter(description = "code Share", required = true) @PathVariable("codeShare") String codeShare) throws Exception {

		ResponseUnit createdAnswer = formService.getQuestionByCodeShare(codeShare);

		return ResponseEntity.ok(createdAnswer);
	}

	@Operation(summary = "Vote", description = "Adds one to score", operationId = "vote")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
			@ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content(schema = @Schema(implementation = ResponseUnit.class)))
	})
	@PostMapping(path = "/form/{codeShare}/question/{questionId}/option/{optionId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> vote(
			@Parameter(description = "code Share", required = true) @PathVariable("codeShare") String codeShare,
			@Parameter(description = "questionId", required = true)  @PathVariable("questionId") Long questionId,
			@Parameter(description = "optionId", required = true)  @PathVariable("optionId") Long optionId) throws Exception {

		ResponseUnit responseVoted = formService.vote(codeShare, questionId, optionId);

		return ResponseEntity.ok(responseVoted);
	}
}
