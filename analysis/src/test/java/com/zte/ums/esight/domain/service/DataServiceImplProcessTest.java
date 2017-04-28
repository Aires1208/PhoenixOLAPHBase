package com.zte.ums.esight.domain.service;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.domain.model.ESQueryResult;
import com.zte.ums.esight.domain.model.phoenix.ESDataFactory;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Ignore
public class DataServiceImplProcessTest {

    @Test
    public void testQuery_getTopNUsage() throws Exception {
        ESQueryCond esQueryCond = ESDataFactory
                .createFmAgentCond(ESConst.PROCESS_TYPE,"getTopNUsage");
        ESQueryResult esQueryResult1 = new DataServiceImpl().query(esQueryCond);


        ESQueryResult esQueryResult2 = new DataServiceImpl().query(esQueryCond);

        assertEquals(esQueryResult1.toString(),esQueryResult2.toString());

        System.out.println("******************* the string style is *******************");
        System.out.println(esQueryResult1.toString());

    }

    @Test
    public void testQuery_getTopNTime() throws Exception {
        ESQueryCond esQueryCond = ESDataFactory
                .createFmAgentCond(ESConst.PROCESS_TYPE,"getTopNTime");
        ESQueryResult esQueryResult1 = new DataServiceImpl().query(esQueryCond);


        ESQueryResult esQueryResult2 = new DataServiceImpl().query(esQueryCond);

        assertEquals(esQueryResult1.toString(),esQueryResult2.toString());

        System.out.println("******************* the string style is *******************");
        System.out.println(esQueryResult1.toString());

    }

    @Test
    public void testQuery_getTopNVirt() throws Exception {
        ESQueryCond esQueryCond = ESDataFactory
                .createFmAgentCond(ESConst.PROCESS_TYPE,"getTopNVirt");
        ESQueryResult esQueryResult1 = new DataServiceImpl().query(esQueryCond);


        ESQueryResult esQueryResult2 = new DataServiceImpl().query(esQueryCond);

        assertEquals(esQueryResult1.toString(),esQueryResult2.toString());

        System.out.println("******************* the string style is *******************");
        System.out.println(esQueryResult1.toString());

    }

    @Test
    public void testQuery_getHisTopNCpuUsage() throws Exception {
        ESQueryCond esQueryCond = ESDataFactory
                .createFmAgentCond(ESConst.PROCESS_TYPE,"getHisTopNCpuUsage");
        ESQueryResult esQueryResult1 = new DataServiceImpl().query(esQueryCond);


        ESQueryResult esQueryResult2 = new DataServiceImpl().query(esQueryCond);

        assertEquals(esQueryResult1.toString(),esQueryResult2.toString());

        System.out.println("******************* the string style is *******************");
        System.out.println(esQueryResult1.toString());

    }

    @Test
    public void testQuery_getHisTopNCpuTime() throws Exception {
        ESQueryCond esQueryCond = ESDataFactory
                .createFmAgentCond(ESConst.PROCESS_TYPE,"getHisTopNCpuTime");
        ESQueryResult esQueryResult1 = new DataServiceImpl().query(esQueryCond);


        ESQueryResult esQueryResult2 = new DataServiceImpl().query(esQueryCond);

        assertEquals(esQueryResult1.toString(),esQueryResult2.toString());

        System.out.println("******************* the string style is *******************");
        System.out.println(esQueryResult1.toString());

    }

    @Test
    public void testQuery_getHisTopNVirt() throws Exception {
        ESQueryCond esQueryCond = ESDataFactory
                .createFmAgentCond(ESConst.PROCESS_TYPE,"getHisTopNVirt");
        ESQueryResult esQueryResult1 = new DataServiceImpl().query(esQueryCond);


        ESQueryResult esQueryResult2 = new DataServiceImpl().query(esQueryCond);

        assertEquals(esQueryResult1.toString(),esQueryResult2.toString());

        System.out.println("******************* the string style is *******************");
        System.out.println(esQueryResult1.toString());

    }

    @Test
    public void testQuery_getHisProcessByPid() throws Exception {
        ESQueryCond esQueryCond = ESDataFactory
                .createFmAgentCond(ESConst.PROCESS_TYPE,"getHisProcessByPid");
        ESQueryResult esQueryResult1 = new DataServiceImpl().query(esQueryCond);


        ESQueryResult esQueryResult2 = new DataServiceImpl().query(esQueryCond);

//        assertEquals(esQueryResult1.toString(),esQueryResult2.toString());

        System.out.println("******************* the string style is *******************");
        System.out.println(esQueryResult1.toString());
        System.out.println(esQueryResult2.toString());

    }


}