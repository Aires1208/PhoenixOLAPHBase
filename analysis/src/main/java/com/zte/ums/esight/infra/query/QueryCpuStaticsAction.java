package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.infra.PhoenixDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryCpuStaticsAction extends QueryAction {
    private static Logger logger = LoggerFactory.getLogger(QueryCpuStaticsAction.class);
    private String aggType;

    public QueryCpuStaticsAction(String aggType) {
        super(ESConst.CPU_TYPE);
        this.aggType = aggType;
    }

    public void setAggType(String aggType) {
        this.aggType = aggType;
    }

    @Override
    public String getSql(ESQueryCond esQueryCond) {
        String collectTime = getAggCollectTime(esQueryCond, aggType
                , CPU_FULL_TABLE);

        StringBuilder sb = new StringBuilder();
        sb.append("select AgentId, AgentStartTime, CollectTime, CpuId,")
                .append("CpuVendor, CpuFamily, CpuModel, CpuModelName,CpuMHZ, CpuCache,")
                .append("CpuUser,CpuNice,CpuSystem ,CpuIdel,CpuIowait,CpuIRQ,CpuSoftIrq").append(BLANK)
                .append("from ").append(CPU_FULL_TABLE).append(BLANK)
                .append(getEqualWhereCause(esQueryCond,collectTime));


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

        esMetricses.sort((o1, o2) -> sortByCpuId(o1, o2));

        return esMetricses;
    }

    protected int sortByCpuId(ESMetrics first, ESMetrics second) {
        System.out.println(first);
        String firstCpuId = first.getValue(ESConst.CPU_ID).toString();
        String secondCpuId = second.getValue(ESConst.CPU_ID).toString();

        String prefix = "cpu";

        return getId(firstCpuId, prefix) - getId(secondCpuId, prefix);
    }

    private int getId(String cpuId, String prefix) {
        int id = 0;
        try {
            id = Integer.parseInt(cpuId.replace(prefix, "").trim());
        } catch (NumberFormatException e) {
            logger.error("getId error:", e);
        }

        return id;
    }
}
