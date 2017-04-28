package com.zte.ums.esight.domain.service;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.domain.model.ESQueryResult;
import com.zte.ums.esight.domain.model.phoenix.ESDataFactory;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

@Ignore
public class DataServiceImplCpuTest {

    @Test
    public void testQuery_getCpuStatics() throws Exception {
        ESQueryCond esQueryCond = ESDataFactory
                .createFmAgentCond(ESConst.CPU_TYPE,"getCpuStatics");
        ESQueryResult esQueryResult1 = new DataServiceImpl().query(esQueryCond);


        ESQueryResult esQueryResult2 = new DataServiceImpl().query(esQueryCond);

        assertEquals(esQueryResult1.toString(),esQueryResult2.toString());

        System.out.println("******************* the string style is *******************");
        System.out.println(esQueryResult1.toString());

    }

    @Test
    public void testQuery_getCpuRatioTopN() throws Exception {
        ESQueryCond esQueryCond = ESDataFactory
                .createFmAgentCond(ESConst.CPU_TYPE,"getCpuRatioTopN");
        ESQueryResult esQueryResult1 = new DataServiceImpl().query(esQueryCond);


        ESQueryResult esQueryResult2 = new DataServiceImpl().query(esQueryCond);

        assertEquals(esQueryResult1.toString(),esQueryResult2.toString());

        System.out.println("******************* the string style is *******************");
        System.out.println(esQueryResult1.toString());

    }


    @Test
    public void testQuery_getCpuRatioByTime() throws Exception {
        ESQueryCond esQueryCond = ESDataFactory
                .createFmAgentCond(ESConst.CPU_TYPE,"getCpuRatioByTime");
        ESQueryResult esQueryResult1 = new DataServiceImpl().query(esQueryCond);


        ESQueryResult esQueryResult2 = new DataServiceImpl().query(esQueryCond);

        assertEquals(esQueryResult1.toString(),esQueryResult2.toString());

        System.out.println("******************* the string style is *******************");
        System.out.println(esQueryResult1.toString());

    }
}