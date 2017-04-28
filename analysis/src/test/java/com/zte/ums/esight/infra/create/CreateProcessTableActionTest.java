package com.zte.ums.esight.infra.create;

import org.junit.Test;

import static org.junit.Assert.*;

public class CreateProcessTableActionTest {
    @Test
    public void getCreateSql() throws Exception {
        String actualSql = new  CreateProcessTableAction().getCreateSql();

        assertEquals(getExpectedSql(),actualSql);

        System.out.println(actualSql);
    }

    private String getExpectedSql() {
        return "CREATE TABLE IF NOT EXISTS SMARTSIGHT.PROCESS_V1 (  \n" +
                " AgentId varchar , \n" +
                " CollectTime varchar, \n" +
                " Mac varchar , \n" +
                " AgentStartTime varchar,\n" +
                " Pid varchar,  \n" +
                " Command varchar,  \n" +
                " Process varchar,  \n" +
                " CpuUsage DOUBLE,  \n" +
                " CpuTime BIGINT,   \n" +
                " Virt BIGINT, \n" +
                " Res BIGINT  \n" +
                " CONSTRAINT process_pk PRIMARY KEY (AgentId,CollectTime,Mac,AgentStartTime,Pid)\n" +
                ")TTL=2592000";
    }

}