package com.zte.ums.esight.domain.model.phoenix;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.zte.ums.esight.domain.model.ESQueryResult;
import com.zte.ums.esight.domain.model.ESQueryResultDeSerializer;
import org.junit.Test;

import static org.junit.Assert.*;

public class ESQueryResultDeSerializerTest {
    @Test
    public void deserializer()  throws Exception{
        //given
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addDeserializer(ESQueryResult.class, new ESQueryResultDeSerializer());
        mapper.registerModule(module);

        //when
        String inputJson = "{\"name\":\"cpu\",\"metrics\":[{\"value2\":10,\"value1\":10,\"value3\":10.1,\"name1\":\"cpu\"}]}";
        ESQueryResult esQueryResult = mapper.readValue(inputJson,ESQueryResult.class);

        //then
//        assertEquals(1, subFlowGroup.getSubFlows().size());
    }

    @Test
    public void deserializer_for_two_metrics()  throws Exception{
        //given
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addDeserializer(ESQueryResult.class, new ESQueryResultDeSerializer());
        mapper.registerModule(module);

        //when
        String inputJson = "{\"name\":\"cpu\",\"metrics\":[{\"value2\":10,\"value1\":10,\"value3\":10.1,\"name1\":\"cpu1\"},{\"name21\":\"cpu2\",\"value22\":10,\"value24\":10.1,\"value23\":10}]}";
        ESQueryResult esQueryResult = mapper.readValue(inputJson,ESQueryResult.class);

        //then
        assertEquals(2, esQueryResult.getEsMetricses().size());
        System.out.println(esQueryResult);
    }

}