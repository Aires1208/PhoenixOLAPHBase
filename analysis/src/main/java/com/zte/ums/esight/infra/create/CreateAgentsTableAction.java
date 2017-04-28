package com.zte.ums.esight.infra.create;

import com.zte.ums.esight.domain.quartz.CreateTableAction;
import com.zte.ums.esight.infra.DBConst;

public class CreateAgentsTableAction extends CreateTableAction {

    public CreateAgentsTableAction() {
        super(AGENTINDEX_TABLE);
    }

    @Override
    public String getCreateSql() {
        return "CREATE TABLE IF NOT EXISTS " + AGENTINDEX_FULL_TABLE + " (  \n" +
                " AgentId varchar , \n" +
                " AgentStartTime varchar,\n" +
                " MacAddress varchar,\n" +
                " CollectTime varchar,\n" +
                " CpuUsage double,\n" +
                " MemUsage double,\n" +
                " IoRead double,\n" +
                " IoWrite double,\n" +
                " DlSpeed double,\n" +
                " UlSpeed double,\n" +
                " CpuShares varchar,\n" +
                " CpuPeriod varchar,\n" +
                " CpuQuota varchar,\n" +
                " CpuSet varchar \n" +
                " CONSTRAINT cpu_pk PRIMARY KEY (AgentId, AgentStartTime, MacAddress)\n" +
                ")" + TTL_ONE_YEAR;
    }
}
