package com.zte.ums.esight.infra.upsert;

import com.zte.ums.esight.domain.model.PhoenixAgentStat;
import com.zte.ums.esight.infra.DBConst;

import java.util.List;

public abstract class UpsertAction implements DBConst{
    public abstract List<String> buildSql(PhoenixAgentStat phoenixAgentStat);
}
