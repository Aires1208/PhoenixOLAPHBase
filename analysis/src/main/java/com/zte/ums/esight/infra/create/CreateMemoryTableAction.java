package com.zte.ums.esight.infra.create;


import com.zte.ums.esight.domain.quartz.CreateTableAction;

public class CreateMemoryTableAction extends CreateTableAction {

    public CreateMemoryTableAction() {
        super(MEMORY_TABLE);
    }

    @Override
    public String getCreateSql() {
        return "CREATE TABLE IF NOT EXISTS " + MEMORY_FULL_TABLE + " (  \n" +
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
                ")" + TTL_30_DAYS;
    }
}
