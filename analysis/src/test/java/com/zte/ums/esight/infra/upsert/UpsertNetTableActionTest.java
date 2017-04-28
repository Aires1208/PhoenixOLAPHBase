package com.zte.ums.esight.infra.upsert;

import com.zte.ums.esight.domain.model.PhoenixAgentStat;
import com.zte.ums.esight.domain.model.phoenix.ESDataFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UpsertNetTableActionTest {
    @Test
    public void buildSql() throws Exception {
        PhoenixAgentStat phoenixAgentStat = ESDataFactory.getPhoenixAgentStat();
        List<String> sqls = new UpsertNetTableAction().buildSql(phoenixAgentStat);
        assertEquals(getExpectedSqls(), sqls.toString());
        System.out.println(sqls);
    }

    private String getExpectedSqls() {
        return "[upsert into SMARTSIGHT.NET_V1 (\n" +
                "  AgentId, AgentStartTime,Mac,CollectTime, Name, V4Address,\n" +
                "  MacAddress,Mtu,ReceiveBytes, ReceiveErrors,TransmitBytes,\n" +
                "  TransmitErrors, Colls\n" +
                "  )\n" +
                "values (\n" +
                "  'fm-agent80','1483253400000',' ','20170101145100','eth0','272001','02234ajk',100,10,1,100,10,10\n" +
                ")\n" +
                ", upsert into SMARTSIGHT.NET_V1 (\n" +
                "  AgentId, AgentStartTime,Mac,CollectTime, Name, V4Address,\n" +
                "  MacAddress,Mtu,ReceiveBytes, ReceiveErrors,TransmitBytes,\n" +
                "  TransmitErrors, Colls\n" +
                "  )\n" +
                "values (\n" +
                "  'fm-agent80','1483253400000',' ','20170101145100','lo','2062011','12234ajk',1000,100,10,1000,100,100\n" +
                ")\n" +
                "]";
    }

}