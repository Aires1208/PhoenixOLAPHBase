package com.zte.ums.esight.domain.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ESQueryCond {
    private String agentId;
    private long agentStartTime;
    private long from;
    private long to;
    private String type;
    private String subType;

    private Map<String, String> parms = new HashMap();


    private ESQueryCond(String agentId, long agentStartTime, long from
            , long to, String type, String subType, Map<String, String> parms) {
        this.agentId = agentId;
        this.agentStartTime = agentStartTime;
        this.from = from;
        this.to = to;
        this.type = type;
        this.subType = subType;
        this.parms = parms;
    }


    public String getAgentId() {
        return agentId;
    }

    public long getAgentStartTime() {
        return agentStartTime;
    }

    public long getFrom() {
        return from;
    }

    public String getFromStr() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(new Date(from));
    }

    public String getGP() {
        String retGP = "DAY";
        long ONE_HOUR = 60 * 60 * 1000L;
        long ONE_DAY = 24 * ONE_HOUR;

        long gap = to - from;

        if (gap > 3 * ONE_DAY) {
            return retGP;
        } else if (gap > 3 * ONE_HOUR) {
            return "HOUR";
        } else if (gap > ONE_HOUR) {
            return "MINUTE";
        } else {
            return "SECOND";
        }

    }

    public String getParam(String key) {
        return parms.get(key);
    }

    public String getToStr() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(new Date(to));
    }

    public long getTo() {
        return to;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("agentId=").append(agentId).append("\n");
        stringBuilder.append("date agentStartTime=").append(new Date(agentStartTime)).append("\n");
        stringBuilder.append("long agentStartTime=").append(agentStartTime).append("\n");
        stringBuilder.append("date from=").append(new Date(from)).append("\n");
        stringBuilder.append("long from=").append(from).append("\n");
        stringBuilder.append("date to=").append(new Date(to)).append("\n");
        stringBuilder.append("long to=").append(to).append("\n");
        stringBuilder.append("type =").append(type).append("\n");
        stringBuilder.append("subType =").append(subType).append("\n");
        stringBuilder.append("parms =").append(parms).append("\n");

        return stringBuilder.toString();

    }

    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public static class ESQueryCondBuild {
        private String agentId;
        private long agentStartTime;
        private long from;
        private long to;
        private String type;
        private String subType;
        private Map<String, String> parms = new HashMap();


        public ESQueryCondBuild(ESQueryCond esQueryCond) {
            this.agentId = esQueryCond.getAgentId();
            this.agentStartTime = esQueryCond.getAgentStartTime();
            this.from = esQueryCond.getFrom();
            this.to = esQueryCond.getTo();
        }

        public ESQueryCondBuild() {

        }

        public ESQueryCondBuild agentId(String agentId) {
            this.agentId = agentId;
            return this;
        }

        public ESQueryCondBuild agentStartTime(long agentStartTime) {
            this.agentStartTime = agentStartTime;
            return this;
        }

        public ESQueryCondBuild from(long from) {
            this.from = from;
            return this;
        }

        public ESQueryCondBuild to(long to) {
            this.to = to;
            return this;
        }

        public ESQueryCondBuild type(String type) {
            this.type = type;
            return this;
        }

        public ESQueryCondBuild subType(String subType) {
            this.subType = subType;
            return this;
        }

        public ESQueryCondBuild parms(Map<String, String> parms) {
            this.parms = parms;
            return this;
        }

        public ESQueryCond build() {
            return new ESQueryCond(agentId,
                    agentStartTime, from, to, type, subType, parms);
        }
    }
}
