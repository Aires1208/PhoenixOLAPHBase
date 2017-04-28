package com.zte.ums.esight.infra.upsert;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.PhoenixAgentStat;
import com.zte.ums.esight.infra.PhoenixDateUtil;

import java.util.ArrayList;
import java.util.List;

public class UpsertNetTableAction extends UpsertAction {


    @Override
    public List<String> buildSql(PhoenixAgentStat phoenixAgentStat) {
        List<String> jsonDatas = new ArrayList<>();

        List<ESMetrics> esMetricses = phoenixAgentStat.getResult(ESConst.NET_TYPE).getEsMetricses();

        for (ESMetrics esMetrics : esMetricses) {

            StringBuilder sb = new StringBuilder();
            sb.append("upsert into ").append(NET_FULL_TABLE).append(" (").append(NEW_LINE)
                    .append(TWO_BLANK).append("AgentId, AgentStartTime,Mac,CollectTime, Name, V4Address,").append(NEW_LINE)
                    .append(TWO_BLANK).append("MacAddress,Mtu,ReceiveBytes, ReceiveErrors,TransmitBytes,").append(NEW_LINE)
                    .append(TWO_BLANK).append("TransmitErrors, Colls").append(NEW_LINE)
                    .append(TWO_BLANK).append(")").append(NEW_LINE)
                    .append("values (").append(NEW_LINE)
                    .append(TWO_BLANK).append("'").append(phoenixAgentStat.getAgentId()).append("'").append(",")
                    .append("'").append(phoenixAgentStat.getStartTime()).append("'").append(",")
                    .append("'").append(phoenixAgentStat.getMacAddress()).append("'").append(",")
                    .append("'").append(PhoenixDateUtil.dateStr(phoenixAgentStat.getCollectTime())).append("'").append(",")

                    .append("'").append(esMetrics.getValue(ESConst.NET_NAME)).append("'").append(",")
                    .append("'").append(esMetrics.getValue(ESConst.NET_V4_ADDRESS)).append("'").append(",")
                    .append("'").append(esMetrics.getValue(ESConst.NET_MAC_ADDRESS)).append("'").append(",")

                    .append(esMetrics.getValue(ESConst.NET_MTU)).append(",")
                    .append(esMetrics.getValue(ESConst.NET_RECEIVE_BYTES)).append(",")
                    .append(esMetrics.getValue(ESConst.NET_RECEIVE_ERRORS)).append(",")
                    .append(esMetrics.getValue(ESConst.NET_TRANSMIT_BYTES)).append(",")
                    .append(esMetrics.getValue(ESConst.NET_TRANSMIT_ERRORS)).append(",")
                    .append(esMetrics.getValue(ESConst.NET_COLLS)).append(NEW_LINE)

                    .append(")").append(NEW_LINE);


            jsonDatas.add(sb.toString());


        }

        return jsonDatas;
    }
}
