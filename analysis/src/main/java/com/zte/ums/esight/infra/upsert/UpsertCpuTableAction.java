package com.zte.ums.esight.infra.upsert;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.PhoenixAgentStat;
import com.zte.ums.esight.infra.PhoenixDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpsertCpuTableAction extends UpsertAction {
    private static Logger logger = LoggerFactory.getLogger(UpsertCpuTableAction.class);

    @Override
    public List<String> buildSql(PhoenixAgentStat phoenixAgentStat) {
        List<String> jsonDatas = new ArrayList<String>();

        List<ESMetrics> esMetricses = phoenixAgentStat.getResult(ESConst.CPU_TYPE).getEsMetricses();

        Set<String> cpuSet = parse(phoenixAgentStat.getCpuSet());

        for (ESMetrics esMetrics : esMetricses) {

            String tmpCpuId = esMetrics.getValue(ESConst.CPU_ID).toString();
            if (!accept(tmpCpuId, cpuSet)) {
                logger.warn("cpuId is discard ," + tmpCpuId);
                logger.warn("cpuSet is " + cpuSet);
                continue;
            }


            StringBuilder sb = new StringBuilder();
            sb.append("upsert into ").append(CPU_FULL_TABLE).append(" (").append(NEW_LINE)
                    .append(TWO_BLANK).append("AgentId, AgentStartTime,Mac,CollectTime, CpuId,").append(NEW_LINE)
                    .append(TWO_BLANK).append("CpuVendor, CpuFamily, CpuModel, CpuModelName,").append(NEW_LINE)
                    .append(TWO_BLANK).append("CpuMHZ, CpuCache, CpuUser, CpuNice, CpuSystem,").append(NEW_LINE)
                    .append(TWO_BLANK).append("CpuIdel, CpuIowait, CpuIRQ, CpuSoftIrq").append(NEW_LINE)
                    .append(TWO_BLANK).append(")").append(NEW_LINE)
                    .append("values (").append(NEW_LINE)
                    .append(TWO_BLANK).append("'").append(phoenixAgentStat.getAgentId()).append("'").append(",")
                    .append("'").append(phoenixAgentStat.getStartTime()).append("'").append(",")
                    .append("'").append(phoenixAgentStat.getMacAddress()).append("'").append(",")
                    .append("'").append(PhoenixDateUtil.dateStr(phoenixAgentStat.getCollectTime())).append("'").append(",")
                    .append("'").append(esMetrics.getValue(ESConst.CPU_ID)).append("'").append(",")
                    .append("'").append(esMetrics.getValue(ESConst.CPU_VENDOR)).append("'").append(",")
                    .append("'").append(esMetrics.getValue(ESConst.CPU_FAMILY)).append("'").append(",")
                    .append("'").append(esMetrics.getValue(ESConst.CPU_MODEL)).append("'").append(",")
                    .append("'").append(esMetrics.getValue(ESConst.CPU_MODEL_NAME)).append("'").append(",")
                    .append("'").append(esMetrics.getValue(ESConst.CPU_MHZ)).append("'").append(",")
                    .append("'").append(esMetrics.getValue(ESConst.CPU_CACHE)).append("'").append(",")


                    .append(esMetrics.getValue(ESConst.CPU_USER)).append(",")
                    .append(esMetrics.getValue(ESConst.CPU_NICE)).append(",")
                    .append(esMetrics.getValue(ESConst.CPU_SYSTEM)).append(",")
                    .append(esMetrics.getValue(ESConst.CPU_IDEL)).append(",")
                    .append(esMetrics.getValue(ESConst.CPU_IOWAIT)).append(",")
                    .append(esMetrics.getValue(ESConst.CPU_IRQ)).append(",")
                    .append(esMetrics.getValue(ESConst.CPU_SOFTIRQ)).append(NEW_LINE)


                    .append(")").append(NEW_LINE);


            jsonDatas.add(sb.toString());
        }

        return jsonDatas;
    }

    protected boolean accept(String cpuId, Set<String> cpuSet) {
        boolean valid = true;
        cpuId = cpuId.replace("cpu", cpuId);
        if (cpuSet.size() == 0) {
            return true;
        }

        return cpuSet.contains(cpuId);
    }

    //0,1,3-4
    protected Set<String> parse(String cpus) {
        if("null".equalsIgnoreCase(cpus) || cpus.trim().length() == 0) {
            return new HashSet();
        }
        Set<String> cpuSet = new HashSet();
        String[] tokens = cpus.split(",");
        for (String token : tokens) {
            Set<String> oneCpuSet = parseOneItem(token);
            cpuSet.addAll(oneCpuSet);
        }

        return cpuSet;
    }

    private Set<String> parseOneItem(String item) {
        int MAX_CPU = 1000;
        Set<String> cpuSet = new HashSet();
        String[] tokens = item.split("-");
        if (tokens.length == 2) {
            int head = Integer.parseInt(tokens[0]);
            int tail = Integer.parseInt(tokens[1]);
            for (int i = head; i <= tail && i < MAX_CPU; i++) {
                cpuSet.add(String.valueOf(i));
            }

        } else {
            cpuSet.add(item);
        }
        return cpuSet;
    }

}
