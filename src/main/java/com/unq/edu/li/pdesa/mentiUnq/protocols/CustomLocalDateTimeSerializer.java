package com.unq.edu.li.pdesa.mentiUnq.protocols;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomLocalDateTimeSerializer implements JsonSerializer<LocalDateTime>
{
    @Override
    public JsonElement serialize(LocalDateTime fieldToSerialize, Type typeOfSrc, JsonSerializationContext context)
    {
        return fieldToSerialize == null ? null : new JsonPrimitive(fieldToSerialize.format(DateTimeFormatter.ISO_DATE_TIME));
    }
}
