package com.unq.edu.li.pdesa.mentiUnq.controllers;

import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.services.SlideService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/slide")
@Tag(name = "Slide Controller.")
public class SlideController {
    private final SlideService slideService;

    public SlideController(SlideService slideService) {
        this.slideService = slideService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get all slides", description = "Get all slides from database with his own structure", operationId = "getAll")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content(schema = @Schema(implementation = ResponseUnit.class)))
    })
    @GetMapping(path = "", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAll()
    {
        ResponseUnit slides = slideService.findAll();

        return ResponseEntity.ok(slides);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get slide by id", description = "Get a slides from database with his own structure", operationId = "getById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseUnit.class))),
            @ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content(schema = @Schema(implementation = ResponseUnit.class)))
    })
    @GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getById( @Parameter(description = "Slide Id", required = true)@PathVariable("id") Long id) throws Exception {
        ResponseUnit slides = slideService.findById(id);

        return ResponseEntity.ok(slides);
    }
}
