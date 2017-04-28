package com.zte.ums.esight.infra.create;

import com.zte.ums.esight.domain.quartz.CreateTableAction;
import com.zte.ums.esight.infra.DBConst;

public class CreateProcessTableAction extends CreateTableAction {
    public CreateProcessTableAction() {
        super(PROCESS_TABLE);
    }

    @Override
    public String getCreateSql() {
        return "CREATE TABLE IF NOT EXISTS " + PROCESS_FULL_TABLE + " (  \n" +
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
                ")" + TTL_30_DAYS;
    }
}
