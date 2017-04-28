package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.domain.model.ESQueryResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class QueryCpuRatioByTimeAction extends QueryAction {

    private QueryHisCpuStaticsGroupbyAgentIdAction queryHisCpuStaticsGroupbyAgentIdAction;

    public QueryCpuRatioByTimeAction(QueryHisCpuStaticsGroupbyAgentIdAction queryHisCpuStaticsGroupbyAgentIdAction) {
        super(ESConst.CPU_TYPE);
        this.queryHisCpuStaticsGroupbyAgentIdAction = queryHisCpuStaticsGroupbyAgentIdAction;
    }

    @Override
    public ESQueryResult query(ESQueryCond esQueryCond) {
        System.out.println("ESQueryCond = " + esQueryCond);
        queryHisCpuStaticsGroupbyAgentIdAction.setAggType(MAX);
        List<ESMetrics> esMetricsesLast = queryHisCpuStaticsGroupbyAgentIdAction
                .query(esQueryCond).getEsMetricses();

        queryHisCpuStaticsGroupbyAgentIdAction.setAggType(MIN);
        List<ESMetrics> esMetricsesFirst = queryHisCpuStaticsGroupbyAgentIdAction
                .query(esQueryCond).getEsMetricses();

        Map<Long, ESMetrics> esMetricsLastMap = new HashMap<>();
        for (ESMetrics esMetrics : esMetricsesLast) {
            esMetricsLastMap.put((Long) esMetrics.getValue(ESConst.COLLECT_TIME), esMetrics);
        }

        Map<Long, ESMetrics> esMetricsFirstMap = new HashMap<>();

        for (ESMetrics esMetrics : esMetricsesFirst) {
            esMetricsFirstMap.put((Long) esMetrics.getValue(ESConst.COLLECT_TIME), esMetrics);
        }

        List<ESMetrics> esMetricses = new ArrayList<>();
        Set<Long> remainSet = esMetricsLastMap.keySet();
        remainSet.retainAll(esMetricsFirstMap.keySet());
        for (Long key : remainSet) {
            ESMetrics last = esMetricsLastMap.get(key);
            ESMetrics first = esMetricsFirstMap.get(key);

            Map<String, Object> metricMap = new HashMap<>();
            metricMap.put(ESConst.COLLECT_TIME, last.getValue(ESConst.COLLECT_TIME));
            metricMap.put(ESConst.CPU_USER, XLong(last.getValue(ESConst.CPU_USER)) - XLong(first.getValue(ESConst.CPU_USER)));
            metricMap.put(ESConst.CPU_NICE, XLong(last.getValue(ESConst.CPU_NICE)) - XLong(first.getValue(ESConst.CPU_NICE)));
            metricMap.put(ESConst.CPU_SYSTEM, XLong(last.getValue(ESConst.CPU_SYSTEM)) - XLong(first.getValue(ESConst.CPU_SYSTEM)));
            metricMap.put(ESConst.CPU_IDEL, XLong(last.getValue(ESConst.CPU_IDEL)) - XLong(first.getValue(ESConst.CPU_IDEL)));
            metricMap.put(ESConst.CPU_IOWAIT, XLong(last.getValue(ESConst.CPU_IOWAIT)) - XLong(first.getValue(ESConst.CPU_IOWAIT)));
            metricMap.put(ESConst.CPU_IRQ, XLong(last.getValue(ESConst.CPU_IRQ)) - XLong(first.getValue(ESConst.CPU_IRQ)));
            metricMap.put(ESConst.CPU_SOFTIRQ, XLong(last.getValue(ESConst.CPU_SOFTIRQ)) - XLong(first.getValue(ESConst.CPU_SOFTIRQ)));

            ESMetrics esMetrics = new ESMetrics(metricMap);
            esMetricses.add(esMetrics);

        }

        sortByTime(esMetricses);

        return new ESQueryResult(ESConst.CPU_TYPE, esMetricses);
    }

    @Override
    public String getSql(ESQueryCond esQueryCond) {
        return "";

    }

    @Override
    public List<ESMetrics> parse(ResultSet rs) throws SQLException {
        return new ArrayList<>();
    }
}
