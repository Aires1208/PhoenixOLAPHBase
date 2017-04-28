package com.zte.ums.esight.infra.upgrade;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UpgradeProcessTableActionTest {
    @Test
    public void getCreateSql() throws Exception {
        String expectedSql = "ALTER TABLE SMARTSIGHT.PROCESS_V1 ADD Res BIGINT";

        String sql = new UpgradeProcessTableAction().getCreateSql();

        assertEquals(expectedSql, sql);

    }

}