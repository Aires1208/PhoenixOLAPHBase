package com.zte.ums.esight.infra.upsert;

import com.zte.ums.esight.domain.model.PhoenixAgentStat;
import com.zte.ums.esight.domain.model.phoenix.ESDataFactory;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class UpsertCpuTableActionTest {

    @Test
    public void regParse_normal(){
        String cpus = "1,3-4,2-3";
        Set<String> cpuSet = new UpsertCpuTableAction().parse(cpus);
        assert cpuSet.size() == 4;
        assert cpuSet.contains("1");
        assert cpuSet.contains("2");
        assert cpuSet.contains("3");
        assert cpuSet.contains("4");
        System.out.println(cpuSet);
    }

    @Test
    public void regParse_null(){
        String cpus = "null";
        Set<String> cpuSet = new UpsertCpuTableAction().parse(cpus);
        assert cpuSet.size() == 0;

        System.out.println(cpuSet);
    }

    @Test
    public void buildSql() throws Exception {
        PhoenixAgentStat phoenixAgentStat = ESDataFactory.getPhoenixAgentStat();
        List<String> sqls = new UpsertCpuTableAction().buildSql(phoenixAgentStat);
        assertEquals(getExpectedSqls(), sqls.toString());
        System.out.println(sqls);
    }

    private String getExpectedSqls() {
        return "[upsert into SMARTSIGHT.CPU_V1 (\n" +
                "  AgentId, AgentStartTime,Mac,CollectTime, CpuId,\n" +
                "  CpuVendor, CpuFamily, CpuModel, CpuModelName,\n" +
                "  CpuMHZ, CpuCache, CpuUser, CpuNice, CpuSystem,\n" +
                "  CpuIdel, CpuIowait, CpuIRQ, CpuSoftIrq\n" +
                "  )\n" +
                "values (\n" +
                "  'fm-agent80','1483253400000',' ','20170101145100','1','vendor1','family1','model1','modelname1','2.7','200',10,20,30,40,50,60,70\n" +
                ")\n" +
                ", upsert into SMARTSIGHT.CPU_V1 (\n" +
                "  AgentId, AgentStartTime,Mac,CollectTime, CpuId,\n" +
                "  CpuVendor, CpuFamily, CpuModel, CpuModelName,\n" +
                "  CpuMHZ, CpuCache, CpuUser, CpuNice, CpuSystem,\n" +
                "  CpuIdel, CpuIowait, CpuIRQ, CpuSoftIrq\n" +
                "  )\n" +
                "values (\n" +
                "  'fm-agent80','1483253400000',' ','20170101145100','2','vendor2','family2','model2','modelname2','3.7','300',100,200,300,400,500,600,700\n" +
                ")\n" +
                "]";
    }

}