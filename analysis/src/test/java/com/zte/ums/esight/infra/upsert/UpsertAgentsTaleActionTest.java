package com.zte.ums.esight.infra.upsert;

import com.zte.ums.esight.domain.model.PhoenixAgentStat;
import com.zte.ums.esight.infra.PhoenixDateUtil;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class UpsertAgentsTaleActionTest {
    @Test
    public void buildSql() throws Exception {

        //given
        UpsertAgentsTaleAction action = new UpsertAgentsTaleAction();
        long timestamp = System.currentTimeMillis();
        String startTime = PhoenixDateUtil.dateStrMs(timestamp);
        String sql = "upsert into SMARTSIGHT.AGENTINDEX_v1 (\n  AgentId, AgentStartTime, MacAddress, CollectTime, \n  CpuUsage, MemUsage, IoRead, IoWrite, DlSpeed, UlSpeed, CpuShares, CpuPeriod, CpuQuota, CpuSet )\n  values (\n  'testAgent', '" + timestamp + "', '52:54:00:b5:d7:14', '" + startTime + "', 0.77643, 0.7676, 0.0932, 0.976, 0.2334, 3.321, 'shares', 'period', 'quota', 'cpuset')\n";

        PhoenixAgentStat phoenixAgentStat = PhoenixAgentStat.Buidler()
                .AgentId("testAgent")
                .StartTime(timestamp)
                .MacAddress("52:54:00:b5:d7:14")
                .CollectTime(timestamp)
                .CpuUsage(0.77643)
                .MemUsage(0.7676)
                .IoRead(0.0932)
                .IoWrite(0.976)
                .DlSpeed(0.2334)
                .UlSpeed(3.321)
                .CpuShares("shares")
                .CpuPeriod("period")
                .CpuQuota("quota")
                .CpuSet("cpuset")
                .build();

        List<String> sqls = action.buildSql(phoenixAgentStat);

        assertEquals(sqls.size(), 1);
        assertThat(sqls.get(0), is(sql));
        System.out.println(sqls);

    }

}