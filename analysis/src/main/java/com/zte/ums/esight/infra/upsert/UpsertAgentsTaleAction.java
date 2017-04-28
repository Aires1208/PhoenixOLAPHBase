package com.zte.ums.esight.infra.upsert;

import com.zte.ums.esight.domain.model.PhoenixAgentStat;
import com.zte.ums.esight.infra.PhoenixDateUtil;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class UpsertAgentsTaleAction extends UpsertAction {
    @Override
    public List<String> buildSql(PhoenixAgentStat phoenixAgentStat) {
        StringBuilder sb = new StringBuilder();
        sb.append("upsert into ").append(AGENTINDEX_FULL_TABLE).append(" (").append(NEW_LINE)
                .append(TWO_BLANK).append("AgentId, AgentStartTime, MacAddress, CollectTime, ").append(NEW_LINE)
                .append(TWO_BLANK).append("CpuUsage, MemUsage, IoRead, IoWrite, DlSpeed, UlSpeed, ")
                .append("CpuShares, CpuPeriod, CpuQuota, CpuSet )").append(NEW_LINE)
                .append(TWO_BLANK).append("values (").append(NEW_LINE)
                .append(TWO_BLANK).append("'").append(phoenixAgentStat.getAgentId()).append("', ")
                .append("'").append(phoenixAgentStat.getStartTime()).append("', ")
                .append("'").append(phoenixAgentStat.getMacAddress()).append("', ")
                .append("'").append(PhoenixDateUtil.dateStrMs(phoenixAgentStat.getCollectTime())).append("', ")
                .append(phoenixAgentStat.getCpuUsage()).append(", ")
                .append(phoenixAgentStat.getMemUsage()).append(", ")
                .append(phoenixAgentStat.getIoRead()).append(", ")
                .append(phoenixAgentStat.getIoWrite()).append(", ")
                .append(phoenixAgentStat.getDlSpeed()).append(", ")
                .append(phoenixAgentStat.getUlSpeed()).append(", ")
                .append("'").append(phoenixAgentStat.getCpuShares()).append("', ")
                .append("'").append(phoenixAgentStat.getCpuPeriod()).append("', ")
                .append("'").append(phoenixAgentStat.getCpuQuota()).append("', ")
                .append("'").append(phoenixAgentStat.getCpuSet()).append("')")
                .append(NEW_LINE);

        return newArrayList(sb.toString());
    }
}
