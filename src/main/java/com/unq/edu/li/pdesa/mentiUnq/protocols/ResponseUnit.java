package com.unq.edu.li.pdesa.mentiUnq.protocols;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unq.edu.li.pdesa.mentiUnq.models.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Schema(name = "ResponseUnit", description = "ResponseUnit response with status, message and payload information.")
public class ResponseUnit {

    @JsonIgnore
    private Gson gson;

    @Schema(description = "Internal status response", required = true)
    private Status status;

    @Schema(description = "Additional message info", required = true)
    private String message;

    @Schema(description = "Payload response in json format", required = true)
    private String payload;

    public ResponseUnit(Status status, String message, BaseModel payload) {
        createGsonBuilder();

        this.status = status;
        this.message = message;
        this.payload = gson.toJson(payload);
    }

    public ResponseUnit(Status status, String message, List<? extends BaseModel> payload) {
        createGsonBuilder();

        this.status = status;
        this.message = message;
        this.payload = gson.toJson(payload);
    }

    public ResponseUnit(Status status, String message, String payload) {
        this.status = status;
        this.message = message;
        this.payload = payload;
    }

    private void createGsonBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        this.gson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
    }
}
