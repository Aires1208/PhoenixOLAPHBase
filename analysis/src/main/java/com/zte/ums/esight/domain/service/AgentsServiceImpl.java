package com.zte.ums.esight.domain.service;

import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.domain.model.ESQueryResult;
import com.zte.ums.esight.infra.query.AgentsQueryAction;
import com.zte.ums.esight.infra.query.QueryAction;
import org.springframework.stereotype.Service;

/**
 * Created by root on 17-3-30.
 */
@Service("agentsService")
public class AgentsServiceImpl implements AgentsService {
    @Override
    public ESQueryResult query() {
        QueryAction action = new AgentsQueryAction();

        return action.query(new ESQueryCond.ESQueryCondBuild().build());
    }
}
