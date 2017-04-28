package com.zte.ums.esight.infra.upsert;

import com.zte.ums.esight.domain.model.PhoenixAgentStat;
import com.zte.ums.esight.infra.PhoenixDBUtil;

import java.util.ArrayList;
import java.util.List;


public class UpsertAgentStatAction extends UpsertAction{
    private UpsertAction[] actions = new UpsertAction[]{
            new UpsertCpuTableAction(), new UpsertDeviceTableAction(),
            new UpsertFileSystemTableAction(), new UpsertMemoryTableAction(),
            new UpsertNetTableAction(), new UpsertProcessTableAction(),
            new UpsertAgentsTaleAction()
    };

    public UpsertAgentStatAction() {
        super();
    }

    public List<String> buildSql(PhoenixAgentStat phoenixAgentStat) {
        List<String> sqls = new ArrayList<>();
        for (UpsertAction action : actions) {
            sqls.addAll(action.buildSql(phoenixAgentStat));
        }

        return sqls;
    }

    public boolean store(List<String> sqls) {
        return PhoenixDBUtil.store(sqls);
    }

}
