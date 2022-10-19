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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
            @Parameter(description = "code Share", required = true)
            @PathVariable("codeShare") String codeShare,
            @PathVariable("questionId") Long questionId,
            @RequestBody AnswerRequest answerRequest) throws Exception {

        ResponseUnit createdAnswer = formService.addAnswer(codeShare, questionId, answerRequest);

        return ResponseEntity.ok(createdAnswer);
    }
}
