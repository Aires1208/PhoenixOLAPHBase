package com.zte.ums.esight.infra.upsert;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.PhoenixAgentStat;
import com.zte.ums.esight.infra.PhoenixDateUtil;

import java.util.ArrayList;
import java.util.List;

public class UpsertProcessTableAction extends UpsertAction {
    @Override
    public List<String> buildSql(PhoenixAgentStat phoenixAgentStat) {
        List<String> jsonDatas = new ArrayList<>();

        List<ESMetrics> esMetricses = phoenixAgentStat.getResult(ESConst.PROCESS_TYPE)
                .getEsMetricses();

        for (ESMetrics esMetrics : esMetricses) {
            StringBuilder sb = new StringBuilder();
            sb.append("upsert into ").append(PROCESS_FULL_TABLE).append(" (").append(NEW_LINE)
                    .append(TWO_BLANK).append("Mac,AgentId, AgentStartTime, CollectTime,").append(NEW_LINE)
                    .append(TWO_BLANK).append("Pid, Process,Command, CpuUsage, CpuTime,Virt,Res").append(NEW_LINE)
                    .append(TWO_BLANK).append(")").append(NEW_LINE)
                    .append("values (").append(NEW_LINE)
                    .append(TWO_BLANK).append("'").append(phoenixAgentStat.getMacAddress()).append("'").append(",")
                    .append("'").append(phoenixAgentStat.getAgentId()).append("'").append(",")
                    .append("'").append(phoenixAgentStat.getStartTime()).append("'").append(",")
                    .append("'").append(PhoenixDateUtil.dateStr(phoenixAgentStat.getCollectTime())).append("'").append(",")

                    .append("'").append(esMetrics.getValue(ESConst.PROCESS_PID)).append("'").append(",")
                    .append("'").append(esMetrics.getValue(ESConst.PROCESS_NAME)).append("'").append(",")
                    .append("'").append(esMetrics.getValue(ESConst.PROCESS_COMMAND)).append("'").append(",")

                    .append(esMetrics.getValue(ESConst.PROCESS_CPU_USAGE)).append(",")
                    .append(esMetrics.getValue(ESConst.PROCESS_CPU_TIME)).append(",")
                    .append(esMetrics.getValue(ESConst.PROCESS_VIRT)).append(",")
                    .append(esMetrics.getValue(ESConst.PROCESS_RES)).append(NEW_LINE)

                    .append(")").append(NEW_LINE);


            jsonDatas.add(sb.toString());

        }

        return jsonDatas;
    }
}
