package com.zte.ums.esight.domain.model.phoenix;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.domain.model.PhoenixAgentStat;
import com.zte.ums.esight.domain.model.PhoenixAgentStatDeSerializer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ESDataFactory {
    public static ESQueryCond createWeekCond() {
        return new ESQueryCond.ESQueryCondBuild()
                .agentId("fm-agent80")
                .agentStartTime(1487302082458L)
                .from(1487120166066L)
                .to(1487638566066L)
                .build();
    }

    public static ESQueryCond createHourCond() {
        return new ESQueryCond.ESQueryCondBuild()
                .agentId("fm-agent80")
                .agentStartTime(1487302082458L)
                .from(1487638566066L - 10 * 60 * 60 * 1000)
                .to(1487638566066L + 13 * 60 * 60 * 1000)
                .build();
    }

    public static ESQueryCond createNFVTraceCond() {
        Map<String, String> parms = new HashMap<>();
        parms.put(ESConst.PROCESS_PID, "1");
        parms.put(ESConst.PROCESS_NAME, "java");
        parms.put(ESConst.PROCESS_COMMAND, "/bin/java");
        return new ESQueryCond.ESQueryCondBuild()
                .agentId("nfvtrace")
                .agentStartTime(1488650933889L)
                .from(487044800000L)
                .to(2487048400000L)
                .parms(parms)
                .build();
    }

    public static ESQueryCond createFmAgentCond(String type, String subType) {

        long from = dateParse("20170102035100") - 1000L;
        long to = dateParse("20170102035100") + 120 * 1000L;


        Map<String, String> parms = new HashMap<>();
        parms.put(ESConst.PROCESS_PID, "1");
        parms.put(ESConst.PROCESS_NAME, "java");
        parms.put(ESConst.PROCESS_COMMAND, "/bin/java");
        return new ESQueryCond.ESQueryCondBuild()
                .agentId("fm-agent80")
                .agentStartTime(1483300200000L)
                .from(from)
                .to(to)
                .type(type)
                .subType(subType)
                .parms(parms)
                .build();
    }

    private static long dateParse(String date) {
        long retTime = 0L;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            retTime = simpleDateFormat.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return retTime;
    }

    public static PhoenixAgentStat getPhoenixAgentStat() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addDeserializer(PhoenixAgentStat.class, new PhoenixAgentStatDeSerializer());
        mapper.registerModule(module);

        //when cpuset
        String inputJson = "{\"agentId\":\"fm-agent80\",\"startTime\":1483253400000" +
                ",\"collectTime\":1483253460000,\"macAddress\":\" \",\"cpuUsage\":0.335662,\"memUsage\":0.2" +
                ",\"ioRead\":0.345,\"ioWrite\":0.654,\"dlSpeed\":0.00181248,\"ulSpeed\":0.00974" +
                ",\"shares\":\"shares\",\"period\":\"period\",\"quota\":\"quota\",\"cpuset\":\"1,2-3\",\"cpu\":[{\"id\":\"1\",\"vendor\":\"vendor1\",\"family\":\"family1\",\"model\":\"model1\",\"modelName\":\"modelname1\",\"mhz\":\"2.7\",\"cache\":\"200\",\"user\":10,\"nice\":20,\"system\":30,\"idle\":40,\"iowait\":50,\"irq\":60,\"softirq\":70},{\"id\":\"2\",\"vendor\":\"vendor2\",\"family\":\"family2\",\"model\":\"model2\",\"modelName\":\"modelname2\",\"mhz\":\"3.7\",\"cache\":\"300\",\"user\":100,\"nice\":200,\"system\":300,\"idle\":400,\"iowait\":500,\"irq\":600,\"softirq\":700}],\"memory\":{\"vmTotal\":100,\"vmFree\":30,\"vmUsed\":70,\"phyTotal\":0,\"phyFree\":0,\"phyUsed\":0,\"swapTotal\":0,\"swapFree\":0,\"swapUsed\":0},\"file\":[{\"mountedOn\":\"/\",\"fileSystem\":\"/dev/sda1\",\"total\":100,\"free\":20,\"used\":80},{\"mountedOn\":\"/dev/shm\",\"fileSystem\":\"/dev/sda2\",\"total\":1000,\"free\":200,\"used\":800}],\"device\":[{\"deviceName\":\"dm-0\",\"read\":100,\"write\":200,\"tps\":10.0,\"readPerSecond\":11.0,\"writePerSecond\":22.0},{\"deviceName\":\"dm-1\",\"read\":1000,\"write\":2000,\"tps\":100.0,\"readPerSecond\":110.0,\"writePerSecond\":220.0}],\"net\":[{\"name\":\"eth0\",\"v4Address\":\"272001\",\"macAddress\":\"02234ajk\",\"mtu\":100,\"receiveBytes\":10,\"receiveErrors\":1,\"transmitBytes\":100,\"transmitErrors\":10,\"colls\":10},{\"name\":\"lo\",\"v4Address\":\"2062011\",\"macAddress\":\"12234ajk\",\"mtu\":1000,\"receiveBytes\":100,\"receiveErrors\":10,\"transmitBytes\":1000,\"transmitErrors\":100,\"colls\":100}],\"process\":[{\"pid\":\"1\",\"process\":\"java\",\"command\":\"/usr/bin/java\",\"cpuUsage\":0.09,\"cpuTime\":100,\"virt\":3000,\"res\":2000}" +
                ",{\"pid\":\"2\",\"process\":\"python\",\"command\":\"/usr/bin/python\"" +
                ",\"cpuUsage\":0.89,\"cpuTime\":1000,\"virt\":4000,\"res\":1234}]}";

        PhoenixAgentStat phoenixAgentStat = mapper.readValue(inputJson,PhoenixAgentStat.class);

        return phoenixAgentStat;
    }

}
