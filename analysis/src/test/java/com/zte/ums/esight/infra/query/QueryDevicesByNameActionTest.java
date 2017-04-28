package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class QueryDevicesByNameActionTest {
    @Test
    public void getSql() throws Exception {
        ESQueryCond queryCond = new ESQueryCond.ESQueryCondBuild()
                .agentId("fm")
                .agentStartTime(100L)
                .from(1L)
                .to(2L)
                .build();
        String sql = new QueryDevicesByNameAction().getSql(queryCond).trim();

        assertEquals(getExpectedSql(), sql);
        System.out.println(sql);
    }

    @Test
    public void parse() throws Exception {
        //given
        ResultSetStub resultSetStub = new ResultSetStub();

        //when
        List<ESMetrics> metricses = new QueryDevicesByNameAction().parse(resultSetStub);

        //then
        assert metricses.size() == 1;

        ESMetrics metrics = metricses.get(0);
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.DEVICE_NAME));
        assertEquals(ResultSetStub.LONG_VALUE, metrics.getValue(ESConst.DEVICE_READ));
        assertEquals(ResultSetStub.LONG_VALUE, metrics.getValue(ESConst.DEVICE_WRITE));
        assertEquals(ResultSetStub.DOUBLE_VALUE, metrics.getValue(ESConst.DEVICE_TPS));
    }

    private String getExpectedSql() {
        return "select AgentId, AgentStartTime,DeviceName\n" +
                ",avg(Tps) as Tps, max(Read) as Read, max(Write) as Write\n" +
                "from SMARTSIGHT.DEVICE_V1\n" +
                "    where CollectTime>='19700101080000'\n" +
                "    and CollectTime<='19700101080000'\n" +
                "    and AgentId='fm'\n" +
                "    and AgentStartTime='100'\n" +
                "    group by AgentId, AgentStartTime,DeviceName";
    }


}