package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.domain.model.ESQueryResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class QueryCpuRatioTopNAction extends QueryAction {
    private QueryCpuStaticsAction queryCpuStaticsAction;

    public QueryCpuRatioTopNAction(QueryCpuStaticsAction queryCpuStaticsAction) {
        super(ESConst.CPU_TYPE);
        this.queryCpuStaticsAction = queryCpuStaticsAction;
    }

    @Override
    public ESQueryResult query(ESQueryCond esQueryCond) {
        queryCpuStaticsAction.setAggType(MAX);
        List<ESMetrics> esMetricsesLast = queryCpuStaticsAction.query(esQueryCond).getEsMetricses();
        Map<String, ESMetrics> esMetricsLastMap = new HashMap<>();
        for (ESMetrics esMetrics : esMetricsesLast) {
            esMetricsLastMap.put((String) esMetrics.getValue(ESConst.CPU_ID), esMetrics);
        }

        Map<String, ESMetrics> esMetricsFirstMap = new HashMap<>();
        queryCpuStaticsAction.setAggType(MIN);
        List<ESMetrics> esMetricsesFirst = queryCpuStaticsAction.query(esQueryCond).getEsMetricses();
        for (ESMetrics esMetrics : esMetricsesFirst) {
            esMetricsFirstMap.put((String) esMetrics.getValue(ESConst.CPU_ID), esMetrics);
        }

        List<ESMetrics> esMetricses = new ArrayList<>();
        Set<String> remainSet = esMetricsLastMap.keySet();
        remainSet.retainAll(esMetricsFirstMap.keySet());
        for (String key : remainSet) {
            ESMetrics last = esMetricsLastMap.get(key);
            ESMetrics first = esMetricsFirstMap.get(key);

            Map<String, Object> metricMap = new HashMap<>();
            metricMap.put(ESConst.CPU_ID, last.getValue(ESConst.CPU_ID));
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

        esMetricses.sort((o1, o2) -> compare(o1, o2));

        int lastIndex = esMetricses.size() < TOPN ? esMetricses.size() : TOPN;
        List<ESMetrics> esMetricsesTopN = esMetricses.subList(0, lastIndex);
        return new ESQueryResult(ESConst.CPU_TYPE, esMetricsesTopN);

    }

    @Override
    public String getSql(ESQueryCond esQueryCond) {
        return "";

    }

    @Override
    public List<ESMetrics> parse(ResultSet rs) throws SQLException {
        return new ArrayList<>();
    }

    private int compare(ESMetrics o1, ESMetrics o2) {
        long used1 = (long) o1.getValue(ESConst.CPU_USER) + (long) o1.getValue(ESConst.CPU_NICE)
                + (long) o1.getValue(ESConst.CPU_SYSTEM);
        long total1 = used1 + (long) o1.getValue(ESConst.CPU_IDEL)
                + (long) o1.getValue(ESConst.CPU_IOWAIT) + (long) o1.getValue(ESConst.CPU_IRQ)
                + (long) o1.getValue(ESConst.CPU_SOFTIRQ);

        long used2 = (long) o2.getValue(ESConst.CPU_USER) + (long) o2.getValue(ESConst.CPU_NICE)
                + (long) o2.getValue(ESConst.CPU_SYSTEM);
        long total2 = used2 + (long) o2.getValue(ESConst.CPU_IDEL)
                + (long) o2.getValue(ESConst.CPU_IOWAIT) + (long) o2.getValue(ESConst.CPU_IRQ)
                + (long) o2.getValue(ESConst.CPU_SOFTIRQ);

        int cpuRatio1;
        int cpuRatio2;
        if (total1 <= 0) {
            cpuRatio1 = 0;
        } else {
            cpuRatio1 = (int) (used1 / total1);
        }

        if (total2 <= 0) {
            cpuRatio2 = 0;
        } else {
            cpuRatio2 = (int) (used2 / total2);
        }

        return cpuRatio1 - cpuRatio2;
    }
}
