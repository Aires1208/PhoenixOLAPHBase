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

public class MemoryGroupbyAgentAction extends QueryAction {
    public MemoryGroupbyAgentAction() {
        super(ESConst.MEMORY_TYPE);
    }

    @Override
    public String getSql(ESQueryCond esQueryCond) {
        StringBuilder sb = new StringBuilder();

        sb.append("select AgentId, AgentStartTime").append(NEW_LINE)
                .append(",avg(VmTotal) as VmTotal, avg(VmFree) as VmFree, avg(VmUsed) as VmUsed").append(NEW_LINE)
                .append(",avg(PhyTotal) as PhyTotal, avg(PhyFree) as PhyFree, avg(PhyUsed) as PhyUsed").append(NEW_LINE)
                .append(",avg(SwapTotal) as SwapTotal, avg(SwapFree) as SwapFree, avg(SwapUsed) as SwapUsed").append(NEW_LINE)
                .append("from ").append(MEMORY_FULL_TABLE).append(NEW_LINE)
                .append(FOUR_BLANK).append(getWhereCause(esQueryCond))
                .append(FOUR_BLANK).append("group by AgentId, AgentStartTime").append(NEW_LINE);

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
            rowMap.put(ESConst.VM_TOTAL, rs.getDouble("VmTotal"));
            rowMap.put(ESConst.VM_USED, rs.getDouble("VmUsed"));
            rowMap.put(ESConst.VM_FREE, rs.getDouble("VmFree"));

            rowMap.put(ESConst.PHY_TOTAL, rs.getDouble("PhyTotal"));
            rowMap.put(ESConst.PHY_USED, rs.getDouble("PhyUsed"));
            rowMap.put(ESConst.PHY_FREE, rs.getDouble("PhyFree"));

            rowMap.put(ESConst.SWAP_TOTAL, rs.getDouble("SwapTotal"));
            rowMap.put(ESConst.SWAP_USED, rs.getDouble("SwapUsed"));
            rowMap.put(ESConst.SWAP_FREE, rs.getDouble("SwapFree"));

            esMetricses.add(new ESMetrics(rowMap));
        }

        return esMetricses;
    }
}
