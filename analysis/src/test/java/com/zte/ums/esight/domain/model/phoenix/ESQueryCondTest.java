package com.zte.ums.esight.domain.model.phoenix;

import com.zte.ums.esight.domain.model.ESQueryCond;
import org.junit.Test;


public class ESQueryCondTest {

    @Test
    public void createESQueryCond() {
        ESQueryCond esQueryCond = new ESQueryCond.ESQueryCondBuild()
                .agentId("fm-active")
                .agentStartTime(100L)
                .from(10L)
                .to(200L)
                .build();

//        assertEquals("fm-active" ,esQueryCond.getAgentId());
//        assertEquals(100L ,esQueryCond.getAgentStartTime());
//        assertEquals(10L ,esQueryCond.getFrom());
//        assertEquals(200L ,esQueryCond.getTo());
//        assertEquals(5 ,esQueryCond.getGp());
//
//        System.out.println(esQueryCond);

    }

}