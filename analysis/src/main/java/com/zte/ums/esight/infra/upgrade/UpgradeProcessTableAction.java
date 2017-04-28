package com.zte.ums.esight.infra.upgrade;


import com.zte.ums.esight.domain.quartz.UpgradeTableAction;

public class UpgradeProcessTableAction extends UpgradeTableAction {
    private final static String FULL_TABLE = SCHEMA + "." + PROCESS_TABLE;
    private final static String COLUMN = "RES";

    public UpgradeProcessTableAction() {
        super(PROCESS_TABLE, COLUMN);
    }

    @Override
    public String getCreateSql() {
        return "ALTER TABLE " + FULL_TABLE + " ADD Res BIGINT";
    }

}
