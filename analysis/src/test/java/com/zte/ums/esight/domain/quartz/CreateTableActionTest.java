package com.zte.ums.esight.domain.quartz;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

@Ignore
public class CreateTableActionTest {

    @Test
    public void testIsTableColumnExist() throws Exception {
        String schemaPattern = "SMARTSIGHT";
        String table = "PROCESS";
        String column = "res";
        boolean exist = new CreateTableActionImpl(table)
                .isTableColumnExist(schemaPattern,table,column);

        assertTrue(exist);

    }

    class CreateTableActionImpl extends CreateTableAction{

        public CreateTableActionImpl(String table) {
            super(table);
        }

        @Override
        public String getCreateSql() {
            return null;
        }
    }
}