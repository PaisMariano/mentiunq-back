package com.unq.edu.li.pdesa.mentiUnq.protocols;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.unq.edu.li.pdesa.mentiUnq.models.BaseModel;

import java.lang.reflect.Type;

public class BaseModelSerializer implements JsonSerializer<BaseModel> {
    public JsonElement serialize(BaseModel src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null)
            return null;
        else {
            Class clazz = src.getClass();
            return context.serialize(src, clazz);
        }
    }
}
