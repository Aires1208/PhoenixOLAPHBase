package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.infra.PhoenixDateUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryCpuEntityAction extends QueryAction {

    public QueryCpuEntityAction() {
        super(ESConst.CPU_TYPE);
    }

    @Override
    public String getSql(ESQueryCond esQueryCond) {

        StringBuilder sb = new StringBuilder();
        sb.append("select AgentId, AgentStartTime, CollectTime, CpuId,")
                .append("CpuVendor, CpuFamily, CpuModel, CpuModelName,CpuMHZ, CpuCache,")
                .append("CpuUser,CpuNice,CpuSystem ,CpuIdel,CpuIowait,CpuIRQ,CpuSoftIrq").append(BLANK)
                .append("from ").append(CPU_FULL_TABLE).append(BLANK)
                .append(getWhereCause(esQueryCond));


        return sb.toString();
    }

    @Override
    public List<ESMetrics> parse(ResultSet rs) throws SQLException {
        List<ESMetrics> esMetricses = new ArrayList<>();

        while (rs.next()) {
            Map<String, Object> rowMap = new HashMap<>();
            rowMap.put(ESConst.AGENT_ID, rs.getString("AgentId"));
            rowMap.put(ESConst.AGENT_STARTTIME, rs.getString("AgentStartTime"));
            rowMap.put(ESConst.COLLECT_TIME, PhoenixDateUtil.longTime(rs.getString("CollectTime")));
            rowMap.put(ESConst.CPU_ID, rs.getString("CpuId"));
            rowMap.put(ESConst.CPU_VENDOR, rs.getString("CpuVendor"));
            rowMap.put(ESConst.CPU_FAMILY, rs.getString("CpuFamily"));
            rowMap.put(ESConst.CPU_MODEL, rs.getString("CpuModel"));
            rowMap.put(ESConst.CPU_MODEL_NAME, rs.getString("CpuModelName"));
            rowMap.put(ESConst.CPU_MHZ, rs.getString("CpuMHZ"));
            rowMap.put(ESConst.CPU_CACHE, rs.getString("CpuCache"));

            rowMap.put(ESConst.CPU_USER, rs.getLong("CpuUser"));
            rowMap.put(ESConst.CPU_NICE, rs.getLong("CpuNice"));
            rowMap.put(ESConst.CPU_SYSTEM, rs.getLong("CpuSystem"));
            rowMap.put(ESConst.CPU_IDEL, rs.getLong("CpuIdel"));
            rowMap.put(ESConst.CPU_IOWAIT, rs.getLong("CpuIowait"));
            rowMap.put(ESConst.CPU_IRQ, rs.getLong("CpuIRQ"));
            rowMap.put(ESConst.CPU_SOFTIRQ, rs.getLong("CpuSoftIrq"));

            esMetricses.add(new ESMetrics(rowMap));
        }


        return esMetricses;
    }
}
