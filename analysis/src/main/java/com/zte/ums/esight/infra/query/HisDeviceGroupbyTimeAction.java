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

public class HisDeviceGroupbyTimeAction extends QueryAction {

    public HisDeviceGroupbyTimeAction() {
        super(ESConst.DEVICE_TYPE);
    }

    @Override
    public String getSql(ESQueryCond esQueryCond) {
        StringBuilder sb = new StringBuilder();

        String truncTime = "TRUNC(TO_DATE(CollectTime,'yyyyMMddHHmmss'),'" + esQueryCond.getGP() + "') ";
        String aggTimeGp = truncTime + " as CollectTime1";

        sb.append("select AgentId, AgentStartTime,").append(aggTimeGp).append(NEW_LINE)
                .append(",avg(Tps) as Tps, avg(ReadPerSecond) as ReadPerSecond, avg(WritePerSecond) as WritePerSecond").append(NEW_LINE)
                .append("from ").append(DEVICE_FULL_TABLE).append(NEW_LINE)
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
            rowMap.put(ESConst.DEVICE_READ_PERSECOND, rs.getDouble("ReadPerSecond"));
            rowMap.put(ESConst.DEVICE_WRITE_PERSECOND, rs.getDouble("WritePerSecond"));
            rowMap.put(ESConst.DEVICE_TPS, rs.getDouble("Tps"));

            esMetricses.add(new ESMetrics(rowMap));
        }

        return esMetricses;
    }
}
