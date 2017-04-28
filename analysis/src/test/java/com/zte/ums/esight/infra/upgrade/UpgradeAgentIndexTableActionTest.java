package com.zte.ums.esight.infra.upgrade;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by root on 17-4-20.
 */
public class UpgradeAgentIndexTableActionTest {
    @Test
    public void getCreateSql() throws Exception {
        String expectedSql = "ALTER TABLE SMARTSIGHT.AGENTINDEX_v1 ADD CpuShares varchar, CpuPeriod varchar, CpuQuota varchar, CpuSet varchar";

        String sql = new UpgradeAgentIndexTableAction().getCreateSql();

        assertEquals(expectedSql, sql);
    }

}