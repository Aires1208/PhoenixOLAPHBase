package com.zte.ums.esight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.domain.model.ESQueryCondJson;
import com.zte.ums.esight.domain.model.ESQueryResult;
import com.zte.ums.esight.domain.service.AgentsService;
import com.zte.ums.esight.domain.service.DataService;
import com.zte.ums.esight.domain.service.XLogger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@CrossOrigin(origins = "*", maxAge = 600)
@RestController
@Api("Phoenix Olap Api")
@RequestMapping("/phoenixolap")
public class OlapDataController {
    private static final ResponseEntity<?> SUCCESS = ResponseEntity.accepted().build();
    private XLogger xLogger = XLogger.getInstance();

    @Autowired
    @Qualifier("dataService")
    private DataService dataService;

    @Autowired
    @Qualifier("agentsService")
    private AgentsService agentsService;

    @ApiOperation(value="debug", notes="")
    @RequestMapping(value = "/api/v1/debug", method = GET)
    public String debug() {
        xLogger.switchDebug();

        return String.valueOf(xLogger.getDebug());
    }

    @RequestMapping(value = "/api/v1/query", method = RequestMethod.POST)
    public String query(@RequestBody byte[] body) {
        String query = new String(body);
        xLogger.log("Incoming query = " + query);

        try {

            ESQueryCond esQueryCond = new ESQueryCondJson().parse(query);

            ESQueryResult esQueryResult = dataService.query(esQueryCond);

            String nodesJson = new ObjectMapper().writeValueAsString(esQueryResult);

            return nodesJson;
        } catch (JsonProcessingException e) {
            xLogger.error(query, e);
        }

        return "{}";
    }


    @RequestMapping(value = "/api/v1/agentStat", method = POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DeferredResult<ResponseEntity<?>> receiveAgentStat(@RequestBody byte[] body) {

        DeferredResult<ResponseEntity<?>> result = new DeferredResult<>();

        dataService.store(body);

        result.setResult(SUCCESS);

        return result;
    }

    @RequestMapping(value = "/api/v1/agents", method = GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> getAgents() {
        try {
            ESQueryResult result = agentsService.query();
            String agents = new ObjectMapper().writeValueAsString(result);
            return ResponseEntity.ok(agents);
        } catch (JsonProcessingException e) {
            xLogger.error("get agents error:{}", e);
        }

        return ResponseEntity.ok("{}");
    }

}