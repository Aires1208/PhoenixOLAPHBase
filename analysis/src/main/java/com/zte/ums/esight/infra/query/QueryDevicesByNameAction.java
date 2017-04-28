package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class QueryDevicesByNameAction extends QueryAction {


    public QueryDevicesByNameAction() {
        super(ESConst.DEVICE_TYPE);
    }

    @Override
    public String getSql(ESQueryCond esQueryCond) {
        StringBuilder sb = new StringBuilder();


        sb.append("select AgentId, AgentStartTime,DeviceName").append(NEW_LINE)
                .append(",avg(Tps) as Tps, max(Read) as Read, max(Write) as Write").append(NEW_LINE)
                .append("from ").append(DEVICE_FULL_TABLE).append(NEW_LINE)
                .append(FOUR_BLANK).append(getWhereCause(esQueryCond))
                .append(FOUR_BLANK).append("group by AgentId, AgentStartTime,DeviceName").append(NEW_LINE);

        return sb.toString();
    }


    @Override
    public List<ESMetrics> parse(ResultSet rs) throws SQLException {
        List<ESMetrics> esMetricses = new ArrayList<>();

        while (rs.next()) {
            Map<String, Object> rowMap = new HashMap<>();
            rowMap.put(ESConst.DEVICE_NAME, rs.getString("DeviceName"));
            rowMap.put(ESConst.DEVICE_READ, rs.getLong("Read"));
            rowMap.put(ESConst.DEVICE_WRITE, rs.getLong("Write"));
            rowMap.put(ESConst.DEVICE_TPS, rs.getDouble("Tps"));

            esMetricses.add(new ESMetrics(rowMap));
        }

        return esMetricses;
    }

}
