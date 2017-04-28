package com.zte.ums.esight.domain.service;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.domain.model.ESQueryResult;
import com.zte.ums.esight.domain.model.phoenix.ESDataFactory;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Ignore
public class DataServiceImplFileSystemTest {

    @Test
    public void testQuery_getDevicesByName() throws Exception {
        ESQueryCond esQueryCond = ESDataFactory
                .createFmAgentCond(ESConst.FILE_TYPE,"getFileUsedTopN");
        ESQueryResult esQueryResult1 = new DataServiceImpl().query(esQueryCond);


        ESQueryResult esQueryResult2 = new DataServiceImpl().query(esQueryCond);

        assertEquals(esQueryResult1.toString(),esQueryResult2.toString());

        System.out.println("******************* the string style is *******************");
        System.out.println(esQueryResult1.toString());

    }

    @Test
    public void testQuery_getDevicesByTime() throws Exception {
        ESQueryCond esQueryCond = ESDataFactory
                .createFmAgentCond(ESConst.FILE_TYPE,"getFilesByTime");
        ESQueryResult esQueryResult1 = new DataServiceImpl().query(esQueryCond);


        ESQueryResult esQueryResult2 = new DataServiceImpl().query(esQueryCond);

        assertEquals(esQueryResult1.toString(),esQueryResult2.toString());

        System.out.println("******************* the string style is *******************");
        System.out.println(esQueryResult1.toString());

    }

}