package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.domain.model.ESQueryResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class QueryProcessHisTopNResAction extends QueryAction {

    public QueryProcessHisTopNResAction() {
        super(ESConst.PROCESS_TYPE);
    }

    @Override
    public ESQueryResult query(ESQueryCond esQueryCond) {
        List<ESMetrics> esMetricses = new ProcessByPidAction()
                .query(esQueryCond).getEsMetricses();


        return topNRes(esMetricses);
    }

    @Override
    public String getSql(ESQueryCond esQueryCond) {

        return "";
    }

    @Override
    public List<ESMetrics> parse(ResultSet rs) throws SQLException {

        return new ArrayList<>();
    }

    private ESQueryResult topNRes(List<ESMetrics> esMetricses) {
        esMetricses.sort(new Comparator<ESMetrics>() {
            @Override
            public int compare(ESMetrics o1, ESMetrics o2) {
                return xcompare(o1.getValue(ESConst.PROCESS_RES),
                        o2.getValue(ESConst.PROCESS_RES));

            }
        });


        return new ESQueryResult(ESConst.PROCESS_TYPE, copy(esMetricses));

    }
}
