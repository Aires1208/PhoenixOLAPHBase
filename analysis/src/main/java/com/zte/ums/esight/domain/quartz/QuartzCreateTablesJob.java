package com.zte.ums.esight.domain.quartz;

import com.zte.ums.esight.infra.create.*;
import com.zte.ums.esight.infra.upgrade.UpgradeAgentIndexTableAction;
import com.zte.ums.esight.infra.upgrade.UpgradeProcessTableAction;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzCreateTablesJob implements Job {
    private static Logger logger = LoggerFactory.getLogger(QuartzCreateTablesJob.class);

    private CreateTableAction[] actions = new CreateTableAction[]{
            new CreateCpuTableAction(), new CreateDeviceTableAction(),
            new CreateFileSystemTableAction(), new CreateMemoryTableAction(),
            new CreateNetTableAction(), new CreateProcessTableAction(),
            new CreateAgentsTableAction()
    };

    private UpgradeTableAction[] upgradeActions = new UpgradeTableAction[]{
             new UpgradeProcessTableAction(), new UpgradeAgentIndexTableAction()
    };

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        logger.info("create tables begin");

        for (CreateTableAction createTableAction : actions) {
            createTableAction.create();
        }

        logger.info("create tables end");

        logger.info("upgrade tables begin");

        for (UpgradeTableAction upgradeTableAction : upgradeActions) {
            upgradeTableAction.upgrade();
        }

        logger.info("upgrade tables end");
    }

}
