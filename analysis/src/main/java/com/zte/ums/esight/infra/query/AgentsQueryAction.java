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

import static javax.management.timer.Timer.ONE_DAY;

public class AgentsQueryAction extends QueryAction {
    public AgentsQueryAction() {
        super(ESConst.AGENTS_TYPE);
    }

    @Override
    public String getSql(ESQueryCond esQueryCond) {
        long timestamp = System.currentTimeMillis();
        String oneDay = PhoenixDateUtil.dateStrMs(timestamp - ONE_DAY);

        StringBuilder sb = new StringBuilder();
        sb.append("select AgentId, AgentStartTime, MacAddress, CollectTime ").append(NEW_LINE)
                .append("from ").append(AGENTINDEX_FULL_TABLE).append(NEW_LINE)
                .append("where collectTime>='").append(oneDay).append("'").append(NEW_LINE);

        return sb.toString();
    }

    @Override
    public List<ESMetrics> parse(ResultSet rs) throws SQLException {
        List<ESMetrics> esMetricses = new ArrayList<>();

        while (rs.next()) {
            Map<String, Object> rowMap = new HashMap<>();
            rowMap.put(ESConst.AGENT_ID, rs.getString("AgentId"));
            rowMap.put(ESConst.AGENT_STARTTIME, rs.getString("AgentStartTime"));
            rowMap.put(ESConst.MAC_ADDRESS, rs.getString("MacAddress") != null ? rs.getString("MacAddress") : "mac");
            rowMap.put(ESConst.COLLECT_TIME, rs.getString("CollectTime"));

            esMetricses.add(new ESMetrics(rowMap));
        }

        return esMetricses;
    }
}
