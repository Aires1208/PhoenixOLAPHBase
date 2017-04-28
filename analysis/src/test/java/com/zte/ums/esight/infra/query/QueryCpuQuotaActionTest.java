package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by root on 17-4-21.
 */
public class QueryCpuQuotaActionTest {

    @Test
    public void getSql() throws Exception {
        ESQueryCond queryCond = new ESQueryCond.ESQueryCondBuild()
                .agentId("fm")
                .agentStartTime(100L)
                .from(1L)
                .to(2L)
                .build();
        String sql = new QueryCpuQuotaAction().getSql(queryCond).trim();

        assertEquals(getExpectedSql(), sql);
        System.out.println(sql);
    }

    @Test
    public void parse() throws Exception {
        //given
        ResultSetStub resultSetStub = new ResultSetStub();

        //when
        List<ESMetrics> metricses = new QueryCpuQuotaAction().parse(resultSetStub);

        //then
        assert metricses.size() == 1;

        ESMetrics metrics = metricses.get(0);
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.CPU_SHARES));
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.CPU_PERIOD));
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.CPU_QUOTA));
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.CPU_SET));


    }

    private String getExpectedSql() {
        return "select * from SMARTSIGHT.AGENTINDEX_v1\n" +
                "where AgentId='fm'\n" +
                "and AgentStartTime='100'";
    }


}
