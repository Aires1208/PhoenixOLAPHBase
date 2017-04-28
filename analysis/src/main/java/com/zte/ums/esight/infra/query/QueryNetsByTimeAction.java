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

public class QueryNetsByTimeAction extends QueryAction {

    public QueryNetsByTimeAction() {
        super(ESConst.NET_TYPE);
    }

    @Override
    public String getSql(ESQueryCond esQueryCond) {
        StringBuilder sb = new StringBuilder();

        String truncTime = "TRUNC(TO_DATE(CollectTime,'yyyyMMddHHmmss'),'" + esQueryCond.getGP() + "') ";
        String aggTimeGp = truncTime + " as CollectTime1";

        sb.append("select AgentId, AgentStartTime, CollectTime1 as CollectTime,").append(NEW_LINE)
                .append(TWO_BLANK).append("CollectTime_MAX,CollectTime_MIN,")
                .append(TWO_BLANK).append("sum(ReceiveBytes_max)-sum(ReceiveBytes_min) as ReceiveBytes,").append(NEW_LINE)
                .append(TWO_BLANK).append("sum(ReceiveErrors_max)-sum(ReceiveErrors_min) as ReceiveErrors,").append(NEW_LINE)
                .append(TWO_BLANK).append("sum(TransmitBytes_max)-sum(TransmitBytes_min) as TransmitBytes,").append(NEW_LINE)
                .append(TWO_BLANK).append("sum(TransmitErrors_max)-sum(TransmitErrors_min) as TransmitErrors,").append(NEW_LINE)
                .append(TWO_BLANK).append("sum(Colls_max)-sum(Colls_min) as Colls").append(NEW_LINE)

                .append(TWO_BLANK).append("from").append(NEW_LINE)
                .append(TWO_BLANK).append("(").append(NEW_LINE)
                .append(FOUR_BLANK).append("select AgentId, AgentStartTime,").append(aggTimeGp).append(NEW_LINE)
                .append(SIX_BLANK).append(",Name,MAX(CollectTime) as CollectTime_MAX,MIN(CollectTime) as CollectTime_MIN,").append(NEW_LINE)

                .append(SIX_BLANK).append("MAX(ReceiveBytes) as ReceiveBytes_max").append(",")
                .append("MAX(ReceiveErrors) as ReceiveErrors_max").append(",")
                .append("MAX(TransmitBytes) as TransmitBytes_max").append(",").append(NEW_LINE)
                .append(SIX_BLANK).append("MAX(TransmitErrors) as TransmitErrors_max").append(",")
                .append("MAX(Colls) as Colls_max,").append(NEW_LINE)

                .append(SIX_BLANK).append("MIN(ReceiveBytes) as ReceiveBytes_min").append(",")
                .append("MIN(ReceiveErrors) as ReceiveErrors_min").append(",")
                .append("MIN(TransmitBytes) as TransmitBytes_min").append(",").append(NEW_LINE)
                .append(SIX_BLANK).append("MIN(TransmitErrors) as TransmitErrors_min").append(",")
                .append("MIN(Colls) as Colls_min").append(NEW_LINE)


                .append(FOUR_BLANK).append("from ").append(NET_FULL_TABLE).append(NEW_LINE)
                .append(FOUR_BLANK).append(getWhereCause(esQueryCond))
                .append(FOUR_BLANK).append("group by AgentId, AgentStartTime,").append(truncTime).append(",Name").append(NEW_LINE)
                .append(TWO_BLANK).append(")").append(NEW_LINE)
                .append("group by AgentId, AgentStartTime, CollectTime,CollectTime_MAX,CollectTime_MIN");
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
            rowMap.put(ESConst.COLLECT_TIME, PhoenixDateUtil.longTime1(rs.getString("CollectTime")));

            long duration = (PhoenixDateUtil.longTime(rs.getString("CollectTime_MAX")) -
                    PhoenixDateUtil.longTime(rs.getString("CollectTime_MIN")))/1000;

            long receiveBytes = duration > 0 ? rs.getLong("ReceiveBytes")/duration : 0;
            rowMap.put(ESConst.NET_RECEIVE_BYTES, receiveBytes);

            long receiveErrors = duration > 0 ? rs.getLong("ReceiveErrors")/duration : 0;
            rowMap.put(ESConst.NET_RECEIVE_ERRORS, receiveErrors);

            long transmitBytes = duration > 0 ? rs.getLong("TransmitBytes")/duration : 0;
            rowMap.put(ESConst.NET_TRANSMIT_BYTES, transmitBytes);

            long transmitErrors = duration > 0 ? rs.getLong("TransmitErrors")/duration : 0;
            rowMap.put(ESConst.NET_TRANSMIT_ERRORS, transmitErrors);

            long colls = duration > 0 ? rs.getLong("Colls")/duration : 0;
            rowMap.put(ESConst.NET_COLLS, colls);

            esMetricses.add(new ESMetrics(rowMap));
        }

        return esMetricses;
    }
}
