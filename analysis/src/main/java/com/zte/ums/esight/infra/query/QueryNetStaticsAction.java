package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.domain.model.ESQueryResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryNetStaticsAction extends QueryAction {
    public QueryNetStaticsAction() {
        super(ESConst.NET_TYPE);
    }

    @Override
    public ESQueryResult query(ESQueryCond esQueryCond) {
        List<ESMetrics> esMetricses = new TimedNetAction(MAX)
                .query(esQueryCond).getEsMetricses();

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
}
