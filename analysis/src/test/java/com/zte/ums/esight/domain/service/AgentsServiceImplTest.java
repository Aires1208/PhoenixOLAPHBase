package com.zte.ums.esight.domain.service;

import com.zte.ums.esight.domain.model.ESQueryResult;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class AgentsServiceImplTest {
    @Test
    public void query() throws Exception {
        AgentsService agentsService = new AgentsServiceImpl();

        ESQueryResult result = agentsService.query();

        System.out.println(result);

    }

}