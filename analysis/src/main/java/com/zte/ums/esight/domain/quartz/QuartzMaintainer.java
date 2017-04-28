package com.zte.ums.esight.domain.quartz;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class QuartzMaintainer {
    public static final String PER_30_SECOND = "*/30 * * * * ?";
    private static final Logger logger = LoggerFactory.getLogger(QuartzMaintainer.class);

    private static final Properties quartzProperties = new Properties();

    private static SchedulerFactory schedulerFactory;

    private static final String JOB_GROUP = "PHOENIX_JOB_GROUP";

    private static final String TRIGGER_GROUP = "PHOENIX_TRIGGER_GROUP";

    static {
        try {
            quartzProperties.setProperty("org.quartz.scheduler.instanceName", "QuartzMaintainerScheduler");
            quartzProperties.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
            quartzProperties.setProperty("org.quartz.threadPool.threadCount", "1");
            quartzProperties.setProperty("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
            schedulerFactory = new StdSchedulerFactory(quartzProperties);
        } catch (SchedulerException e) {
            logger.error("Initial Scheduler Factory Error" + e.getMessage(), e);
        }
    }

    private QuartzMaintainer() {
    }

    public static void addQuartzJob(Class jobClass, String jobName, String time) throws SchedulerException {
        addQuartzJob(jobClass, jobName, JOB_GROUP, jobName, TRIGGER_GROUP, time);
    }
    private static void addQuartzJob(Class jobClass, JobKey jobKey, TriggerKey triggerKey, String time) throws SchedulerException {
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobDetail jobDetail = JobBuilder.newJob().ofType(jobClass).withIdentity(jobKey).build();
        Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(time))
                .withIdentity(triggerKey).build();
        scheduler.scheduleJob(jobDetail, trigger);
        if (!scheduler.isShutdown()) {
            logger.info("add QuartzCreateTablesJob success " + jobClass);
            scheduler.start();
        }
    }

    private static void addQuartzJob(Class jobClass, String jobName, String jobGroup, String triggerName, String triggerGroup, String time) throws SchedulerException {
        addQuartzJob(jobClass, JobKey.jobKey(jobName, jobGroup), TriggerKey.triggerKey(triggerName, triggerGroup), time);
    }


    public static void modifyQuartzJobTime(String jobName, String time) throws SchedulerException {
        modifyQuartzJobTime(jobName, JOB_GROUP, jobName, TRIGGER_GROUP, time);
    }

    private static void modifyQuartzJobTime(JobKey jobKey, TriggerKey triggerKey, String time) throws SchedulerException {
        Scheduler scheduler = schedulerFactory.getScheduler();
        Trigger trigger = scheduler.getTrigger(triggerKey);
        if (trigger == null) {
            return;
        }
        String oldTime = ((CronTrigger) trigger).getCronExpression();
        if (!oldTime.equalsIgnoreCase(time)) {
            Class jobClass = scheduler.getJobDetail(jobKey).getJobClass();
            removeQuartzJob(jobKey, triggerKey);
            addQuartzJob(jobClass, jobKey, triggerKey, time);
        }
    }

    private static void modifyQuartzJobTime(String jobName, String jobGroup, String triggerName, String triggerGroup, String time) throws SchedulerException {
        modifyQuartzJobTime(JobKey.jobKey(jobName, jobGroup), TriggerKey.triggerKey(triggerName, triggerGroup), time);
    }

    public static void removeQuartzJob(String jobName) throws SchedulerException {
        removeQuartzJob(jobName, JOB_GROUP, jobName, TRIGGER_GROUP);
    }

    private static void removeQuartzJob(JobKey jobKey, TriggerKey triggerKey) throws SchedulerException {
        Scheduler schedule = schedulerFactory.getScheduler();
        schedule.pauseTrigger(triggerKey);
        schedule.unscheduleJob(triggerKey);
        schedule.deleteJob(jobKey);
    }

    private static void removeQuartzJob(String jobName, String jobGroup, String triggerName, String triggerGroup) throws SchedulerException {
        removeQuartzJob(JobKey.jobKey(jobName, jobGroup), TriggerKey.triggerKey(triggerName, triggerGroup));
    }

}
