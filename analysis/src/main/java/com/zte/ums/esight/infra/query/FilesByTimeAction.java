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

public class FilesByTimeAction extends QueryAction {

    public FilesByTimeAction() {
        super(ESConst.FILE_TYPE);
    }

    @Override
    public String getSql(ESQueryCond esQueryCond) {
        StringBuilder sb = new StringBuilder();

        String truncTime = "TRUNC(TO_DATE(CollectTime,'yyyyMMddHHmmss'),'" + esQueryCond.getGP() + "') ";
        String aggTimeGp = truncTime + " as CollectTime1";

        sb.append("select AgentId, AgentStartTime,").append(aggTimeGp).append(NEW_LINE)
                .append(",avg(Total) as Total, avg(Used) as Used, avg(Free) as Free").append(NEW_LINE)
                .append("from ").append(FILESYSTEM_FULL_TABLE).append(NEW_LINE)
                .append(FOUR_BLANK).append(getWhereCause(esQueryCond))
                .append(FOUR_BLANK).append("group by AgentId, AgentStartTime,").append(truncTime).append(NEW_LINE);

        return sb.toString();
    }

    @Override
    public List<ESMetrics> parse(ResultSet rs) throws SQLException {
        List<ESMetrics> esMetricses = new ArrayList<>();

        while (rs.next()) {
            Map<String, Object> rowMap = new HashMap<>();
            rowMap.put(ESConst.AGENT_ID, rs.getString("AgentId"));
            rowMap.put(ESConst.AGENT_STARTTIME, rs.getString("AgentStartTime"));
            rowMap.put(ESConst.COLLECT_TIME, PhoenixDateUtil.longTime1(rs.getString("CollectTime1")));
            rowMap.put(ESConst.FILE_TOTAL, rs.getDouble("Total"));
            rowMap.put(ESConst.FILE_USED, rs.getDouble("Used"));
            rowMap.put(ESConst.FILE_FREE, rs.getDouble("Free"));

            esMetricses.add(new ESMetrics(rowMap));
        }

        return esMetricses;
    }
}
