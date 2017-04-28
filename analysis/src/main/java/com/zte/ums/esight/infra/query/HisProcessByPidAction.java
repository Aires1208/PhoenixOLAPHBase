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

public class HisProcessByPidAction extends QueryAction {

    public HisProcessByPidAction() {
        super(ESConst.PROCESS_TYPE);
    }

    @Override
    public String getSql(ESQueryCond esQueryCond) {
        StringBuilder sb = new StringBuilder();
        String truncTime = "TRUNC(TO_DATE(CollectTime,'yyyyMMddHHmmss'),'" + esQueryCond.getGP() + "') ";
        String aggTimeGp = truncTime + " as CollectTime1";

        sb.append("select AgentId, AgentStartTime, Pid, Process,Command,").append(aggTimeGp).append(NEW_LINE)
                .append(",avg(CpuUsage) as CpuUsage, max(CpuTime) as CpuTime,avg(Virt) as Virt,avg(Res) as Res").append(NEW_LINE)
                .append("from ").append(PROCESS_FULL_TABLE).append(NEW_LINE)
                .append(getWhereCause(esQueryCond))
                .append("and Pid='").append(esQueryCond.getParam(ESConst.PROCESS_PID)).append("'").append(NEW_LINE)
                .append("and Process='").append(esQueryCond.getParam(ESConst.PROCESS_NAME)).append("'").append(NEW_LINE)
                .append("and Command='").append(esQueryCond.getParam(ESConst.PROCESS_COMMAND)).append("'").append(NEW_LINE)
                .append("group by AgentId, AgentStartTime, Pid, Process,Command,")
                .append(truncTime).append(NEW_LINE);

        String sql = sb.toString();
        return sql;
    }

    @Override
    public List<ESMetrics> parse(ResultSet rs) throws SQLException {

        List<ESMetrics> esMetricses = new ArrayList<>();

        while (rs.next()) {
            Map<String, Object> rowMap = new HashMap<>();
            rowMap.put(ESConst.AGENT_ID, rs.getString("AgentId"));
            rowMap.put(ESConst.AGENT_STARTTIME, rs.getString("AgentStartTime"));
            rowMap.put(ESConst.PROCESS_PID, rs.getString("Pid"));
            rowMap.put(ESConst.PROCESS_NAME, rs.getString("process"));
            rowMap.put(ESConst.PROCESS_COMMAND, rs.getString("Command"));
            rowMap.put(ESConst.COLLECT_TIME, PhoenixDateUtil.longTime1(rs.getString("CollectTime1")));
            rowMap.put(ESConst.PROCESS_CPU_USAGE, rs.getDouble("CpuUsage"));
            rowMap.put(ESConst.PROCESS_CPU_TIME, rs.getLong("CpuTime"));
            rowMap.put(ESConst.PROCESS_VIRT, rs.getDouble("Virt"));
            rowMap.put(ESConst.PROCESS_RES, rs.getDouble("Res"));

            esMetricses.add(new ESMetrics(rowMap));
        }

        return esMetricses;
    }
}
