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

public class FileStaticsAction extends QueryAction {
    public FileStaticsAction() {
        super(ESConst.FILE_TYPE);
    }

    @Override
    public String getSql(ESQueryCond esQueryCond) {
        String collectTime = getAggCollectTime(esQueryCond, MAX, FILESYSTEM_FULL_TABLE);
        StringBuilder sb = new StringBuilder();
        sb.append("select AgentId, AgentStartTime, CollectTime, MountedOn,")
                .append("FileSystem,Total, Used, Free").append(BLANK)
                .append("from ").append(FILESYSTEM_FULL_TABLE).append(BLANK)
                .append(getEqualWhereCause(esQueryCond,collectTime));

        return sb.toString();
    }

    @Override
    public List<ESMetrics> parse(ResultSet rs) throws SQLException {
        List<ESMetrics> metricses = new ArrayList<>();

        while (rs.next()) {
            Map<String, Object> rowMap = new HashMap<>();
            rowMap.put(ESConst.AGENT_ID, rs.getString("AgentId"));
            rowMap.put(ESConst.AGENT_STARTTIME, rs.getString("AgentStartTime"));
            rowMap.put(ESConst.COLLECT_TIME, PhoenixDateUtil.longTime(rs.getString("CollectTime")));
            rowMap.put(ESConst.FILE_MOUNTON, rs.getString("MountedOn"));
            rowMap.put(ESConst.FILE_SYSTEM, rs.getString("FileSystem"));
            rowMap.put(ESConst.FILE_TOTAL, rs.getLong("Total"));
            rowMap.put(ESConst.FILE_USED, rs.getLong("Used"));
            rowMap.put(ESConst.FILE_FREE, rs.getLong("Free"));

            metricses.add(new ESMetrics(rowMap));
        }

        return metricses;
    }
}
