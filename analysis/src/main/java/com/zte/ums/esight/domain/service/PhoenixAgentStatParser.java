package com.zte.ums.esight.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.zte.ums.esight.domain.model.PhoenixAgentStat;
import com.zte.ums.esight.domain.model.PhoenixAgentStatDeSerializer;

import java.io.IOException;

/**
 * Created by root on 3/8/17.
 */
public class PhoenixAgentStatParser {
    private ObjectMapper mapper = new ObjectMapper();

    public PhoenixAgentStatParser() {
        SimpleModule module = new SimpleModule();

        module.addDeserializer(PhoenixAgentStat.class, new PhoenixAgentStatDeSerializer());
        mapper.registerModule(module);

    }
    public PhoenixAgentStat parse(String inputJson) throws IOException {

        PhoenixAgentStat phoenixAgentStat = mapper.readValue(inputJson,PhoenixAgentStat.class);

        return phoenixAgentStat;
    }
}
