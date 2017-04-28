package com.zte.ums.esight.infra.create;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateAgentsTableActionTest {
    @Test
    public void getCreateSql() throws Exception {

        String actualSql = new CreateAgentsTableAction().getCreateSql();

        System.out.println(actualSql);
        assertEquals(getExpectedSql(), actualSql);

    }

    private String getExpectedSql() {
        return "CREATE TABLE IF NOT EXISTS SMARTSIGHT.AGENTINDEX_v1 (  \n" +
                " AgentId varchar , \n" +
                " AgentStartTime varchar,\n" +
                " MacAddress varchar,\n" +
                " CollectTime varchar,\n" +
                " CpuUsage double,\n" +
                " MemUsage double,\n" +
                " IoRead double,\n" +
                " IoWrite double,\n" +
                " DlSpeed double,\n" +
                " UlSpeed double,\n" +
                " CpuShares varchar,\n" +
                " CpuPeriod varchar,\n" +
                " CpuQuota varchar,\n" +
                " CpuSet varchar \n" +
                " CONSTRAINT cpu_pk PRIMARY KEY (AgentId, AgentStartTime, MacAddress)\n" +
                ")TTL=31536000";
    }

}