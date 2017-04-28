package com.zte.ums.esight.infra.upgrade;

import com.zte.ums.esight.domain.quartz.UpgradeTableAction;

/**
 * Created by root on 17-4-20.
 */
public class UpgradeAgentIndexTableAction extends UpgradeTableAction {


    public UpgradeAgentIndexTableAction() {
        super(AGENTINDEX_TABLE, COLUMN__CPU_SHARES);
    }

    @Override
    public String getCreateSql() {
        return "ALTER TABLE " + AGENTINDEX_FULL_TABLE + " ADD CpuShares varchar, CpuPeriod varchar, CpuQuota varchar, CpuSet varchar";
    }
}
