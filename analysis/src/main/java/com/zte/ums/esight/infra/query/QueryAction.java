package com.zte.ums.esight.infra.query;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.domain.model.ESQueryResult;
import com.zte.ums.esight.infra.DBConst;
import com.zte.ums.esight.infra.PhoenixDBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class QueryAction implements DBConst {
    private String title;

    public QueryAction(String title) {
        this.title = title;
    }

    public abstract String getSql(ESQueryCond esQueryCond);

    public abstract List<ESMetrics> parse(ResultSet rs) throws SQLException;

    public ESQueryResult query(ESQueryCond esQueryCond) {
        String sql = getSql(esQueryCond);

        List<ESMetrics> metricses = PhoenixDBUtil.excute(sql, this);

        return new ESQueryResult(title, metricses);
    }

    public String getAggCollectTime(ESQueryCond esQueryCond, String aggType, String table) {
        StringBuilder sb = new StringBuilder();

        sb.append("select ").append(BLANK)
                .append(aggType).append("(CollectTime) as CollectTime from ").append(table).append(BLANK)
                .append(getWhereCause(esQueryCond));

        return PhoenixDBUtil.getCollectTime(sb.toString());
    }

    protected String getWhereCause(ESQueryCond esQueryCond) {
        StringBuilder sb = new StringBuilder();
        sb.append("where CollectTime>='").append(esQueryCond.getFromStr()).append("'").append(NEW_LINE)
                .append(FOUR_BLANK).append("and CollectTime<='").append(esQueryCond.getToStr()).append("'").append(NEW_LINE)
                .append(FOUR_BLANK).append("and AgentId='").append(esQueryCond.getAgentId()).append("'").append(NEW_LINE);

        String mac = esQueryCond.getParam(ESConst.MAC_ADDRESS);
        if(acceptMac(mac)){
            sb.append(FOUR_BLANK).append("and Mac='").append(mac).append("'").append(NEW_LINE);
        } else {
            sb.append(FOUR_BLANK).append("and AgentStartTime='").append(esQueryCond.getAgentStartTime()).append("'").append(NEW_LINE);
        }

        return sb.toString();
    }

    protected String getEqualWhereCause(ESQueryCond esQueryCond,String collectTime) {
        StringBuilder sb = new StringBuilder();
        sb.append("where CollectTime='").append(collectTime).append("'")
                .append(FOUR_BLANK).append("and AgentId='").append(esQueryCond.getAgentId()).append("'").append(NEW_LINE);
        String mac = esQueryCond.getParam(ESConst.MAC_ADDRESS);
        if(acceptMac(mac)){
            sb.append(FOUR_BLANK).append("and Mac='").append(mac).append("'").append(NEW_LINE);
        } else {
            sb.append(FOUR_BLANK).append("and AgentStartTime='").append(esQueryCond.getAgentStartTime()).append("'").append(NEW_LINE);
        }

        return sb.toString();
    }

    private boolean acceptMac(String mac) {
        if(mac == null){
            return false;
        }
        if(mac.trim().length() == 0) {
            return false;
        }
        if(mac.equalsIgnoreCase("null")) {
            return false;
        }

        return true;
    }

    protected  String agg(String metric, String aggType) {
        return aggType + "(" + metric + ") as " + metric + BLANK;
    }

    protected void sortByTime(List<ESMetrics> esMetricses) {
        esMetricses.sort((o1, o2) -> {
            long collectTime1 = (long) o1.getValue(ESConst.COLLECT_TIME);
            long collectTime2 = (long) o2.getValue(ESConst.COLLECT_TIME);


            if (collectTime1 > collectTime2) {
                return 1;
            } else if (collectTime1 == collectTime2) {
                return 0;
            } else {
                return -1;
            }

        });
    }

    protected Long XLong(Object object) {
        if (object instanceof Integer) {
            return ((Integer) object).longValue();
        }
        return (Long) object;
    }

    public int xcompare(Object o1, Object o2) {
        int ret = 0;
        if (o1 instanceof Double) {
            double virt1 = (double) o1;
            double virt2 = (double) o2;
            double diff = virt1 - virt2;

            if (diff > 0) {
                ret = -1;
            } else if (diff < 0) {
                ret = 1;
            } else {
                ret = 0;
            }
        } else if (o1 instanceof Integer) {
            int virt1 = (int) o1;
            int virt2 = (int) o2;
            int diff = virt1 - virt2;
            if (diff > 0) {
                ret = -1;
            }
            if (diff == 0) {
                ret = 0;
            }
            if (diff < 0) {
                ret = 1;
            }
        } else if (o1 instanceof Long) {
            long virt1 = (long) o1;
            long virt2 = (long) o2;
            long diff = virt1 - virt2;
            if (diff > 0) {
                ret = -1;
            }
            if (diff == 0) {
                ret = 0;
            }
            if (diff < 0) {
                ret = 1;
            }
        }

        return ret;

    }

    public List<ESMetrics> copy(List<ESMetrics> esMetricses) {

        int lastIndex = esMetricses.size() > TOPN ? TOPN : esMetricses.size();

        List<ESMetrics> retEsMetricses = new ArrayList<>();
        for (int i = 0; i < lastIndex; i++) {
            retEsMetricses.add(esMetricses.get(i));
        }

        return retEsMetricses;
    }
}
