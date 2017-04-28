package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.domain.model.ESQueryResult;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryCpuRatioTopNActionTest {
    @Test
    public void query() throws Exception {

        QueryCpuStaticsAction queryCpuStaticsAction = PowerMockito.mock(QueryCpuStaticsAction.class);
        ESQueryCond queryCond = new ESQueryCond.ESQueryCondBuild().build();
        PowerMockito.when(queryCpuStaticsAction.query(queryCond)).thenReturn(getResult());

        ESQueryResult result = new QueryCpuRatioTopNAction(queryCpuStaticsAction).query(queryCond);

        assert result.getEsMetricses().size() == 1;

        System.out.println(result);

    }

    private ESQueryResult getResult() {
        List<ESMetrics> metricses = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put(ESConst.CPU_ID, "1");
        map.put(ESConst.CPU_USER, 100L);
        map.put(ESConst.CPU_NICE, 100L);
        map.put(ESConst.CPU_SYSTEM, 100L);
        map.put(ESConst.CPU_IDEL, 100L);
        map.put(ESConst.CPU_IOWAIT, 100L);
        map.put(ESConst.CPU_IRQ, 100L);
        map.put(ESConst.CPU_SOFTIRQ, 100L);

        metricses.add(new ESMetrics(map));

        return new ESQueryResult(ESConst.CPU_TYPE, metricses);
    }
}