package com.zte.ums.esight.domain.model.phoenix;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryResult;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ESQueryResultSerializerTest {

    @Test
    public void testSerialize() throws Exception {
        List<ESMetrics> esMetricses = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("name1","cpu1");
        map.put("value1",10);
        map.put("value2",10L);
        map.put("value3",10.1);
        esMetricses.add(new ESMetrics(map));


        ESQueryResult esQueryResult = new ESQueryResult("cpu",esMetricses);
        String nodesJson = new ObjectMapper().writeValueAsString(esQueryResult);

        System.out.println(nodesJson);
    }
    @Test
    public void testSerialize_for_two_metrics() throws Exception {
        List<ESMetrics> esMetricses = new ArrayList<ESMetrics>();
        Map<String,Object> map = new HashMap<>();
        map.put("name1","cpu1");
        map.put("value1",10);
        map.put("value2",10L);
        map.put("value3",10.1);
        esMetricses.add(new ESMetrics(map));

        map = new HashMap<>();
        map.put("name21","cpu2");
        map.put("value22",10);
        map.put("value23",10L);
        map.put("value24",10.1);
        esMetricses.add(new ESMetrics(map));

        ESQueryResult esQueryResult = new ESQueryResult("cpu",esMetricses);
        String nodesJson = new ObjectMapper().writeValueAsString(esQueryResult);

        System.out.println(nodesJson);
    }


}