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

public class QueryHisCpuStaticsGroupbyAgentIdAction extends QueryAction {

    private String aggType;

    public QueryHisCpuStaticsGroupbyAgentIdAction(String aggType) {
        super(ESConst.CPU_TYPE);
        this.aggType = aggType;
    }

    public void setAggType(String aggType) {
        this.aggType = aggType;
    }

    @Override
    public String getSql(ESQueryCond esQueryCond) {

        String truncTime = "TRUNC(TO_DATE(CollectTime,'yyyyMMddHHmmss'),'" + esQueryCond.getGP() + "') ";
        String aggTimeGp = truncTime + " as CollectTime1";

        StringBuilder sb = new StringBuilder();
        sb.append("select AgentId, AgentStartTime, CollectTime1 as CollectTime,sum(CpuUser) as CpuUser,").append(NEW_LINE)
                .append(TWO_BLANK).append("sum(CpuNice) as CpuNice, sum(CpuSystem) as CpuSystem,").append(NEW_LINE)
                .append(TWO_BLANK).append("sum(CpuIdel) as CpuIdel ,sum(CpuIowait) as CpuIowait,").append(NEW_LINE)
                .append(TWO_BLANK).append("sum(CpuIRQ) as CpuIRQ, sum(CpuSoftIrq) as CpuSoftIrq").append(NEW_LINE)
                .append(TWO_BLANK).append("from").append(NEW_LINE)
                .append(TWO_BLANK).append("(").append(NEW_LINE)
                .append(FOUR_BLANK).append("select AgentId, AgentStartTime,").append(aggTimeGp).append(",CpuId,").append(NEW_LINE)

                .append(SIX_BLANK).append(agg("CpuUser", aggType)).append(",")
                .append(agg("CpuNice", aggType)).append(",")
                .append(agg("CpuSystem", aggType)).append(",").append(NEW_LINE)

                .append(SIX_BLANK).append(agg("CpuIdel", aggType)).append(",")
                .append(agg("CpuIowait", aggType)).append(",")
                .append(agg("CpuIRQ", aggType)).append(",")
                .append(agg("CpuSoftIrq", aggType)).append(NEW_LINE)


                .append(FOUR_BLANK).append("from ").append(CPU_FULL_TABLE).append(NEW_LINE)
                .append(FOUR_BLANK).append(getWhereCause(esQueryCond))
                .append(FOUR_BLANK).append("group by AgentId, AgentStartTime,").append(truncTime).append(",CpuId").append(NEW_LINE)
                .append(TWO_BLANK).append(")").append(NEW_LINE)
                .append("group by AgentId, AgentStartTime, CollectTime");

        return sb.toString();
    }

    @Override
    public List<ESMetrics> parse(ResultSet rs) throws SQLException {
        List<ESMetrics> esMetricses = new ArrayList<>();

        while (rs.next()) {
            Map<String, Object> rowMap = new HashMap<>();
            rowMap.put(ESConst.AGENT_ID, rs.getString("AgentId"));
            rowMap.put(ESConst.AGENT_STARTTIME, rs.getString("AgentStartTime"));
            rowMap.put(ESConst.COLLECT_TIME, PhoenixDateUtil.longTime1(rs.getString("CollectTime")));

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
