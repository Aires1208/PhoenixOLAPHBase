package com.zte.ums.esight.infra;

import com.zte.ums.esight.domain.config.EnvConfiguration;
import com.zte.ums.esight.domain.model.ESMetrics;
import com.zte.ums.esight.domain.service.XLogger;
import com.zte.ums.esight.infra.query.QueryAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhoenixDBUtil {
    protected static Logger logger = LoggerFactory.getLogger(PhoenixDBUtil.class);
    public static final String JDBC_URL = EnvConfiguration.jdbcUrl();

    public static boolean store(List<String> sqls) {
        Connection conn = null;
        Statement stmt = null;
        try {
            XLogger.getInstance().log("phoenix sql is :" + sqls);
            conn = DriverManager.getConnection(JDBC_URL);

            stmt = conn.createStatement();

            for (String sql : sqls) {
                stmt.executeUpdate(sql);
                XLogger.getInstance().log("=======execute sql:" + sql);
            }

            conn.commit();

        } catch (SQLException e) {
            logger.error("store exception", e);
            return false;
        } finally {
            release(stmt, conn);
        }

        return true;
    }

    public static List<ESMetrics> excute(String sql, QueryAction action) {
        List<ESMetrics> metricses = new ArrayList<>();
        Connection conn = null;
        Statement pstm = null;
        ResultSet rs = null;
        try {

            conn = DriverManager.getConnection(JDBC_URL);

            logger.info("phoenix sql is :" + sql);

            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery(sql);

            metricses = action.parse(rs);

        } catch (SQLException e) {
            logger.error("query exception", e);
        } finally {

            release(rs, pstm, conn);
        }
        return metricses;
    }

    public static String getCollectTime(String sql) {
        String collectTime = "";

        Connection conn = null;
        Statement pstm = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(JDBC_URL);
            logger.info(sql);
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery(sql);
            while (rs.next()) {
                collectTime = rs.getString("CollectTime");
                logger.info("collectTime = " + collectTime);
            }
        } catch (SQLException e) {
            logger.error("Connection exception", e);
        } finally {
            release(rs, pstm, conn);
        }

        return collectTime;
    }

    public static boolean isTableColumnExist(String schemaPattern
            , String tableName, String columnName) {
        boolean flag = false;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(JDBC_URL);
            DatabaseMetaData meta = conn.getMetaData();
            rs = meta.getColumns(null, schemaPattern.toUpperCase(),
                    tableName.toUpperCase(), columnName.toUpperCase());
            flag = rs.next();
        } catch (SQLException e) {
            logger.error("isTableColumnExist exception", e);
        } finally {
            release(rs, null, conn);
        }

        return flag;
    }

    public static void release(Statement stmt, Connection conn) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            logger.error("stmt close exception", e);
        }

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error("conn close exception", e);
        }
    }

    public static void release(ResultSet rs, Statement stmt, Connection conn) {

        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            logger.error("re close exception", e);
        }

        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            logger.error("stmt close exception", e);
        }

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error("conn close exception", e);
        }

    }
}
