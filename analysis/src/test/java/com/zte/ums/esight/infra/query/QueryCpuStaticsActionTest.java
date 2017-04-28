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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PhoenixDBUtil.class})
public class QueryCpuStaticsActionTest {
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
        String sql = new QueryCpuStaticsAction("MAX").getSql(queryCond).trim();

        assertEquals(getExpectedSql(), sql);
        System.out.println(sql);
    }

    @Test
    public void sortResultByCpuId() {
        //given
        List<ESMetrics> metricses = new ArrayList<>();
        Map<String, Object> firstMap = new HashMap<>();
        firstMap.put(ESConst.CPU_ID, "cpu2");
        metricses.add(new ESMetrics(firstMap));

        Map<String, Object> secondMap = new HashMap<>();
        secondMap.put(ESConst.CPU_ID, "cpu1");
        metricses.add(new ESMetrics(secondMap));

        //when
        QueryCpuStaticsAction queryCpuStaticsAction = new QueryCpuStaticsAction("MAX");
        metricses.sort((o1, o2) -> queryCpuStaticsAction.sortByCpuId(o1, o2));

        //then
        String actualId = metricses.get(0).getValue(ESConst.CPU_ID).toString();
        assertEquals("cpu1", actualId);

    }

    @Test
    public void parse() throws Exception {
        //given
        ResultSetStub resultSetStub = new ResultSetStub();

        //when
        List<ESMetrics> metricses = new QueryCpuStaticsAction("MAX").parse(resultSetStub);

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
        return "select AgentId, AgentStartTime, CollectTime, CpuId,CpuVendor, CpuFamily, CpuModel, CpuModelName,CpuMHZ, CpuCache,CpuUser,CpuNice,CpuSystem ,CpuIdel,CpuIowait,CpuIRQ,CpuSoftIrq from SMARTSIGHT.CPU_V1 where CollectTime='2001-01-02'    and AgentId='fm'\n" +
                "    and AgentStartTime='100'";
    }


}