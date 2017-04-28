package com.zte.ums.esight.infra.create;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateCpuTableActionTest {
    @Test
    public void getCreateSql() throws Exception {
        String actualSql = new CreateCpuTableAction().getCreateSql();
        assertEquals(getExpectedSql(), actualSql.trim());
        System.out.println(actualSql);
    }

    private String getExpectedSql() {
        return "CREATE TABLE IF NOT EXISTS SMARTSIGHT.CPU_V1 (  \n" +
                " AgentId varchar , \n" +
                " CollectTime varchar,\n" +
                " Mac varchar , \n" +
                " AgentStartTime varchar,\n" +
                " CpuId varchar,\n" +
                " CpuVendor varchar,\n" +
                " CpuFamily varchar,\n" +
                " CpuModel varchar,\n" +
                " CpuModelName varchar,\n" +
                " CpuMHZ varchar,\n" +
                " CpuCache varchar,\n" +
                " CpuUser BIGINT,\n" +
                " CpuNice BIGINT,\n" +
                " CpuSystem BIGINT,\n" +
                " CpuIdel BIGINT,\n" +
                " CpuIowait BIGINT,\n" +
                " CpuIRQ BIGINT,\n" +
                " CpuSoftIrq BIGINT\n" +
                " CONSTRAINT cpu_pk PRIMARY KEY (AgentId,CollectTime,Mac, AgentStartTime,CpuId)\n" +
                ")TTL=2592000";
    }

}