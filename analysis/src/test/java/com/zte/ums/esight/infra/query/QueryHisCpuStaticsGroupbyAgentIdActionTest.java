package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class QueryHisCpuStaticsGroupbyAgentIdActionTest {
    @Test
    public void getSql() throws Exception {
        ESQueryCond queryCond = new ESQueryCond.ESQueryCondBuild()
                .agentId("fm")
                .agentStartTime(100L)
                .from(1L)
                .to(2L)
                .build();
        String sql = new QueryHisCpuStaticsGroupbyAgentIdAction("MAX").getSql(queryCond).trim();

        assertEquals(getExpectedSql(), sql);
        System.out.println(sql);
    }

    @Test
    public void parse() throws Exception {
        //given
        ResultSetStub resultSetStub = new ResultSetStub();

        //when
        List<ESMetrics> metricses = new QueryHisCpuStaticsGroupbyAgentIdAction("MAX").parse(resultSetStub);

        //then
        assert metricses.size() == 1;

        ESMetrics metrics = metricses.get(0);
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.AGENT_ID));
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.AGENT_STARTTIME));
        assertEquals(ResultSetStub.NEGATIVE_VALUE, metrics.getValue(ESConst.COLLECT_TIME));
        assertEquals(ResultSetStub.LONG_VALUE, metrics.getValue(ESConst.CPU_USER));
        assertEquals(ResultSetStub.LONG_VALUE, metrics.getValue(ESConst.CPU_NICE));
        assertEquals(ResultSetStub.LONG_VALUE, metrics.getValue(ESConst.CPU_SYSTEM));
        assertEquals(ResultSetStub.LONG_VALUE, metrics.getValue(ESConst.CPU_IDEL));
        assertEquals(ResultSetStub.LONG_VALUE, metrics.getValue(ESConst.CPU_IOWAIT));
        assertEquals(ResultSetStub.LONG_VALUE, metrics.getValue(ESConst.CPU_IRQ));
        assertEquals(ResultSetStub.LONG_VALUE, metrics.getValue(ESConst.CPU_SOFTIRQ));
    }

    private String getExpectedSql() {
        return "select AgentId, AgentStartTime, CollectTime1 as CollectTime,sum(CpuUser) as CpuUser,\n" +
                "  sum(CpuNice) as CpuNice, sum(CpuSystem) as CpuSystem,\n" +
                "  sum(CpuIdel) as CpuIdel ,sum(CpuIowait) as CpuIowait,\n" +
                "  sum(CpuIRQ) as CpuIRQ, sum(CpuSoftIrq) as CpuSoftIrq\n" +
                "  from\n" +
                "  (\n" +
                "    select AgentId, AgentStartTime,TRUNC(TO_DATE(CollectTime,'yyyyMMddHHmmss'),'SECOND')  as CollectTime1,CpuId,\n" +
                "      MAX(CpuUser) as CpuUser ,MAX(CpuNice) as CpuNice ,MAX(CpuSystem) as CpuSystem ,\n" +
                "      MAX(CpuIdel) as CpuIdel ,MAX(CpuIowait) as CpuIowait ,MAX(CpuIRQ) as CpuIRQ ,MAX(CpuSoftIrq) as CpuSoftIrq \n" +
                "    from SMARTSIGHT.CPU_V1\n" +
                "    where CollectTime>='19700101080000'\n" +
                "    and CollectTime<='19700101080000'\n" +
                "    and AgentId='fm'\n" +
                "    and AgentStartTime='100'\n" +
                "    group by AgentId, AgentStartTime,TRUNC(TO_DATE(CollectTime,'yyyyMMddHHmmss'),'SECOND') ,CpuId\n" +
                "  )\n" +
                "group by AgentId, AgentStartTime, CollectTime";
    }

}