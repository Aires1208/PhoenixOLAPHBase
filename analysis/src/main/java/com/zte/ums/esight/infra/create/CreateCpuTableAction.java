package com.zte.ums.esight.infra.create;


import com.zte.ums.esight.domain.quartz.CreateTableAction;
import com.zte.ums.esight.infra.DBConst;

public class CreateCpuTableAction extends CreateTableAction {
    public CreateCpuTableAction() {
        super(CPU_TABLE);
    }

    @Override
    public String getCreateSql() {
        return "CREATE TABLE IF NOT EXISTS " + CPU_FULL_TABLE + " (  \n" +
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
                ")" + TTL_30_DAYS;
    }
}
