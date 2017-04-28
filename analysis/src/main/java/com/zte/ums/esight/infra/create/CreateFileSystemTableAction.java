package com.zte.ums.esight.infra.create;


import com.zte.ums.esight.domain.quartz.CreateTableAction;
import com.zte.ums.esight.infra.DBConst;

public class CreateFileSystemTableAction extends CreateTableAction {
    public CreateFileSystemTableAction() {
        super(FILESYSTEM_TABLE);
    }

    @Override
    public String getCreateSql() {
        return "CREATE TABLE IF NOT EXISTS " + FILESYSTEM_FULL_TABLE + " (  \n" +
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
                ")" + TTL_30_DAYS;
    }
}
