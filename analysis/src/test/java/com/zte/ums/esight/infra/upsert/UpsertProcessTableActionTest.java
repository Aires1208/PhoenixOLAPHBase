package com.zte.ums.esight.infra.upsert;

import com.zte.ums.esight.domain.model.PhoenixAgentStat;
import com.zte.ums.esight.domain.model.phoenix.ESDataFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UpsertProcessTableActionTest {
    @Test
    public void buildSql() throws Exception {
        PhoenixAgentStat phoenixAgentStat = ESDataFactory.getPhoenixAgentStat();
        List<String> sqls = new UpsertProcessTableAction().buildSql(phoenixAgentStat);
        assertEquals(getExpectedSqls(), sqls.toString());
        System.out.println(sqls);
    }

    private String getExpectedSqls() {
        return "[upsert into SMARTSIGHT.PROCESS_V1 (\n" +
                "  Mac,AgentId, AgentStartTime, CollectTime,\n" +
                "  Pid, Process,Command, CpuUsage, CpuTime,Virt,Res\n" +
                "  )\n" +
                "values (\n" +
                "  ' ','fm-agent80','1483253400000','20170101145100','1','java','/usr/bin/java',0.09,100,3000,2000\n" +
                ")\n" +
                ", upsert into SMARTSIGHT.PROCESS_V1 (\n" +
                "  Mac,AgentId, AgentStartTime, CollectTime,\n" +
                "  Pid, Process,Command, CpuUsage, CpuTime,Virt,Res\n" +
                "  )\n" +
                "values (\n" +
                "  ' ','fm-agent80','1483253400000','20170101145100','2','python','/usr/bin/python',0.89,1000,4000,1234\n" +
                ")\n" +
                "]";
    }

}