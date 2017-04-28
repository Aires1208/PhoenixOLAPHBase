package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class QueryCpuQuotaAction extends QueryAction {

    public QueryCpuQuotaAction() {
        super(ESConst.AGENTS_TYPE);
    }


    @Override
    public String getSql(ESQueryCond esQueryCond) {

        StringBuilder sb = new StringBuilder();
        sb.append("select *").append(BLANK)
                .append("from ").append(AGENTINDEX_FULL_TABLE).append(NEW_LINE)
                .append("where AgentId='").append(esQueryCond.getAgentId()).append("'").append(NEW_LINE)
                .append("and AgentStartTime='").append(esQueryCond.getAgentStartTime()).append("'").append(NEW_LINE);

        return sb.toString();
    }

    @Override
    public List<ESMetrics> parse(ResultSet rs) throws SQLException {
        List<ESMetrics> esMetricses = new ArrayList<>();

        while (rs.next()) {
            Map<String, Object> rowMap = new HashMap<>();
            rowMap.put(ESConst.CPU_SHARES, rs.getString(COLUMN__CPU_SHARES));
            rowMap.put(ESConst.CPU_PERIOD, rs.getString(COLUMN__CPU_PERIOD));
            rowMap.put(ESConst.CPU_QUOTA, rs.getString(COLUMN__CPU_QUOTA));
            rowMap.put(ESConst.CPU_SET, rs.getString(COLUMN__CPU_SET));

            esMetricses.add(new ESMetrics(rowMap));
        }

        return esMetricses;
    }
}
