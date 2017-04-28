package com.zte.ums.esight.infra.create;

import org.junit.Test;

import static org.junit.Assert.*;

public class CreateDeviceTableActionTest {
    @Test
    public void getCreateSql() throws Exception {
        String sql  = new CreateDeviceTableAction().getCreateSql();

        assertEquals(getExpectedSql(),sql);
        System.out.println(sql);


    }

    private String getExpectedSql() {
        return "CREATE TABLE IF NOT EXISTS SMARTSIGHT.DEVICE_V1 (  \n" +
                " AgentId varchar , \n" +
                " CollectTime varchar, \n" +
                " Mac varchar,\n" +
                " AgentStartTime varchar,\n" +
                " DeviceName varchar,  \n" +
                " Read BIGINT,\n" +
                " Write BIGINT,\n" +
                " Tps DOUBLE,\n" +
                " ReadPerSecond DOUBLE,\n" +
                " WritePerSecond DOUBLE  \n" +
                " CONSTRAINT device_pk PRIMARY KEY (AgentId,CollectTime,Mac,AgentStartTime,DeviceName)\n" +
                ")TTL=2592000";
    }

}