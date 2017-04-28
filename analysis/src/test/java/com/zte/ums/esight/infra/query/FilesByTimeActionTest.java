package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class FilesByTimeActionTest {

    @Test
    public void getSql() throws Exception {
        ESQueryCond queryCond = new ESQueryCond.ESQueryCondBuild()
                .agentId("fm")
                .agentStartTime(100L)
                .from(1L)
                .to(2L)
                .build();
        String sql = new FilesByTimeAction().getSql(queryCond).trim();

        assertEquals(getExpectedSql(), sql);
        System.out.println(sql);
    }

    @Test
    public void parse() throws Exception {
        //given
        ResultSetStub resultSetStub = new ResultSetStub();

        //when
        List<ESMetrics> metricses = new FilesByTimeAction().parse(resultSetStub);

        //then
        assert metricses.size() == 1;

        ESMetrics metrics = metricses.get(0);
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.AGENT_ID));
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.AGENT_STARTTIME));
        assertEquals(ResultSetStub.NEGATIVE_VALUE, metrics.getValue(ESConst.COLLECT_TIME));
        assertEquals(ResultSetStub.DOUBLE_VALUE, metrics.getValue(ESConst.FILE_TOTAL));
        assertEquals(ResultSetStub.DOUBLE_VALUE, metrics.getValue(ESConst.FILE_USED));
        assertEquals(ResultSetStub.DOUBLE_VALUE, metrics.getValue(ESConst.FILE_FREE));
    }

    private String getExpectedSql() {
        return "select AgentId, AgentStartTime,TRUNC(TO_DATE(CollectTime,'yyyyMMddHHmmss'),'SECOND')  as CollectTime1\n" +
                ",avg(Total) as Total, avg(Used) as Used, avg(Free) as Free\n" +
                "from SMARTSIGHT.FILESYSTEM_V1\n" +
                "    where CollectTime>='19700101080000'\n" +
                "    and CollectTime<='19700101080000'\n" +
                "    and AgentId='fm'\n" +
                "    and AgentStartTime='100'\n" +
                "    group by AgentId, AgentStartTime,TRUNC(TO_DATE(CollectTime,'yyyyMMddHHmmss'),'SECOND')";
    }
}