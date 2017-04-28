package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.domain.model.ESQueryResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class QueryProcessTopNTimeAction extends QueryAction {

    public QueryProcessTopNTimeAction() {
        super(ESConst.PROCESS_TYPE);
    }

    @Override
    public ESQueryResult query(ESQueryCond esQueryCond) {
        List<ESMetrics> esMetricsesLast = new TimedProcessAction(MAX)
                .query(esQueryCond).getEsMetricses();
        Map<String, ESMetrics> esMetricsLastMap = new HashMap<>();
        for (ESMetrics esMetrics : esMetricsesLast) {
            esMetricsLastMap.put((String) esMetrics.getValue(ESConst.PROCESS_PID), esMetrics);
        }
        List<ESMetrics> esMetricses = new ArrayList<>();
        esMetricses.addAll(esMetricsLastMap.values());

        return topNCpuTime(esMetricses);
    }

    @Override
    public String getSql(ESQueryCond esQueryCond) {

        return "";
    }

    @Override
    public List<ESMetrics> parse(ResultSet rs) throws SQLException {

        return new ArrayList<ESMetrics>();
    }

    private ESQueryResult topNCpuTime(List<ESMetrics> esMetricses) {
        esMetricses.sort(new Comparator<ESMetrics>() {
            @Override
            public int compare(ESMetrics o1, ESMetrics o2) {
                return xcompare(o1.getValue(ESConst.PROCESS_CPU_TIME), o2.getValue(ESConst.PROCESS_CPU_TIME));

            }
        });


        return new ESQueryResult(ESConst.PROCESS_TYPE, copy(esMetricses));

    }
}
