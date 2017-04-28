package com.zte.ums.esight.domain.service;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

@Ignore
public class AgentDataStoreHandlerTest {

    @Test
    public void testRun() throws Exception {

        String inputJson1 = "{\"agentId\":\"fm-agent80\",\"startTime\":1483300200000,\"collectTime\":1483300260000,\"cpu\":[{\"id\":\"1\",\"vendor\":\"vendor1\",\"family\":\"family1\",\"model\":\"model1\",\"modelName\":\"modelname1\",\"mhz\":\"2.7\",\"cache\":\"200\",\"user\":10,\"nice\":20,\"system\":30,\"idle\":40,\"iowait\":50,\"irq\":60,\"softirq\":70},{\"id\":\"2\",\"vendor\":\"vendor2\",\"family\":\"family2\",\"model\":\"model2\",\"modelName\":\"modelname2\",\"mhz\":\"3.7\",\"cache\":\"300\",\"user\":100,\"nice\":200,\"system\":300,\"idle\":400,\"iowait\":500,\"irq\":600,\"softirq\":700}],\"memory\":{\"vmTotal\":100,\"vmFree\":30,\"vmUsed\":70,\"phyTotal\":0,\"phyFree\":0,\"phyUsed\":0,\"swapTotal\":0,\"swapFree\":0,\"swapUsed\":0},\"file\":[{\"mountedOn\":\"/\",\"fileSystem\":\"/dev/sda1\",\"total\":100,\"free\":20,\"used\":80},{\"mountedOn\":\"/dev/shm\",\"fileSystem\":\"/dev/sda2\",\"total\":1000,\"free\":200,\"used\":800}],\"device\":[{\"deviceName\":\"dm-0\",\"read\":100,\"write\":200,\"tps\":10.0,\"readPerSecond\":11.0,\"writePerSecond\":22.0},{\"deviceName\":\"dm-1\",\"read\":1000,\"write\":2000,\"tps\":100.0,\"readPerSecond\":110.0,\"writePerSecond\":220.0}],\"net\":[{\"name\":\"eth0\",\"v4Address\":\"272001\",\"macAddress\":\"02234ajk\",\"mtu\":100,\"receiveBytes\":10,\"receiveErrors\":1,\"transmitBytes\":100,\"transmitErrors\":10,\"colls\":10},{\"name\":\"lo\",\"v4Address\":\"2062011\",\"macAddress\":\"12234ajk\",\"mtu\":1000,\"receiveBytes\":100,\"receiveErrors\":10,\"transmitBytes\":1000,\"transmitErrors\":100,\"colls\":100}],\"process\":[{\"pid\":\"1\",\"process\":\"java\",\"comand\":\"/usr/bin/java\",\"cpuUsage\":0.09,\"cpuTime\":100,\"virt\":3000},{\"pid\":\"2\",\"process\":\"python\",\"comand\":\"/usr/bin/python\",\"cpuUsage\":0.89,\"cpuTime\":1000,\"virt\":4000}]}";

        new AgentDataStoreHandler(inputJson1).run();

        String inputJson2 = "{\"agentId\":\"fm-agent80\",\"startTime\":1483300200000,\"collectTime\":1483300360000,\"cpu\":[{\"id\":\"1\",\"vendor\":\"vendor1\",\"family\":\"family1\",\"model\":\"model1\",\"modelName\":\"modelname1\",\"mhz\":\"2.7\",\"cache\":\"200\",\"user\":10,\"nice\":20,\"system\":30,\"idle\":40,\"iowait\":50,\"irq\":60,\"softirq\":70},{\"id\":\"2\",\"vendor\":\"vendor2\",\"family\":\"family2\",\"model\":\"model2\",\"modelName\":\"modelname2\",\"mhz\":\"3.7\",\"cache\":\"300\",\"user\":100,\"nice\":200,\"system\":300,\"idle\":400,\"iowait\":500,\"irq\":600,\"softirq\":700}],\"memory\":{\"vmTotal\":100,\"vmFree\":30,\"vmUsed\":70,\"phyTotal\":0,\"phyFree\":0,\"phyUsed\":0,\"swapTotal\":0,\"swapFree\":0,\"swapUsed\":0},\"file\":[{\"mountedOn\":\"/\",\"fileSystem\":\"/dev/sda1\",\"total\":100,\"free\":20,\"used\":80},{\"mountedOn\":\"/dev/shm\",\"fileSystem\":\"/dev/sda2\",\"total\":1000,\"free\":200,\"used\":800}],\"device\":[{\"deviceName\":\"dm-0\",\"read\":100,\"write\":200,\"tps\":10.0,\"readPerSecond\":11.0,\"writePerSecond\":22.0},{\"deviceName\":\"dm-1\",\"read\":1000,\"write\":2000,\"tps\":100.0,\"readPerSecond\":110.0,\"writePerSecond\":220.0}],\"net\":[{\"name\":\"eth0\",\"v4Address\":\"272001\",\"macAddress\":\"02234ajk\",\"mtu\":100,\"receiveBytes\":10,\"receiveErrors\":1,\"transmitBytes\":100,\"transmitErrors\":10,\"colls\":10},{\"name\":\"lo\",\"v4Address\":\"2062011\",\"macAddress\":\"12234ajk\",\"mtu\":1000,\"receiveBytes\":100,\"receiveErrors\":10,\"transmitBytes\":1000,\"transmitErrors\":100,\"colls\":100}],\"process\":[{\"pid\":\"1\",\"process\":\"java\",\"comand\":\"/usr/bin/java\",\"cpuUsage\":0.09,\"cpuTime\":100,\"virt\":3000},{\"pid\":\"2\",\"process\":\"python\",\"comand\":\"/usr/bin/python\",\"cpuUsage\":0.89,\"cpuTime\":1000,\"virt\":4000}]}";

        new AgentDataStoreHandler(inputJson2).run();
    }
}