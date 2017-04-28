package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class QueryNetsByTimeActionTest {
    @Test
    public void getSql() throws Exception {
        ESQueryCond queryCond = new ESQueryCond.ESQueryCondBuild()
                .agentId("fm")
                .agentStartTime(100L)
                .from(1L)
                .to(2L)
                .build();
        String maxSql = new QueryNetsByTimeAction().getSql(queryCond).trim();
        System.out.println(maxSql);
//        String minSql = new QueryNetsByTimeAction("MIN").getSql(queryCond).trim();

//        assertEquals(getExpectedMaxSql(), maxSql);
//        assertEquals(getExpectedMinSql(), minSql);
    }

    @Test
    public void parse() throws Exception {
        //given
        ResultSetStub resultSetStub = new ResultSetStub();

        //when
        List<ESMetrics> metricses = new QueryNetsByTimeAction().parse(resultSetStub);

        //then
        assert metricses.size() == 1;

        ESMetrics metrics = metricses.get(0);
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.AGENT_ID));
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.AGENT_STARTTIME));
        assertEquals(ResultSetStub.NEGATIVE_VALUE, metrics.getValue(ESConst.COLLECT_TIME));
        assertEquals(ResultSetStub.LONG_ZERO, metrics.getValue(ESConst.NET_RECEIVE_BYTES));
        assertEquals(ResultSetStub.LONG_ZERO, metrics.getValue(ESConst.NET_RECEIVE_ERRORS));
        assertEquals(ResultSetStub.LONG_ZERO, metrics.getValue(ESConst.NET_TRANSMIT_BYTES));
        assertEquals(ResultSetStub.LONG_ZERO, metrics.getValue(ESConst.NET_TRANSMIT_ERRORS));
        assertEquals(ResultSetStub.LONG_ZERO, metrics.getValue(ESConst.NET_COLLS));

    }

    private String getExpectedMaxSql() {
        return "select AgentId, AgentStartTime, CollectTime1 as CollectTime,sum(ReceiveBytes) as ReceiveBytes,\n" +
                "  sum(ReceiveErrors) as ReceiveErrors, sum(TransmitBytes) as TransmitBytes,\n" +
                "  sum(TransmitErrors) as TransmitErrors ,sum(Colls) as Colls\n" +
                "  from\n" +
                "  (\n" +
                "    select AgentId, AgentStartTime,TRUNC(TO_DATE(CollectTime,'yyyyMMddHHmmss'),'SECOND')  as CollectTime1,Name,\n" +
                "      MAX(ReceiveBytes) as ReceiveBytes ,MAX(ReceiveErrors) as ReceiveErrors ,MAX(TransmitBytes) as TransmitBytes ,\n" +
                "      MAX(TransmitErrors) as TransmitErrors ,MAX(Colls) as Colls \n" +
                "    from SMARTSIGHT.NET_V1\n" +
                "    where CollectTime>='19700101080000'\n" +
                "     and CollectTime<='19700101080000'\n" +
                "    and AgentId='fm'\n" +
                "    and AgentStartTime='100'\n" +
                "    group by AgentId, AgentStartTime,TRUNC(TO_DATE(CollectTime,'yyyyMMddHHmmss'),'SECOND') ,Name\n" +
                "  )\n" +
                "group by AgentId, AgentStartTime, CollectTime";
    }

    private String getExpectedMinSql() {
        return "select AgentId, AgentStartTime, CollectTime1 as CollectTime,sum(ReceiveBytes) as ReceiveBytes,\n" +
                "  sum(ReceiveErrors) as ReceiveErrors, sum(TransmitBytes) as TransmitBytes,\n" +
                "  sum(TransmitErrors) as TransmitErrors ,sum(Colls) as Colls\n" +
                "  from\n" +
                "  (\n" +
                "    select AgentId, AgentStartTime,TRUNC(TO_DATE(CollectTime,'yyyyMMddHHmmss'),'SECOND')  as CollectTime1,Name,\n" +
                "      MIN(ReceiveBytes) as ReceiveBytes ,MIN(ReceiveErrors) as ReceiveErrors ,MIN(TransmitBytes) as TransmitBytes ,\n" +
                "      MIN(TransmitErrors) as TransmitErrors ,MIN(Colls) as Colls \n" +
                "    from SMARTSIGHT.NET_V1\n" +
                "    where CollectTime>='19700101080000'\n" +
                "     and CollectTime<='19700101080000'\n" +
                "    and AgentId='fm'\n" +
                "    and AgentStartTime='100'\n" +
                "    group by AgentId, AgentStartTime,TRUNC(TO_DATE(CollectTime,'yyyyMMddHHmmss'),'SECOND') ,Name\n" +
                "  )\n" +
                "group by AgentId, AgentStartTime, CollectTime";
    }
}