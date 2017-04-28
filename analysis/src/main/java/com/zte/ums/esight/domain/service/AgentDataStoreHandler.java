package com.zte.ums.esight.domain.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.zte.ums.esight.domain.model.PhoenixAgentStat;
import com.zte.ums.esight.infra.upsert.UpsertAgentStatAction;

public class AgentDataStoreHandler implements Runnable {

    private static PhoenixAgentStatParser phoenixAgentStatParser = new PhoenixAgentStatParser();

    private static UpsertAgentStatAction upsertAgentStatAction = new UpsertAgentStatAction();

    private String inputJson = "";

    public AgentDataStoreHandler(String inputJson) {

        this.inputJson = inputJson;
    }

    public AgentDataStoreHandler(byte[] body) {
        inputJson = new String(body);
        XLogger.getInstance().log(inputJson);
    }

    @Override
    public void run() {
        try {

            PhoenixAgentStat phoenixAgentStat = phoenixAgentStatParser.parse(inputJson);

            List<String> sqls = upsertAgentStatAction.buildSql(phoenixAgentStat);

            long begin = new Date().getTime();

            upsertAgentStatAction.store(sqls);

            long end = new Date().getTime();

            XLogger.getInstance().log(new Date() + ",spend time is " + (end - begin) + " mil seconds");

        } catch (IOException  e) {
            XLogger.getInstance().error(inputJson, e);
        }
    }
}
