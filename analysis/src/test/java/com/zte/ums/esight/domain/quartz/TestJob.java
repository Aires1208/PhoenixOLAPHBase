package com.zte.ums.esight.domain.quartz;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJob implements Job {
    private static int jobCounter = 0;

    public static int getJobCounter() {
        return jobCounter;
    }

    public static void setJobCounter(int jobCounter) {
        TestJob.jobCounter = jobCounter;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        jobCounter++;
        System.out.println("jobCounter is :" + jobCounter);

    }
}
