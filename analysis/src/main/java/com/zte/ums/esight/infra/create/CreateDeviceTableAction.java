package com.zte.ums.esight.infra.create;


import com.zte.ums.esight.domain.quartz.CreateTableAction;
import com.zte.ums.esight.infra.DBConst;

public class CreateDeviceTableAction extends CreateTableAction {
    public CreateDeviceTableAction() {
        super(DEVICE_TABLE);
    }

    @Override
    public String getCreateSql() {
        return "CREATE TABLE IF NOT EXISTS " + DEVICE_FULL_TABLE + " (  \n" +
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
                ")" + TTL_30_DAYS;
    }
}
