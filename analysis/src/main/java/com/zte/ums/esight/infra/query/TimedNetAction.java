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

public class TimedNetAction extends QueryAction {

    private String aggType;

    public TimedNetAction(String aggType) {
        super(ESConst.NET_TYPE);
        this.aggType = aggType;
    }

    @Override
    public String getSql(ESQueryCond esQueryCond) {
        String collectTime = getAggCollectTime(esQueryCond, aggType
                , NET_FULL_TABLE);
        StringBuilder sb = new StringBuilder();
        sb.append("select AgentId, AgentStartTime, CollectTime, Name, V4Address, MacAddress, Mtu,").append(NEW_LINE)
                .append("ReceiveBytes, ReceiveErrors,TransmitBytes,TransmitErrors, Colls").append(NEW_LINE)
                .append("from ").append(NET_FULL_TABLE).append(NEW_LINE)
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
            rowMap.put(ESConst.NET_NAME, rs.getString("Name"));
            rowMap.put(ESConst.NET_V4_ADDRESS, rs.getString("V4Address"));
            rowMap.put(ESConst.NET_MAC_ADDRESS, rs.getString("MacAddress"));
            rowMap.put(ESConst.NET_MTU, rs.getString("Mtu"));
            rowMap.put(ESConst.NET_RECEIVE_BYTES, rs.getLong("ReceiveBytes"));
            rowMap.put(ESConst.NET_RECEIVE_ERRORS, rs.getLong("ReceiveErrors"));
            rowMap.put(ESConst.NET_TRANSMIT_BYTES, rs.getLong("TransmitBytes"));
            rowMap.put(ESConst.NET_TRANSMIT_ERRORS, rs.getLong("TransmitErrors"));
            rowMap.put(ESConst.NET_COLLS, rs.getLong("Colls"));

            esMetricses.add(new ESMetrics(rowMap));
        }


        return esMetricses;
    }
}
