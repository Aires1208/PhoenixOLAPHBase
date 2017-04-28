package com.zte.ums.esight.domain.service;

import com.zte.ums.esight.domain.config.EnvConfiguration;
import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.domain.model.ESQueryResult;
import com.zte.ums.esight.domain.model.phoenix.ESDataFactory;
import com.zte.ums.esight.domain.quartz.UpgradeTableAction;
import com.zte.ums.esight.infra.Invoke;
import com.zte.ums.esight.infra.LoggerImpl;
import com.zte.ums.esight.infra.PhoenixDBUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import static org.junit.Assert.assertEquals;

@Ignore
public class DataServiceImplDeviceTest {
    @Before
    public void before() throws Exception {

        PowerMockito.mockStatic(EnvConfiguration.class);
        EnvConfiguration.setHbaseIP("10.62.100.164");
    }

    @Test
    public void testQuery_getDevicesByName() throws Exception {
//        PowerMockito.when(EnvConfiguration.jdbcUrl()).thenReturn("jdbc:phoenix:10.63.246.155:2181");
        ESQueryCond esQueryCond = new ESQueryCond.ESQueryCondBuild()
                .agentId("hello44")
                .agentStartTime(1492068267647L)
                .from(1492043989798L)
                .to(1492132189000L)
                .type(ESConst.DEVICE_TYPE)
                .subType("getDevicesByName")
                .build();

        ESQueryResult esQueryResult1 = new DataServiceImpl().query(esQueryCond);


        System.out.println("******************* the string style is *******************");
        System.out.println(esQueryResult1.toString());

    }

    @Test
    public void testQuery_getDevicesByTime() throws Exception {
        ESQueryCond esQueryCond = new ESQueryCond.ESQueryCondBuild()
                .agentId("hello44")
                .agentStartTime(1492068267647L)
                .from(1492043989798L)
                .to(1492132189000L)
                .type(ESConst.DEVICE_TYPE)
                .subType("getDevicesByTime")
                .build();


        ESQueryResult esQueryResult1 = new DataServiceImpl().query(esQueryCond);


        ESQueryResult esQueryResult2 = new DataServiceImpl().query(esQueryCond);

        assertEquals(esQueryResult1.toString(),esQueryResult2.toString());

        System.out.println("******************* the string style is *******************");
        System.out.println(esQueryResult1.toString());

    }

}