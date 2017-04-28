package com.zte.ums.esight.domain.service;

import com.zte.ums.esight.domain.model.ESConst;
import com.zte.ums.esight.domain.model.ESQueryCond;
import com.zte.ums.esight.domain.model.ESQueryResult;
import com.zte.ums.esight.domain.model.PhoenixConst;
import com.zte.ums.esight.infra.query.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


@Service("dataService")
public class DataServiceImpl implements DataService {
    private static Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);

    public static final String CPU_QUOTA = ESConst.AGENTS_TYPE + ":" + "getCpuQuota";

    public static final String CPU_ENTITY = ESConst.CPU_TYPE + ":" + "getCpu";

    public static final String CPU_STATICS = ESConst.CPU_TYPE + ":" + "getCpuStatics";
    public static final String CPU_TOPN = ESConst.CPU_TYPE + ":" + "getCpuRatioTopN";
    public static final String CPU_BYTIME = ESConst.CPU_TYPE + ":" + "getCpuRatioByTime";

    public static final String DEVICE_BYNAME = ESConst.DEVICE_TYPE + ":" + "getDevicesByName";
    public static final String DEVICE_BYTIME = ESConst.DEVICE_TYPE + ":" + "getDevicesByTime";

    public static final String FILE_STATICS = ESConst.FILE_TYPE + ":" + "getFileStatics";
    public static final String FILE_USED_TOPN = ESConst.FILE_TYPE + ":" + "getFileUsedTopN";
    public static final String FILE_BYTIME = ESConst.FILE_TYPE + ":" + "getFilesByTime";

    public static final String NET_TOPN = ESConst.NET_TYPE + ":" + "getNetTopN";
    public static final String NET_STATICS = ESConst.NET_TYPE + ":" + "getNetStatics";
    public static final String NET_BYTIME = ESConst.NET_TYPE + ":" + "getNetsByTime";

    public static final String MEMORY_INFOS = ESConst.MEMORY_TYPE + ":" + "getMemInfos";
    public static final String MEMORY_AGG_INFOS = ESConst.MEMORY_TYPE + ":" + "getAggMemInfos";


    public static final String PROCESS_TOPN_USAGE = ESConst.PROCESS_TYPE + ":" + "getTopNUsage";
    public static final String PROCESS_TOPN_TIME = ESConst.PROCESS_TYPE + ":" + "getTopNTime";
    public static final String PROCESS_TOPN_VIRT = ESConst.PROCESS_TYPE + ":" + "getTopNVirt";
    public static final String PROCESS_TOPN_RES = ESConst.PROCESS_TYPE + ":" + "topn_res";

    public static final String PROCESS_TOPN_USAGE_BYTIME = ESConst.PROCESS_TYPE + ":" + "getHisTopNCpuUsage";
    public static final String PROCESS_TOPN_TIME_BYTIME = ESConst.PROCESS_TYPE + ":" + "getHisTopNCpuTime";
    public static final String PROCESS_TOPN_VIRT_BYTIME = ESConst.PROCESS_TYPE + ":" + "getHisTopNVirt";
    public static final String PROCESS_TOPN_RES_BYTIME = ESConst.PROCESS_TYPE + ":" + "topn_res_bytime";
    public static final String PROCESS_PID_BYTIME = ESConst.PROCESS_TYPE + ":" + "getHisProcessByPid";

    private static final int THREAD_NUMBERS = 50;
    private static final int QUEUE_SIZE = 10000;

    private long startTime = new Date().getTime();
    private AtomicLong count = new AtomicLong();

    private LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<>(QUEUE_SIZE);
    private ExecutorService executor = new ThreadPoolExecutor(THREAD_NUMBERS, THREAD_NUMBERS,
            0L, TimeUnit.MILLISECONDS,
            linkedBlockingQueue);

    private static Map<String, QueryAction> queryActionMap = new HashMap<>();

    static {
        queryActionMap.put(CPU_QUOTA, new QueryCpuQuotaAction());
        queryActionMap.put(CPU_ENTITY, new QueryCpuEntityAction());
        queryActionMap.put(CPU_STATICS, new QueryCpuStaticsAction(PhoenixConst.MAX));
        queryActionMap.put(CPU_TOPN, new QueryCpuRatioTopNAction(new QueryCpuStaticsAction(PhoenixConst.MAX)));
        queryActionMap.put(CPU_BYTIME, new QueryCpuRatioByTimeAction(new QueryHisCpuStaticsGroupbyAgentIdAction(PhoenixConst.MAX)));

        queryActionMap.put(DEVICE_BYNAME, new QueryDevicesByNameAction());
        queryActionMap.put(DEVICE_BYTIME, new HisDeviceGroupbyTimeAction());

        queryActionMap.put(FILE_USED_TOPN, new FileUsedTopNAction());
        queryActionMap.put(FILE_BYTIME, new FilesByTimeAction());
        queryActionMap.put(FILE_STATICS, new FileStaticsAction());

        queryActionMap.put(NET_TOPN, new QueryNetTopNAction());
        queryActionMap.put(NET_STATICS, new QueryNetStaticsAction());
        queryActionMap.put(NET_BYTIME, new QueryNetsByTimeAction());

        queryActionMap.put(MEMORY_INFOS, new HisMemoryGroupbyTimAction());
        queryActionMap.put(MEMORY_AGG_INFOS, new MemoryGroupbyAgentAction());

        queryActionMap.put(PROCESS_TOPN_USAGE, new QueryProcessTopNUsageAction());
        queryActionMap.put(PROCESS_TOPN_TIME, new QueryProcessTopNTimeAction());
        queryActionMap.put(PROCESS_TOPN_VIRT, new QueryProcessTopNVirtAction());
        queryActionMap.put(PROCESS_TOPN_RES, new QueryProcessTopNResAction());
        queryActionMap.put(PROCESS_TOPN_USAGE_BYTIME, new QueryProcessHisTopNCpuUsageAction());
        queryActionMap.put(PROCESS_TOPN_VIRT_BYTIME, new QueryProcessHisTopNVirtAction());
        queryActionMap.put(PROCESS_TOPN_TIME_BYTIME, new QueryProcessHisTopNCpuTimeAction());
        queryActionMap.put(PROCESS_TOPN_RES_BYTIME, new QueryProcessHisTopNResAction());
        queryActionMap.put(PROCESS_PID_BYTIME, new HisProcessByPidAction());
    }

    public DataServiceImpl() {
        super();
    }

    @Override
    public ESQueryResult query(ESQueryCond esQueryCond) {
        logger.info(esQueryCond.toString());

        String type = esQueryCond.getType();
        String subType = esQueryCond.getSubType();
        String kind = type + ":" + subType;

        QueryAction queryAction = queryActionMap.get(kind);

        ESQueryResult result = queryAction.query(esQueryCond);

        logger.info(result.toString());
        return result;
    }


    @Override
    public void store(byte[] body) {
        long elpasedTime = (new Date().getTime() - startTime) / 1000;

        double messageRatio = elpasedTime == 0 ? 0 : count.longValue() / elpasedTime;
        logger.info("receive message number is " + count.getAndIncrement()
                + ",messageRatio is " + messageRatio);
        logger.info("Queue size is " + linkedBlockingQueue.size());
        executor.submit(new AgentDataStoreHandler(body));


    }
}
