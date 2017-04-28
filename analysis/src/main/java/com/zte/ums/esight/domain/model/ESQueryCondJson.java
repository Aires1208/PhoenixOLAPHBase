package com.zte.ums.esight.domain.model;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 3/2/17.
 */
public class ESQueryCondJson {
    private static Logger logger = LoggerFactory.getLogger(ESQueryCondJson.class);

    public ESQueryCond parse(String data) {
        ESQueryCond esQueryCond = null;
        JsonParser parse = new JsonParser();
        try {
            JsonObject json = (JsonObject) parse.parse(data);

            JsonObject parmsObject = (JsonObject) json.get("parms");

            Map<String, String> parmsMap = getParms(parmsObject);

            String agentId = json.get(ESConst.AGENT_ID).getAsString();
            long startTime = json.get(ESConst.AGENT_STARTTIME).getAsLong();
            long from = json.get("from").getAsLong();
            long to = json.get("to").getAsLong();
            String type = json.get("type").getAsString();
            String subType = json.get("subType").getAsString();

            esQueryCond = new ESQueryCond.ESQueryCondBuild()
                    .agentId(agentId)
                    .agentStartTime(startTime)
                    .from(from)
                    .to(to)
                    .type(type)
                    .subType(subType)
                    .parms(parmsMap)
                    .build();


        } catch (JsonIOException | JsonSyntaxException e) {
            logger.error(data,e);
        }

        return esQueryCond;
    }


    private Map<String, String> getParms(JsonObject parmsObject) {

        if (parmsObject == null) return new HashMap<>();

        Map<String, String> map = new HashMap<>();

        JsonElement pidJsonEle = parmsObject.get("pid");
        if (pidJsonEle != null) {
            map.put(ESConst.PROCESS_PID, pidJsonEle.getAsString());
        }


        JsonElement processJsonEle = parmsObject.get("process");
        if (processJsonEle != null) {
            map.put(ESConst.PROCESS_NAME, processJsonEle.getAsString());
        }

        JsonElement commandJsonEle = parmsObject.get("command");
        if (commandJsonEle != null) {
            String tmpCommand = commandJsonEle instanceof JsonNull ? "null" : commandJsonEle.getAsString();
            map.put(ESConst.PROCESS_COMMAND, tmpCommand);
        }

        JsonElement macEle = parmsObject.get("macAddress");
        if (macEle != null) {
            String tmpMac = macEle instanceof JsonNull ? "null" : macEle.getAsString();
            map.put(ESConst.MAC_ADDRESS, tmpMac);
        }



        return map;
    }


}
