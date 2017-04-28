package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class HisDeviceGroupbyTimeActionTest {
    @Test
    public void getSql() throws Exception {
        ESQueryCond queryCond = new ESQueryCond.ESQueryCondBuild()
                .agentId("fm")
                .agentStartTime(100L)
                .from(1L)
                .to(2L)
                .build();
        String sql = new HisDeviceGroupbyTimeAction().getSql(queryCond).trim();

        assertEquals(getExpectedSql(), sql);
        System.out.println(sql);
    }

    @Test
    public void parse() throws Exception {
        //given
        ResultSetStub resultSetStub = new ResultSetStub();

        //when
        List<ESMetrics> metricses = new HisDeviceGroupbyTimeAction().parse(resultSetStub);

        //then
        assert metricses.size() == 1;

        ESMetrics metrics = metricses.get(0);
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.AGENT_ID));
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.AGENT_STARTTIME));
        assertEquals(ResultSetStub.NEGATIVE_VALUE, metrics.getValue(ESConst.COLLECT_TIME));
        assertEquals(ResultSetStub.DOUBLE_VALUE, metrics.getValue(ESConst.DEVICE_READ_PERSECOND));
        assertEquals(ResultSetStub.DOUBLE_VALUE, metrics.getValue(ESConst.DEVICE_WRITE_PERSECOND));
        assertEquals(ResultSetStub.DOUBLE_VALUE, metrics.getValue(ESConst.DEVICE_TPS));
    }

    private String getExpectedSql() {
        return "select AgentId, AgentStartTime,TRUNC(TO_DATE(CollectTime,'yyyyMMddHHmmss'),'SECOND')  as CollectTime1\n" +
                ",avg(Tps) as Tps, avg(ReadPerSecond) as ReadPerSecond, avg(WritePerSecond) as WritePerSecond\n" +
                "from SMARTSIGHT.DEVICE_V1\n" +
                "    where CollectTime>='19700101080000'\n" +
                "    and CollectTime<='19700101080000'\n" +
                "    and AgentId='fm'\n" +
                "    and AgentStartTime='100'\n" +
                "    group by AgentId, AgentStartTime,TRUNC(TO_DATE(CollectTime,'yyyyMMddHHmmss'),'SECOND')";
    }


}