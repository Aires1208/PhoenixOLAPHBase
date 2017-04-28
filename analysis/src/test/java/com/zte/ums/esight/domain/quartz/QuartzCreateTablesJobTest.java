package com.zte.ums.esight.domain.quartz;

import org.junit.Ignore;
import org.junit.Test;
import org.quartz.JobExecutionContext;

import static org.junit.Assert.*;

/**
 * Created by root on 17-3-16.
 */
@Ignore
public class QuartzCreateTablesJobTest {

    @Test
    public void testExecute() throws Exception {
        new QuartzCreateTablesJob().execute(null);
    }
}