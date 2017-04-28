package com.zte.ums.esight.infra.upsert;

import com.zte.ums.esight.domain.model.PhoenixAgentStat;
import com.zte.ums.esight.domain.model.phoenix.ESDataFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UpsertMemoryTableActionTest {
    @Test
    public void buildSql() throws Exception {
        PhoenixAgentStat phoenixAgentStat = ESDataFactory.getPhoenixAgentStat();
        List<String> sqls = new UpsertMemoryTableAction().buildSql(phoenixAgentStat);
        assertEquals(getExpectedSqls(), sqls.toString());
        System.out.println(sqls);
    }

    private String getExpectedSqls() {
        return "[upsert into SMARTSIGHT.MEMORY_V1 (\n" +
                "  AgentId, AgentStartTime,Mac,CollectTime,\n" +
                "  VmTotal, VmFree, VmUsed,\n" +
                "  PhyTotal, PhyFree, PhyUsed,\n" +
                "  SwapTotal, SwapFree, SwapUsed\n" +
                "  )\n" +
                "values (\n" +
                "  'fm-agent80','1483253400000',' ','20170101145100',100,30,70,0,0,0,0,0,0\n" +
                ")\n" +
                "]";
    }

}