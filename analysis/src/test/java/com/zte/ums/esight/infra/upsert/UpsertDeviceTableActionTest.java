package com.zte.ums.esight.infra.upsert;

import com.zte.ums.esight.domain.model.PhoenixAgentStat;
import com.zte.ums.esight.domain.model.phoenix.ESDataFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class UpsertDeviceTableActionTest {
    @Test
    public void buildSql() throws Exception {
        PhoenixAgentStat phoenixAgentStat = ESDataFactory.getPhoenixAgentStat();
        List<String> sqls = new UpsertDeviceTableAction().buildSql(phoenixAgentStat);
        assertEquals(getExpectedSqls(), sqls.toString());
        System.out.println(sqls);
    }

    private String getExpectedSqls() {
        return "[upsert into SMARTSIGHT.DEVICE_V1 (\n" +
                "  AgentId, AgentStartTime,Mac,CollectTime, DeviceName,\n" +
                "  Read,Write,Tps, ReadPerSecond,WritePerSecond\n" +
                "  )\n" +
                "values (\n" +
                "  'fm-agent80','1483253400000',' ','20170101145100','dm-0',100,200,10.0,11.0,22.0\n" +
                ")\n" +
                ", upsert into SMARTSIGHT.DEVICE_V1 (\n" +
                "  AgentId, AgentStartTime,Mac,CollectTime, DeviceName,\n" +
                "  Read,Write,Tps, ReadPerSecond,WritePerSecond\n" +
                "  )\n" +
                "values (\n" +
                "  'fm-agent80','1483253400000',' ','20170101145100','dm-1',1000,2000,100.0,110.0,220.0\n" +
                ")\n" +
                "]";
    }

}