package com.zte.ums.esight.domain.quartz;

import org.junit.Before;
import org.junit.Test;
import org.quartz.SchedulerException;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;


public class QuartzMaintainerTest {

    @Before
    public void setup() throws SchedulerException {
        TestJob.setJobCounter(0);
    }

    @Test
    public void test_add_quartz_job() throws SchedulerException, InterruptedException {
        QuartzMaintainer.addQuartzJob(TestJob.class, "test_add_quartz_job", "*/2 * * * * ?");
        TimeUnit.SECONDS.sleep(5);
        QuartzMaintainer.removeQuartzJob("test_add_quartz_job");
        assertTrue(TestJob.getJobCounter() > 0);
    }

    @Test
    public void test_modify_quartz_time() throws SchedulerException, InterruptedException {
        QuartzMaintainer.addQuartzJob(TestJob.class, "test_modify_quartz_job", "*/2 * * * * ?");
        TimeUnit.SECONDS.sleep(6);
        QuartzMaintainer.modifyQuartzJobTime("test_modify_quartz_job", "*/1 * * * * ?");
        TimeUnit.SECONDS.sleep(5);
        QuartzMaintainer.removeQuartzJob("test_modify_quartz_job");
        assertTrue(TestJob.getJobCounter() > 8);
    }

    @Test
    public void test_remove_quartz_time() throws SchedulerException, InterruptedException {
        QuartzMaintainer.addQuartzJob(TestJob.class, "test_remove_quartz_job", "*/2 * * * * ?");
        TimeUnit.SECONDS.sleep(6);
        QuartzMaintainer.removeQuartzJob("test_remove_quartz_job");
        TimeUnit.SECONDS.sleep(10);
        assertTrue(TestJob.getJobCounter() < 5);
    }
}