package com.zte.ums.esight.infra;

import com.zte.ums.esight.domain.quartz.UpgradeTableAction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PhoenixDBUtil.class})
public class PhoenixDBUtilTest {
    @Before
    public void before() throws Exception {


        PowerMockito.mockStatic(DriverManager.class);
        PhoenixDBUtil.logger = new LoggerImpl();
    }

    @Test
    public void getCollectTime() throws Exception {
        PowerMockito.mockStatic(PhoenixDBUtil.class);
        PowerMockito.when(PhoenixDBUtil.getCollectTime(anyObject())).thenReturn("2001-01-02");
        System.out.println(PhoenixDBUtil.getCollectTime("sql"));
    }

    @Test
    public void store() throws Exception {
        PowerMockito.when(DriverManager.getConnection(anyObject())).thenReturn(new ConnectionStub());

        List<String> sqls = new ArrayList<>();
        sqls.add("this is sql");
        boolean ret = PhoenixDBUtil.store(sqls);

        assert ret;
    }

}