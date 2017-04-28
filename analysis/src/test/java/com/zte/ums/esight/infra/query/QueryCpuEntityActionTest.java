package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class QueryCpuEntityActionTest {
    @Test
    public void getSql() throws Exception {
        ESQueryCond queryCond = new ESQueryCond.ESQueryCondBuild()
                .agentId("fm")
                .agentStartTime(100L)
                .from(1L)
                .to(2L)
                .build();
        String sql = new QueryCpuEntityAction().getSql(queryCond).trim();

        assertEquals(getExpectedSql(), sql);
        System.out.println(sql);
    }

    @Test
    public void parse() throws Exception {
        //given
        ResultSetStub resultSetStub = new ResultSetStub();

        //when
        List<ESMetrics> metricses = new QueryCpuEntityAction().parse(resultSetStub);

        //then
        assert metricses.size() == 1;

        ESMetrics metrics = metricses.get(0);
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.AGENT_ID));
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.AGENT_STARTTIME));
        assertEquals(ResultSetStub.NEGATIVE_VALUE, metrics.getValue(ESConst.COLLECT_TIME));


        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.CPU_ID));
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.CPU_VENDOR));
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.CPU_FAMILY));
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.CPU_MODEL));
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.CPU_MODEL_NAME));
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.CPU_MHZ));
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.CPU_CACHE));

        assertEquals(ResultSetStub.LONG_VALUE, metrics.getValue(ESConst.CPU_USER));
        assertEquals(ResultSetStub.LONG_VALUE, metrics.getValue(ESConst.CPU_NICE));
        assertEquals(ResultSetStub.LONG_VALUE, metrics.getValue(ESConst.CPU_SYSTEM));
        assertEquals(ResultSetStub.LONG_VALUE, metrics.getValue(ESConst.CPU_IDEL));
        assertEquals(ResultSetStub.LONG_VALUE, metrics.getValue(ESConst.CPU_IOWAIT));
        assertEquals(ResultSetStub.LONG_VALUE, metrics.getValue(ESConst.CPU_IRQ));
        assertEquals(ResultSetStub.LONG_VALUE, metrics.getValue(ESConst.CPU_SOFTIRQ));

    }

    private String getExpectedSql() {
        return "select AgentId, AgentStartTime, CollectTime, CpuId,CpuVendor, CpuFamily, CpuModel, CpuModelName,CpuMHZ, CpuCache,CpuUser,CpuNice,CpuSystem ,CpuIdel,CpuIowait,CpuIRQ,CpuSoftIrq from SMARTSIGHT.CPU_V1 where CollectTime>='19700101080000'\n" +
                "    and CollectTime<='19700101080000'\n" +
                "    and AgentId='fm'\n" +
                "    and AgentStartTime='100'";
    }


}