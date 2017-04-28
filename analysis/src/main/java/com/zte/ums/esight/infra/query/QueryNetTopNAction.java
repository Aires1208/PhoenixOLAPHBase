package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.domain.model.ESQueryResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class QueryNetTopNAction extends QueryAction {

    public QueryNetTopNAction() {
        super(ESConst.NET_TYPE);
    }

    @Override
    public ESQueryResult query(ESQueryCond esQueryCond) {
        List<ESMetrics> esMetricsesLast = new TimedNetAction(MAX)
                .query(esQueryCond).getEsMetricses();
        Map<String, ESMetrics> esMetricsLastMap = new HashMap<>();
        for (ESMetrics esMetrics : esMetricsesLast) {
            esMetricsLastMap.put((String) esMetrics.getValue(ESConst.NET_NAME), esMetrics);
        }


        Map<String, ESMetrics> esMetricsFirstMap = new HashMap<>();
        List<ESMetrics> esMetricsesFirst = new TimedNetAction(MIN)
                .query(esQueryCond).getEsMetricses();

        for (ESMetrics esMetrics : esMetricsesFirst) {
            esMetricsFirstMap.put((String) esMetrics.getValue(ESConst.NET_NAME), esMetrics);
        }


        List<ESMetrics> esMetricses = new ArrayList<>();
        Set<String> remainSet = esMetricsLastMap.keySet();
        remainSet.retainAll(esMetricsFirstMap.keySet());
        for (String key : remainSet) {
            ESMetrics last = esMetricsLastMap.get(key);
            ESMetrics first = esMetricsFirstMap.get(key);
            esMetricses.add(calMetircs(first, last));
        }

        return new ESQueryResult(ESConst.NET_TYPE, esMetricses);

    }

    @Override
    public String getSql(ESQueryCond esQueryCond) {

        return "";
    }

    @Override
    public List<ESMetrics> parse(ResultSet rs) throws SQLException {

        return new ArrayList<>();
    }

    private ESMetrics calMetircs(ESMetrics beforeEsMetrics, ESMetrics curEsMetrics) {

        long colls;
        long transmitErrs;
        long transmitBytes;
        long receiveErrs;
        long receiveBytes;

        long BeforeCollectTime = XLong(beforeEsMetrics.getValue(ESConst.COLLECT_TIME));
        long curCollectTime = XLong(curEsMetrics.getValue(ESConst.COLLECT_TIME));

        long beforeColls = XLong(beforeEsMetrics.getValue(ESConst.NET_COLLS));
        long curColls = XLong(curEsMetrics.getValue(ESConst.NET_COLLS));
        colls = curColls - beforeColls;

        long beforeReceiveErrs = XLong(beforeEsMetrics.getValue(ESConst.NET_RECEIVE_ERRORS));
        long curReceiveErrs = XLong(curEsMetrics.getValue(ESConst.NET_RECEIVE_ERRORS));
        receiveErrs = curReceiveErrs - beforeReceiveErrs;

        long beforeTansmitErrs = XLong(beforeEsMetrics.getValue(ESConst.NET_TRANSMIT_ERRORS));
        long curTansmitErrs = XLong(curEsMetrics.getValue(ESConst.NET_TRANSMIT_ERRORS));
        transmitErrs = curTansmitErrs - beforeTansmitErrs;

        long beforeReceiveBytes = XLong(beforeEsMetrics.getValue(ESConst.NET_RECEIVE_BYTES));
        long curReceiveBytes = XLong(curEsMetrics.getValue(ESConst.NET_RECEIVE_BYTES));

        if (curCollectTime - BeforeCollectTime > 0) {
            receiveBytes = (curReceiveBytes - beforeReceiveBytes) / ((curCollectTime - BeforeCollectTime) / 1000);
        } else {
            receiveBytes = 0;
        }


        long beforeTransmitBytes = XLong(beforeEsMetrics.getValue(ESConst.NET_TRANSMIT_BYTES));
        long curTransmitBytes = XLong(curEsMetrics.getValue(ESConst.NET_TRANSMIT_BYTES));
        if (curCollectTime - BeforeCollectTime > 0) {
            transmitBytes = (curTransmitBytes - beforeTransmitBytes) / ((curCollectTime - BeforeCollectTime) / 1000);
        } else {
            transmitBytes = 0;
        }


        Map<String, Object> metricMap = new HashMap<>();
        metricMap.put(ESConst.COLLECT_TIME, curCollectTime);
        metricMap.put(ESConst.NET_COLLS, colls);
        metricMap.put(ESConst.NET_RECEIVE_BYTES, receiveBytes);
        metricMap.put(ESConst.NET_RECEIVE_ERRORS, receiveErrs);
        metricMap.put(ESConst.NET_TRANSMIT_BYTES, transmitBytes);
        metricMap.put(ESConst.NET_TRANSMIT_ERRORS, transmitErrs);

        if (curEsMetrics.getValue(ESConst.NET_NAME) != null) {
            metricMap.put(ESConst.NET_NAME, curEsMetrics.getValue(ESConst.NET_NAME));

        }

        return new ESMetrics(metricMap);
    }
}
