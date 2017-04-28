package com.zte.ums.esight.infra.create;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateFileSystemTableActionTest {
    @Test
    public void getCreateSql() throws Exception {

        String sql = new CreateFileSystemTableAction().getCreateSql();

        assertEquals(getExpectedSql(),sql);
        System.out.println(sql);

    }

    private String getExpectedSql() {
        return "CREATE TABLE IF NOT EXISTS SMARTSIGHT.FILESYSTEM_V1 (  \n" +
                " AgentId varchar , \n" +
                " CollectTime varchar,\n" +
                " Mac varchar , \n" +
                " AgentStartTime varchar,\n" +
                " MountedOn varchar,\n" +
                " FileSystem varchar, \n" +
                " Total BIGINT,\n" +
                " Used BIGINT,\n" +
                " Free BIGINT \n" +
                " CONSTRAINT filesystem_pk PRIMARY KEY (AgentId,CollectTime,Mac,AgentStartTime,MountedOn)\n" +
                ")TTL=2592000";
    }

}