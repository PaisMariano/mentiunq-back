package com.unq.edu.li.pdesa.mentiUnq.protocols;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.unq.edu.li.pdesa.mentiUnq.models.BaseModel;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListSerializer implements JsonSerializer<ArrayList<BaseModel>> {

    @Override
    public JsonElement serialize(ArrayList<BaseModel> src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null)
            return null;
        else {
            JsonArray jsonArray = new JsonArray();
            for (BaseModel baseModel : src) {
                Class clazz = baseModel.getClass();
                jsonArray.add(context.serialize(baseModel, clazz));
            }
            return jsonArray;
        }
    }

}
