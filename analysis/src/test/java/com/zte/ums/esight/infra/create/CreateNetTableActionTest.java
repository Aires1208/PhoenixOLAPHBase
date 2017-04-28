package com.zte.ums.esight.infra.create;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by 10018761 on 2017/4/7.
 */
public class CreateNetTableActionTest {
    @Test
    public void getCreateSql() throws Exception {
        String actualSql = new CreateNetTableAction().getCreateSql();

        assertEquals(getExpectedSql(),actualSql);

        System.out.println(actualSql);
    }

    private String getExpectedSql() {
        return "CREATE TABLE IF NOT EXISTS SMARTSIGHT.NET_V1 (  \n" +
                " AgentId varchar , \n" +
                " CollectTime varchar, \n" +
                " Mac varchar , \n" +
                " AgentStartTime varchar,\n" +
                " Name varchar,  \n" +
                " V4Address varchar,  \n" +
                " MacAddress varchar,  \n" +
                " Mtu BIGINT,   \n" +
                " ReceiveBytes BIGINT,\n" +
                " ReceiveErrors BIGINT,\n" +
                " TransmitBytes BIGINT,\n" +
                " TransmitErrors BIGINT,\n" +
                " Colls BIGINT  \n" +
                " CONSTRAINT net_pk PRIMARY KEY (AgentId,CollectTime,Mac,AgentStartTime,Name)\n" +
                ")TTL=2592000";
    }

}