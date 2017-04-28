package com.zte.ums.esight.domain.quartz;


import com.zte.ums.esight.domain.service.XLogger;
import com.zte.ums.esight.infra.DBConst;
import com.zte.ums.esight.infra.PhoenixDBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class UpgradeTableAction implements DBConst{
    protected static final Logger logger = LoggerFactory.getLogger(UpgradeTableAction.class.getName());
    private String table = "";
    private String column = "";

    public UpgradeTableAction() {

    }

    public UpgradeTableAction(String table, String column) {
        this.table = table;
        this.column = column;
    }

    public abstract String getCreateSql();

    public boolean upgrade() {
        logger.info("upgrade:" + SCHEMA + ":" + table + ":" + column);
        if (PhoenixDBUtil.isTableColumnExist(SCHEMA, table, column)) {
            XLogger.getInstance().log(SCHEMA + "." + table + ","
                    + column + " is exist");

        } else {
            List<String> sqls = new ArrayList<>();
            sqls.add(getCreateSql());

            PhoenixDBUtil.store(sqls);
        }

        return true;
    }
}
