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

public class TimedProcessAction extends QueryAction {

    private String aggType;

    public TimedProcessAction(String aggType) {
        super(ESConst.PROCESS_TYPE);
        this.aggType = aggType;
    }

    @Override
    public String getSql(ESQueryCond esQueryCond) {
        String collectTime = getAggCollectTime(esQueryCond, aggType
                , PROCESS_FULL_TABLE);
        StringBuilder sb = new StringBuilder();
        sb.append("select AgentId, AgentStartTime, CollectTime, Pid, ").append(NEW_LINE)
                .append("Process,Command, CpuUsage, CpuTime,Virt,Res").append(NEW_LINE)
                .append("from ").append(PROCESS_FULL_TABLE).append(NEW_LINE)
                .append(getEqualWhereCause(esQueryCond,collectTime));

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
            rowMap.put(ESConst.COLLECT_TIME, PhoenixDateUtil.longTime(rs.getString("CollectTime")));
            rowMap.put(ESConst.PROCESS_PID, rs.getString("Pid"));
            rowMap.put(ESConst.PROCESS_NAME, rs.getString("process"));
            rowMap.put(ESConst.PROCESS_COMMAND, rs.getString("Command"));

            rowMap.put(ESConst.PROCESS_CPU_USAGE, rs.getDouble("CpuUsage"));
            rowMap.put(ESConst.PROCESS_CPU_TIME, rs.getLong("CpuTime"));
            rowMap.put(ESConst.PROCESS_VIRT, rs.getLong("Virt"));
            rowMap.put(ESConst.PROCESS_RES, rs.getLong("Res"));

            esMetricses.add(new ESMetrics(rowMap));
        }


        return esMetricses;
    }
}
