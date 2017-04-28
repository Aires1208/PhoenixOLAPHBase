package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.infra.PhoenixDBUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PhoenixDBUtil.class})
public class FileStaticsActionTest {

    @Before
    public void before() throws Exception {

        PowerMockito.mockStatic(PhoenixDBUtil.class);
        PowerMockito.when(PhoenixDBUtil.getCollectTime(anyObject())).thenReturn("2001-01-02");
    }

    @Test
    public void getSql() throws Exception {
        ESQueryCond queryCond = new ESQueryCond.ESQueryCondBuild()
                .agentId("fm")
                .agentStartTime(100L)
                .from(1L)
                .to(2L)
                .build();
        String sql = new FileStaticsAction().getSql(queryCond).trim();

        assertEquals(getExpectedSql(), sql);
        System.out.println(sql);
    }

    @Test
    public void parse() throws Exception {
        //given
        ResultSetStub resultSetStub = new ResultSetStub();

        //when
        List<ESMetrics> metricses = new FileStaticsAction().parse(resultSetStub);

        //then
        assert metricses.size() == 1;

        ESMetrics metrics = metricses.get(0);
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.AGENT_ID));
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.AGENT_STARTTIME));
        assertEquals(ResultSetStub.STRING_VALUE, metrics.getValue(ESConst.FILE_MOUNTON));
        assertEquals(ResultSetStub.NEGATIVE_VALUE, metrics.getValue(ESConst.COLLECT_TIME));
        assertEquals(ResultSetStub.LONG_VALUE, metrics.getValue(ESConst.FILE_TOTAL));
        assertEquals(ResultSetStub.LONG_VALUE, metrics.getValue(ESConst.FILE_USED));
        assertEquals(ResultSetStub.LONG_VALUE, metrics.getValue(ESConst.FILE_FREE));

    }

    public String getExpectedSql() {
        return "select AgentId, AgentStartTime, CollectTime, MountedOn,FileSystem,Total, Used, Free from SMARTSIGHT.FILESYSTEM_V1 where CollectTime='2001-01-02'    and AgentId='fm'\n" +
                "    and AgentStartTime='100'";
    }
}