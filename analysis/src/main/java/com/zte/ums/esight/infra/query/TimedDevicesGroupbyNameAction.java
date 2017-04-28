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

/**
 * Created by root on 17-3-17.
 */
public class TimedDevicesGroupbyNameAction extends QueryAction {

    private final String aggType;

    public TimedDevicesGroupbyNameAction(String aggType) {
        super(ESConst.DEVICE_TYPE);
        this.aggType = aggType;
    }

    @Override
    public String getSql(ESQueryCond esQueryCond) {
        String collectTime = getAggCollectTime(esQueryCond, aggType
                , DEVICE_FULL_TABLE);
        StringBuilder sb = new StringBuilder();
        sb.append("select AgentId, AgentStartTime, CollectTime, DeviceName,")
                .append("Read,Write,Tps").append(BLANK)
                .append("from ").append(DEVICE_FULL_TABLE).append(BLANK)
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
            rowMap.put(ESConst.DEVICE_NAME, rs.getString("DeviceName"));
            rowMap.put(ESConst.DEVICE_READ, rs.getLong("Read"));
            rowMap.put(ESConst.DEVICE_WRITE, rs.getLong("Write"));
            rowMap.put(ESConst.DEVICE_TPS, rs.getLong("Tps"));

            esMetricses.add(new ESMetrics(rowMap));
        }


        return esMetricses;
    }
}
