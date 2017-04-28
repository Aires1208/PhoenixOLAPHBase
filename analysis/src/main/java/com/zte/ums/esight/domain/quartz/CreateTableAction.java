package com.zte.ums.esight.domain.quartz;


import com.zte.ums.esight.domain.config.EnvConfiguration;
import com.zte.ums.esight.infra.DBConst;
import com.zte.ums.esight.infra.PhoenixDBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class CreateTableAction implements DBConst{
    private static Logger logger = LoggerFactory.getLogger(CreateTableAction.class);

    private String table = "";

    public abstract String getCreateSql();

    public CreateTableAction(String table) {
        this.table = table;
    }

    public void create() {

        if (isTableExist(SCHEMA, table)) {
            logger.info(SCHEMA + "." + table + " is exist");

        } else {
            List<String> sqls = new ArrayList<>();
            sqls.add(getCreateSql());

            logger.info("create:" + sqls);
            PhoenixDBUtil.store(sqls);
        }

    }


    private boolean isTableExist(String schemaPattern, String tableName) {
        boolean flag = false;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(EnvConfiguration.jdbcUrl());
            DatabaseMetaData meta = conn.getMetaData();
            String type[] = {"TABLE"};
            rs = meta.getTables(null, schemaPattern.toUpperCase(), tableName, type);
            flag = rs.next();
        } catch (SQLException e) {
            logger.error("isTableExist exception", e);
        } finally {
            PhoenixDBUtil.release(rs, null, conn);
        }

        return flag;
    }

    protected boolean isTableColumnExist(String schemaPattern
            , String tableName, String columnName) {
        boolean flag = false;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(EnvConfiguration.jdbcUrl());
            DatabaseMetaData meta = conn.getMetaData();
            rs = meta.getColumns(null, schemaPattern.toUpperCase(),
                    tableName.toUpperCase(), columnName.toUpperCase());
            flag = rs.next();
        } catch (SQLException e) {
            logger.error("isTableColumnExist exception", e);
        } finally {
            PhoenixDBUtil.release(rs, null, conn);
        }

        return flag;
    }
}
