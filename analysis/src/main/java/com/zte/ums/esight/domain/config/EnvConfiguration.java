package com.zte.ums.esight.domain.config;

public class EnvConfiguration {
    private static  String DEFAULT_HBASE_IP = "127.0.0.1";
    private static final String DEFAULT_HBASE_PORT = "2181";

    private static final String DEFAULT_TIME_ZONE = "Asia/Shanghai";


    public static String jdbcUrl() {
        return "jdbc:phoenix:" + getHbaseIp() + ":" + getHbasePort();
    }

    private static String getHbaseIp() {
        String hbaseIp = System.getenv("HBASE_IP");
        return hbaseIp == null ? DEFAULT_HBASE_IP : hbaseIp;
    }

    private static String getHbasePort() {
        String hbasePort = System.getenv("HBASE_PORT");
        return hbasePort == null ? DEFAULT_HBASE_PORT : hbasePort;
    }

    public static void setTimeZone() {
        String timeZone = System.getenv("user.timezone");
        String curTimeZone = timeZone == null ? DEFAULT_TIME_ZONE : timeZone;
        System.setProperty("user.timezone", curTimeZone);
    }

    public static void setHbaseIP(String ip){
        DEFAULT_HBASE_IP = ip;
    }


}
