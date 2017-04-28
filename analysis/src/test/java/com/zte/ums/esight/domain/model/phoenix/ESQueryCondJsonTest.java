package com.zte.ums.esight.domain.model.phoenix;

import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.domain.model.ESQueryCondJson;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ESQueryCondJsonTest {

    @Test
    public void testParse() throws Exception {
        String jsonData = "{agentId='cm-agent11'," +
                "startTime=1488384000000" +
                ",from=1,to=2,type='type',subType='subType'}";
        ESQueryCond esQueryCond = new ESQueryCondJson().parse(jsonData);

        assertEquals(esQueryCond.getAgentId(), "cm-agent11");
        assertEquals(esQueryCond.getAgentStartTime(), 1488384000000L);
        assertEquals(esQueryCond.getFrom(), 1L);
        assertEquals(esQueryCond.getTo(), 2L);

    }

    @Test
    public void testParseCommon() throws Exception {
        String jsonData = "{agentId='cm-agent11'," +
                "startTime=1488384000000" +
                ",from=1,to=2,type='type',subType='subType',parms:{" +
                "pid=1,process='java',command='/bin/java'}}";
        ESQueryCond esQueryCond = new ESQueryCondJson().parse(jsonData);

        assertEquals(esQueryCond.getAgentId(), "cm-agent11");
        assertEquals(esQueryCond.getAgentStartTime(), 1488384000000L);
        assertEquals(esQueryCond.getFrom(), 1L);
        assertEquals(esQueryCond.getTo(), 2L);

    }
}