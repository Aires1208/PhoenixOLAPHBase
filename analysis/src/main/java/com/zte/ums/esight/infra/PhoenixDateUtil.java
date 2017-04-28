package com.zte.ums.esight.infra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PhoenixDateUtil {
    private static Logger logger = LoggerFactory.getLogger(PhoenixDateUtil.class);

    public static String dateStr(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(new java.util.Date(time));
    }

    public static String dateStrMs(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return simpleDateFormat.format(new java.util.Date(time));
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
}
