package com.zte.ums.esight.domain.quartz;

import com.zte.ums.esight.infra.Invoke;
import com.zte.ums.esight.infra.LoggerImpl;
import com.zte.ums.esight.infra.PhoenixDBUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Matchers.anyObject;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PhoenixDBUtil.class})

public class UpgradeTableActionTest {
    @Before
    public void before() throws Exception {

        PowerMockito.mockStatic(PhoenixDBUtil.class);
        Invoke.setFinalStatic(UpgradeTableAction.class.getDeclaredField("logger"), new LoggerImpl());
    }

    @Test
    public void upgrade_column_is_exist() throws Exception {

        PowerMockito.when(PhoenixDBUtil.isTableColumnExist(anyObject(), anyObject(), anyObject())).thenReturn(true);

        assert new UpgradeTableActionImpl().upgrade();

    }

    @Test
    public void upgrade_column_not_exist() throws Exception {

        PowerMockito.when(PhoenixDBUtil.isTableColumnExist(anyObject(), anyObject(), anyObject())).thenReturn(false);
        PowerMockito.when(PhoenixDBUtil.store(anyObject())).thenReturn(true);

        UpgradeTableActionImpl upgradeTableAction = new UpgradeTableActionImpl();

        assert upgradeTableAction.upgrade();

    }


    class UpgradeTableActionImpl extends UpgradeTableAction {
        private static final String test = "dfdf";

        public UpgradeTableActionImpl() {
//            super("table", "column");

        }

        @Override
        public String getCreateSql() {
            return "";
        }
    }

}