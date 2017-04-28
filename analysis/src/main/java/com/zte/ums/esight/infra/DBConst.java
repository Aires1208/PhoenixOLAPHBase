package com.zte.ums.esight.infra;

public interface DBConst {
    String TTL_30_DAYS = "TTL=2592000";
    String TTL_ONE_YEAR = "TTL=31536000";
    String SCHEMA = "SMARTSIGHT";

    //table
    String AGENTINDEX_TABLE = "AGENTINDEX_v1";
    String CPU_TABLE = "CPU_V1";
    String DEVICE_TABLE = "DEVICE_V1";
    String FILESYSTEM_TABLE = "FILESYSTEM_V1";
    String MEMORY_TABLE = "MEMORY_V1";
    String NET_TABLE = "NET_V1";
    String PROCESS_TABLE = "PROCESS_V1";

    String AGENTINDEX_FULL_TABLE = SCHEMA + "." + AGENTINDEX_TABLE;
    String CPU_FULL_TABLE = SCHEMA + "." + CPU_TABLE;
    String DEVICE_FULL_TABLE = SCHEMA + "." + DEVICE_TABLE;
    String FILESYSTEM_FULL_TABLE = SCHEMA + "." + FILESYSTEM_TABLE;
    String MEMORY_FULL_TABLE = SCHEMA + "." + MEMORY_TABLE;
    String NET_FULL_TABLE = SCHEMA + "." + NET_TABLE;
    String PROCESS_FULL_TABLE = SCHEMA + "." + PROCESS_TABLE;

    String DEFAULT_COLLECTTIME = "N/A";
    String BLANK = " ";
    String TWO_BLANK = BLANK + BLANK;
    String FOUR_BLANK = TWO_BLANK + TWO_BLANK;
    String SIX_BLANK = TWO_BLANK + TWO_BLANK + TWO_BLANK;
    String NEW_LINE = "\n";
    String MAX = "MAX";
    String MIN = "MIN";
    int TOPN = 5;

    String COLUMN__CPU_SHARES = "CpuShares";
    String COLUMN__CPU_PERIOD = "CpuPeriod";
    String COLUMN__CPU_QUOTA = "CpuQuota";
    String COLUMN__CPU_SET = "CpuSet";
}
