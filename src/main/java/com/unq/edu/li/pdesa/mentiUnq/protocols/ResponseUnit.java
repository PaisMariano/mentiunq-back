package com.unq.edu.li.pdesa.mentiUnq.protocols;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unq.edu.li.pdesa.mentiUnq.models.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ResponseUnit {
    private Gson gson;
    @Setter
    @Getter
    private Status status;
    @Setter
    @Getter
    private String message;
    @Setter
    @Getter
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
        this.gson = gsonBuilder.create();
    }
}
