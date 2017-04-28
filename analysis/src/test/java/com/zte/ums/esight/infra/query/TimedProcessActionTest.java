package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.infra.PhoenixDBUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PhoenixDBUtil.class})
public class TimedProcessActionTest {
    @Before
    public void before() throws Exception {

        PowerMockito.mockStatic(PhoenixDBUtil.class);
        PowerMockito.when(PhoenixDBUtil.getCollectTime(anyObject())).thenReturn("2001-01-02");
    }

    @Test
    public void getSql() throws Exception {
        ESQueryCond queryCond = new ESQueryCond.ESQueryCondBuild()
                .agentId("fm")
                .agentStartTime(100L)
                .from(1L)
                .to(2L)
                .build();
        String sql = new TimedProcessAction("MAX").getSql(queryCond).trim();

        assertEquals(getExpectedSql(), sql);
        System.out.println(sql);
    }

    @Test
    public void parse() throws Exception {
        //given
        ResultSetStub resultSetStub = new ResultSetStub();

        //when
        List<ESMetrics> metricses = new TimedProcessAction("MAX").parse(resultSetStub);

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
        assertEquals(ResultSetStub.LONG_VALUE, metrics.getValue(ESConst.PROCESS_VIRT));
        assertEquals(ResultSetStub.LONG_VALUE, metrics.getValue(ESConst.PROCESS_RES));
    }

    private String getExpectedSql() {
        return "select AgentId, AgentStartTime, CollectTime, Pid, \n" +
                "Process,Command, CpuUsage, CpuTime,Virt,Res\n" +
                "from SMARTSIGHT.PROCESS_V1\n" +
                "where CollectTime='2001-01-02'    and AgentId='fm'\n" +
                "    and AgentStartTime='100'";
    }


}