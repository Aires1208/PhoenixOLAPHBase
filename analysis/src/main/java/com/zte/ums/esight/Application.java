package com.zte.ums.esight;

import com.zte.ums.esight.domain.config.EnvConfiguration;
import com.zte.ums.esight.domain.quartz.QuartzCreateTablesJob;
import com.zte.ums.esight.domain.quartz.QuartzMaintainer;
import org.quartz.SchedulerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws SchedulerException {

        System.setProperty("hadoop.home.dir", "/");

        EnvConfiguration.setTimeZone();

        QuartzMaintainer.addQuartzJob(QuartzCreateTablesJob.class,
                "default", QuartzMaintainer.PER_30_SECOND);

        SpringApplication.run(Application.class, args);
    }
}

