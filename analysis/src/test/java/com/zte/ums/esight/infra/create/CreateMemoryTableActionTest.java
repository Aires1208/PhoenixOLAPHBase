package com.zte.ums.esight.infra.create;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by 10018761 on 2017/4/7.
 */
public class CreateMemoryTableActionTest {
    @Test
    public void getCreateSql() throws Exception {
        String actualSql = new CreateMemoryTableAction().getCreateSql();
        assertEquals(getExpectedSql(),actualSql);
        System.out.println(actualSql);
    }

    private String getExpectedSql() {
        return "CREATE TABLE IF NOT EXISTS SMARTSIGHT.MEMORY_V1 (  \n" +
                " AgentId varchar , \n" +
                " CollectTime varchar, \n" +
                " Mac varchar , \n" +
                " AgentStartTime varchar,\n" +
                " VmTotal BIGINT,\n" +
                " VmFree BIGINT,\n" +
                " VmUsed BIGINT,\n" +
                " PhyTotal BIGINT,\n" +
                " PhyFree BIGINT,\n" +
                " PhyUsed BIGINT,\n" +
                " SwapTotal BIGINT,\n" +
                " SwapFree BIGINT,\n" +
                " SwapUsed BIGINT\n" +
                " CONSTRAINT memory_pk PRIMARY KEY (AgentId,CollectTime,Mac,AgentStartTime)\n" +
                ")TTL=2592000";
    }

}