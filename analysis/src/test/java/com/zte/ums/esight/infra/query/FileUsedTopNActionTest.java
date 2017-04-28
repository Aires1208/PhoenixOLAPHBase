package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.infra.Invoke;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class FileUsedTopNActionTest {
    @Test
    public void query() throws Exception {

    }

    @Test
    public void getSql() throws Exception {
        ESQueryCond queryCond = new ESQueryCond.ESQueryCondBuild()
                .agentId("fm")
                .agentStartTime(100L)
                .from(1L)
                .to(2L)
                .build();
        String sql = new FileUsedTopNAction().getSql(queryCond).trim();

        assertEquals(getExpectedSql(), sql);
        System.out.println(sql);
    }

    @Test
    public void parse() throws Exception {
        //given
        ResultSetStub resultSetStub = new ResultSetStub();

        //when
        List<ESMetrics> metricses = new FileUsedTopNAction().parse(resultSetStub);

        //then
        assert metricses.size() == 1;

        ESMetrics metrics = metricses.get(0);
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.AGENT_ID));
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.AGENT_STARTTIME));
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.FILE_MOUNTON));
        assertEquals(ResultSetStub.DOUBLE_VALUE, metrics.getValue(ESConst.FILE_TOTAL));
        assertEquals(ResultSetStub.DOUBLE_VALUE, metrics.getValue(ESConst.FILE_USED));
        assertEquals(ResultSetStub.DOUBLE_VALUE, metrics.getValue(ESConst.FILE_FREE));
    }

    private String getExpectedSql() {
        return "select AgentId, AgentStartTime, MountedOn,avg(Total) as Total, avg(Used) as Used, avg(Free) as Free\n" +
                "from SMARTSIGHT.FILESYSTEM_V1\n" +
                "where CollectTime>='19700101080000'\n" +
                "    and CollectTime<='19700101080000'\n" +
                "    and AgentId='fm'\n" +
                "    and AgentStartTime='100'\n" +
                "group by AgentId, AgentStartTime, MountedOn";
    }

    @Test
    public void sublist() {
        //given
        List<String> names = new ArrayList<>();
        names.add("1");
        names.add("2");
        names.add("3");
        int TOPN = 2;

        //when
        int lastIndex = names.size() < TOPN ? names.size() : TOPN;
        List<String> subNames = names.subList(0, lastIndex);

        //then
        assertEquals(TOPN, subNames.size());
    }

    @Test
    public void testCompare() {
        //given
        Map<String,Object> params1 = new HashMap();
        params1.put(ESConst.FILE_TOTAL,10.0);
        params1.put(ESConst.FILE_USED,1.0);
        ESMetrics o1 = new ESMetrics(params1);

        Map<String,Object> params2 = new HashMap();
        params2.put(ESConst.FILE_TOTAL,10.0);
        params2.put(ESConst.FILE_USED,2.0);
        ESMetrics o2 = new ESMetrics(params2);

        Map<String,Object> params3 = new HashMap();
        params3.put(ESConst.FILE_TOTAL,10.0);
        params3.put(ESConst.FILE_USED,3.0);
        ESMetrics o3 = new ESMetrics(params3);

        Map<String,Object> params4 = new HashMap();
        params4.put(ESConst.FILE_TOTAL,20.0);
        params4.put(ESConst.FILE_USED,6.0);
        ESMetrics o4 = new ESMetrics(params4);

        //then
        FileUsedTopNAction fileUsedTopNAction = new FileUsedTopNAction();
        assert fileUsedTopNAction.compare(o1,o2) == 1;
        assert fileUsedTopNAction.compare(o3,o2) == -1;
        assert fileUsedTopNAction.compare(o3,o4) == 0;
    }

}