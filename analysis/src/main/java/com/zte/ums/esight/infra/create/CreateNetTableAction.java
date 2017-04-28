package com.zte.ums.esight.infra.create;


import com.zte.ums.esight.domain.quartz.CreateTableAction;
import com.zte.ums.esight.infra.DBConst;

public class CreateNetTableAction extends CreateTableAction {
    public CreateNetTableAction() {
        super(NET_TABLE);
    }

    @Override
    public String getCreateSql() {
        return "CREATE TABLE IF NOT EXISTS " + NET_FULL_TABLE + " (  \n" +
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
                ")" + TTL_30_DAYS;
    }
}
