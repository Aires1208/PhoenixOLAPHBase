package com.zte.ums.esight.domain.model;

import com.zte.ums.esight.domain.config.EnvConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public abstract class PhoenixBaseDao {
    public static final String JDBC_URL = EnvConfiguration.jdbcUrl();
    public static final String DEFAULT_COLLECTTIME = "N/A";
    public static final String BLANK = " ";
    public static final String TWO_BLANK = BLANK + BLANK;
    public static final String FOUR_BLANK = TWO_BLANK + TWO_BLANK;
    public static final String SIX_BLANK = TWO_BLANK + TWO_BLANK + TWO_BLANK;
    public static final String NEW_LINE = "\n";
    public static final String MAX = "MAX";
    public static final String MIN = "MIN";
    public static final int TOPN = 5;
    private static Logger logger = LoggerFactory.getLogger(PhoenixBaseDao.class);

    public static String agg(String metric, String aggType) {
        return aggType + "(" + metric + ") as " + metric + BLANK;
    }

    public static long longTime(String time) {
        long retTime = -1L;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            retTime = simpleDateFormat.parse(time).getTime();
        } catch (ParseException e) {
            logger.error(time, e);
        }

        return retTime;
    }

    public static long longTime1(String time) {
        long retTime = -1L;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            retTime = simpleDateFormat.parse(time).getTime();
        } catch (ParseException e) {
            logger.error(time, e);
        }

        return retTime;
    }

    public abstract List<String> buildSql(PhoenixAgentStat phoenixAgentStat);

    public String dateStr(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(new Date(time));
    }

    public String dateStrMs(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return simpleDateFormat.format(new Date(time));
    }

    public void sortByTime(List<ESMetrics> esMetricses) {
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

    public Long XLong(Object object) {
        if (object instanceof Integer) {
            return ((Integer) object).longValue();
        }
        return (Long) object;
    }
}
