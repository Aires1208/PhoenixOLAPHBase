package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
public class ProcessByPidActionTest {
    @Test
    public void getSql() throws Exception {
        ESQueryCond queryCond = new ESQueryCond.ESQueryCondBuild()
                .agentId("fm")
                .agentStartTime(100L)
                .from(1L)
                .to(2L)
                .build();
        String sql = new ProcessByPidAction().getSql(queryCond).trim();

        assertEquals(getExpectedSql(), sql);
        System.out.println(sql);
    }

    @Test
    public void parse() throws Exception {
        //given
        ResultSetStub resultSetStub = new ResultSetStub();

        //when
        List<ESMetrics> metricses = new ProcessByPidAction().parse(resultSetStub);

        //then
        assert metricses.size() == 1;

        ESMetrics metrics = metricses.get(0);
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.AGENT_ID));
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.AGENT_STARTTIME));
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.PROCESS_PID));
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.PROCESS_NAME));
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.PROCESS_COMMAND));
        assertEquals(ResultSetStub.DOUBLE_VALUE, metrics.getValue(ESConst.PROCESS_CPU_USAGE));
        assertEquals(ResultSetStub.LONG_VALUE, metrics.getValue(ESConst.PROCESS_CPU_TIME));
        assertEquals(ResultSetStub.DOUBLE_VALUE, metrics.getValue(ESConst.PROCESS_VIRT));
        assertEquals(ResultSetStub.DOUBLE_VALUE, metrics.getValue(ESConst.PROCESS_RES));
    }

    private String getExpectedSql() {
        return "select AgentId, AgentStartTime, Pid, Process,Command,\n" +
                " avg(CpuUsage) as CpuUsage, max(CpuTime) as CpuTime,avg(Virt) as Virt,avg(Res) as Res\n" +
                "from SMARTSIGHT.PROCESS_V1\n" +
                "where CollectTime>='19700101080000'\n" +
                "    and CollectTime<='19700101080000'\n" +
                "    and AgentId='fm'\n" +
                "    and AgentStartTime='100'\n" +
                "group by AgentId, AgentStartTime, Pid, Process,Command";
    }


}