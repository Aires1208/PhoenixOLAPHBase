package com.zte.ums.esight.domain.service;

import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.domain.model.ESQueryResult;
import com.zte.ums.esight.domain.model.PhoenixAgentStat;

public interface DataService {
    ESQueryResult query(ESQueryCond esQueryCond);
    void store(byte[] body);
}
