package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.domain.model.ESQueryResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUsedTopNAction extends QueryAction {

    public FileUsedTopNAction() {
        super(ESConst.FILE_TYPE);
    }

    @Override
    public ESQueryResult query(ESQueryCond esQueryCond) {
        List<ESMetrics> esMetricses = super.query(esQueryCond).getEsMetricses();

        esMetricses.sort((o1, o2) -> compare(o1, o2));

        int lastIndex = esMetricses.size() < TOPN ? esMetricses.size() : TOPN;
        List<ESMetrics> subMetricses = esMetricses.subList(0, lastIndex);

        return new ESQueryResult(ESConst.FILE_TYPE, subMetricses);
    }

    @Override
    public String getSql(ESQueryCond esQueryCond) {
        StringBuilder sb = new StringBuilder();
        sb.append("select AgentId, AgentStartTime, MountedOn,avg(Total) as Total, avg(Used) as Used, avg(Free) as Free").append(NEW_LINE)
                .append("from ").append(FILESYSTEM_FULL_TABLE).append(NEW_LINE)
                .append(getWhereCause(esQueryCond))
                .append("group by AgentId, AgentStartTime, MountedOn");

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
            rowMap.put(ESConst.FILE_MOUNTON, rs.getString("MountedOn"));
            rowMap.put(ESConst.FILE_TOTAL, rs.getDouble("Total"));
            rowMap.put(ESConst.FILE_USED, rs.getDouble("Used"));
            rowMap.put(ESConst.FILE_FREE, rs.getDouble("Free"));

            esMetricses.add(new ESMetrics(rowMap));
        }


        return esMetricses;
    }

    protected int compare(ESMetrics o1, ESMetrics o2) {
        double used1 = (double) o1.getValue(ESConst.FILE_USED);
        double total1 = (double) o1.getValue(ESConst.FILE_TOTAL);

        double used2 = (double) o2.getValue(ESConst.FILE_USED);
        double total2 = (double) o2.getValue(ESConst.FILE_TOTAL);

        if (used1 / total1 > used2 / total2) {
            return -1;
        }
        if (used1 / total1 < used2 / total2) {
            return 1;
        } else {
            return 0;
        }
    }
}
