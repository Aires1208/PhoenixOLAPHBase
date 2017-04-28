package com.zte.ums.esight.domain.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Test;

import static org.junit.Assert.*;


public class ResultDeserializerTest{
    @Test
    public void deserializer()  throws Exception{
        //given
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addDeserializer(Result.class, new ResultDeserializer());
        mapper.registerModule(module);

        //when
        Result result = mapper.readValue("{\"status\":1}",Result.class);

        //then
        assertEquals(1, result.getStatus());
    }

}