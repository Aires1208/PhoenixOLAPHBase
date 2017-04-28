package com.zte.ums.esight.infra.upsert;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.PhoenixAgentStat;
import com.zte.ums.esight.infra.PhoenixDateUtil;

import java.util.ArrayList;
import java.util.List;

public class UpsertMemoryTableAction extends UpsertAction {


    @Override
    public List<String> buildSql(PhoenixAgentStat phoenixAgentStat) {
        List<String> jsonDatas = new ArrayList<>();

        List<ESMetrics> esMetricses = phoenixAgentStat.getResult(ESConst.MEMORY_TYPE).getEsMetricses();

        for (ESMetrics esMetrics : esMetricses) {
            StringBuilder sb = new StringBuilder();
            sb.append("upsert into ").append(MEMORY_FULL_TABLE).append(" (").append(NEW_LINE)
                    .append(TWO_BLANK).append("AgentId, AgentStartTime,Mac,CollectTime,").append(NEW_LINE)
                    .append(TWO_BLANK).append("VmTotal, VmFree, VmUsed,").append(NEW_LINE)
                    .append(TWO_BLANK).append("PhyTotal, PhyFree, PhyUsed,").append(NEW_LINE)
                    .append(TWO_BLANK).append("SwapTotal, SwapFree, SwapUsed").append(NEW_LINE)
                    .append(TWO_BLANK).append(")").append(NEW_LINE)
                    .append("values (").append(NEW_LINE)
                    .append(TWO_BLANK).append("'").append(phoenixAgentStat.getAgentId()).append("'").append(",")
                    .append("'").append(phoenixAgentStat.getStartTime()).append("'").append(",")
                    .append("'").append(phoenixAgentStat.getMacAddress()).append("'").append(",")
                    .append("'").append(PhoenixDateUtil.dateStr(phoenixAgentStat.getCollectTime())).append("'").append(",")

                    .append(esMetrics.getValue(ESConst.VM_TOTAL)).append(",")
                    .append(esMetrics.getValue(ESConst.VM_FREE)).append(",")
                    .append(esMetrics.getValue(ESConst.VM_USED)).append(",")

                    .append(esMetrics.getValue(ESConst.PHY_TOTAL)).append(",")
                    .append(esMetrics.getValue(ESConst.PHY_FREE)).append(",")
                    .append(esMetrics.getValue(ESConst.PHY_USED)).append(",")

                    .append(esMetrics.getValue(ESConst.SWAP_TOTAL)).append(",")
                    .append(esMetrics.getValue(ESConst.SWAP_FREE)).append(",")
                    .append(esMetrics.getValue(ESConst.SWAP_USED)).append(NEW_LINE)


                    .append(")").append(NEW_LINE);


            jsonDatas.add(sb.toString());
        }

        return jsonDatas;
    }
}
