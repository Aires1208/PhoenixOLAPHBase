package com.zte.ums.esight.domain.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;


public class ESQueryResultSerializer extends JsonSerializer<ESQueryResult> {

    @Override
    public void serialize(ESQueryResult esQueryResult, JsonGenerator jgen, SerializerProvider provider) throws IOException {

        jgen.writeStartObject();

        jgen.writeStringField("name",esQueryResult.getName());

        jgen.writeFieldName("metrics");
        writeMetrics(esQueryResult.getEsMetricses(),jgen);


        jgen.writeEndObject();
    }

    private void writeMetrics(List<ESMetrics> nodes, JsonGenerator jgen) throws IOException{
        jgen.writeStartArray();

        for(ESMetrics node : nodes) {
            jgen.writeStartObject();
            for(Map.Entry<String,Object> entry : node.getValues().entrySet()) {
                if(entry.getValue() instanceof Long) {
                    jgen.writeNumberField(entry.getKey(),(long)entry.getValue());
                } else if(entry.getValue() instanceof Integer) {
                    jgen.writeNumberField(entry.getKey(),(int)entry.getValue());
                } else if(entry.getValue() instanceof Double) {
                    jgen.writeNumberField(entry.getKey(),(double)entry.getValue());
                } else {
                    jgen.writeStringField(entry.getKey(),(String)entry.getValue());
                }

            }


            jgen.writeEndObject();
        }

        jgen.writeEndArray();
    }
}
