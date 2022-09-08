package com.unq.edu.li.pdesa.mentiUnq.protocols;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.unq.edu.li.pdesa.mentiUnq.models.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

public class ResponseUnit implements Serializable {
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
        createGsonBuilder(payload.getClass(), new BaseModelSerializer());

        this.status = status;
        this.message = message;
        this.payload = gson.toJson(payload);
    }

    public ResponseUnit(Status status, String message, List<? extends BaseModel> payload) {
        createGsonBuilder(payload.getClass(), new ListSerializer());

        this.status = status;
        this.message = message;
        this.payload = gson.toJson(payload);
    }

    private void createGsonBuilder(Class clazz, JsonSerializer serializer) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(clazz, serializer);
        this.gson = gsonBuilder.create();
    }
}
