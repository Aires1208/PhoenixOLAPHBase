package com.zte.ums.esight.domain.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by root on 16-10-27.
 */
public class ResultSerializer extends JsonSerializer<Result> {
    @Override
    public void serialize(Result result, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("status");
        jsonGenerator.writeObject(result.getStatus());
        jsonGenerator.writeEndObject();
    }
}
