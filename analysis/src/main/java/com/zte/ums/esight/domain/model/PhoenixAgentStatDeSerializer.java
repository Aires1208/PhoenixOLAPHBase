package com.zte.ums.esight.domain.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.*;

import java.io.IOException;
import java.util.*;


public class PhoenixAgentStatDeSerializer extends JsonDeserializer<PhoenixAgentStat> {

    @Override
    public PhoenixAgentStat deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        ObjectNode objectNode = jsonParser.getCodec().readTree(jsonParser);
        TextNode nameNode = (TextNode) objectNode.get(ESConst.AGENT_ID);
        LongNode startTimeNode = (LongNode) objectNode.get(ESConst.AGENT_STARTTIME);
        LongNode collectTimeNode = (LongNode) objectNode.get(ESConst.COLLECT_TIME);
        JsonNode macNode = objectNode.get(ESConst.MAC_ADDRESS);

        DoubleNode cpuUsage = (DoubleNode) objectNode.get(ESConst.CPU_USAGE);
        DoubleNode memUsage = (DoubleNode) objectNode.get(ESConst.MEM_USAGE);
        DoubleNode ioRead = (DoubleNode) objectNode.get(ESConst.IO_READ);
        DoubleNode ioWrite = (DoubleNode) objectNode.get(ESConst.IO_WRITE);
        DoubleNode dlSpeed = (DoubleNode) objectNode.get(ESConst.DL_SPEED);
        DoubleNode ulSpeed = (DoubleNode) objectNode.get(ESConst.UL_SPEED);

        JsonNode cpuShares = objectNode.get(ESConst.CPU_SHARES);
        JsonNode cpuPeriod = objectNode.get(ESConst.CPU_PERIOD);
        JsonNode cpuQuota = objectNode.get(ESConst.CPU_QUOTA);
        JsonNode cpuSet = objectNode.get(ESConst.CPU_SET);

        ArrayNode cpusNodes = (ArrayNode) objectNode.get(ESConst.CPU_TYPE);
        JsonNode memoryNode = objectNode.get(ESConst.MEMORY_TYPE);
        ArrayNode fileNodes = (ArrayNode) objectNode.get(ESConst.FILE_TYPE);
        ArrayNode deviceNodes = (ArrayNode) objectNode.get(ESConst.DEVICE_TYPE);
        ArrayNode netNodes = (ArrayNode) objectNode.get(ESConst.NET_TYPE);
        ArrayNode processNodes = (ArrayNode) objectNode.get(ESConst.PROCESS_TYPE);

        PhoenixAgentStat phoenixAgentStat = PhoenixAgentStat.Buidler()
                .AgentId(nameNode.asText())
                .StartTime(startTimeNode.longValue())
                .CollectTime(collectTimeNode.longValue())
                .MacAddress(macNode != null ? macNode.asText() : " ")
                .CpuUsage(cpuUsage.doubleValue())
                .MemUsage(memUsage.doubleValue())
                .IoRead(ioRead.doubleValue())
                .IoWrite(ioWrite.doubleValue())
                .DlSpeed(dlSpeed.doubleValue())
                .UlSpeed(ulSpeed.doubleValue())
                .CpuShares(cpuShares.asText(" "))
                .CpuPeriod(cpuPeriod.asText(" "))
                .CpuQuota(cpuQuota.asText(" "))
                .CpuSet(cpuSet.asText(" "))
                .build();


        phoenixAgentStat.addResult(new ESQueryResult(ESConst.CPU_TYPE, getESMetrics(cpusNodes)));
        phoenixAgentStat.addResult(new ESQueryResult(ESConst.MEMORY_TYPE, getNodeESMetrics(memoryNode)));
        phoenixAgentStat.addResult(new ESQueryResult(ESConst.FILE_TYPE, getESMetrics(fileNodes)));
        phoenixAgentStat.addResult(new ESQueryResult(ESConst.DEVICE_TYPE, getESMetrics(deviceNodes)));
        phoenixAgentStat.addResult(new ESQueryResult(ESConst.NET_TYPE, getESMetrics(netNodes)));
        phoenixAgentStat.addResult(new ESQueryResult(ESConst.PROCESS_TYPE, getESMetrics(processNodes)));

        return phoenixAgentStat;
    }

    private List<ESMetrics> getESMetrics(ArrayNode arrayNode) {
        List<ESMetrics> esMetricses = new ArrayList<>();
        if (arrayNode == null) return esMetricses;
        for (JsonNode node : arrayNode) {
            Iterator<String> it = node.fieldNames();
            Map<String, Object> map = new HashMap<>();
            while (it.hasNext()) {
                String fieldName = it.next();
                JsonNode valueNode = node.get(fieldName);
                if (valueNode instanceof IntNode) {
                    map.put(fieldName, valueNode.intValue());
                } else if (valueNode instanceof LongNode) {
                    map.put(fieldName, valueNode.longValue());
                } else if (valueNode instanceof DoubleNode) {
                    map.put(fieldName, valueNode.doubleValue());
                } else {
                    map.put(fieldName, valueNode.textValue());
                }
            }
            esMetricses.add(new ESMetrics(map));
        }

        return esMetricses;
    }

    private List<ESMetrics> getNodeESMetrics(JsonNode node) {
        List<ESMetrics> esMetricses = new ArrayList<>();
        if (node == null) return esMetricses;
        Iterator<String> it = node.fieldNames();
        Map<String, Object> map = new HashMap<>();
        while (it.hasNext()) {
            String fieldName = it.next();
            JsonNode valueNode = node.get(fieldName);
            if (valueNode instanceof IntNode) {
                map.put(fieldName, valueNode.intValue());
            } else if (valueNode instanceof LongNode) {
                map.put(fieldName, valueNode.longValue());
            } else if (valueNode instanceof DoubleNode) {
                map.put(fieldName, valueNode.doubleValue());
            } else {
                map.put(fieldName, valueNode.textValue());
            }
        }
        esMetricses.add(new ESMetrics(map));

        return esMetricses;
    }

}
