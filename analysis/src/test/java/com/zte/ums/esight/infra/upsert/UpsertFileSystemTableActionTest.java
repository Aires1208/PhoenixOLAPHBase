package com.zte.ums.esight.infra.upsert;

import com.zte.ums.esight.domain.model.PhoenixAgentStat;
import com.zte.ums.esight.domain.model.phoenix.ESDataFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UpsertFileSystemTableActionTest {
    @Test
    public void buildSql() throws Exception {
        PhoenixAgentStat phoenixAgentStat = ESDataFactory.getPhoenixAgentStat();
        List<String> sqls = new UpsertFileSystemTableAction().buildSql(phoenixAgentStat);
        assertEquals(getExpectedSqls(), sqls.toString());
        System.out.println(sqls);
    }

    private String getExpectedSqls() {
        return "[upsert into SMARTSIGHT.FILESYSTEM_V1 (\n" +
                "  AgentId, AgentStartTime,Mac,CollectTime, \n" +
                "  MountedOn,FileSystem, Total, Used, Free\n" +
                "  )\n" +
                "values (\n" +
                "  'fm-agent80','1483253400000',' ','20170101145100','/','/dev/sda1',100,80,20\n" +
                ")\n" +
                ", upsert into SMARTSIGHT.FILESYSTEM_V1 (\n" +
                "  AgentId, AgentStartTime,Mac,CollectTime, \n" +
                "  MountedOn,FileSystem, Total, Used, Free\n" +
                "  )\n" +
                "values (\n" +
                "  'fm-agent80','1483253400000',' ','20170101145100','/dev/shm','/dev/sda2',1000,800,200\n" +
                ")\n" +
                "]";
    }

}