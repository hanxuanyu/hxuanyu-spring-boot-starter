package com.hxuanyu.starter.test.monitor;

import com.hxuanyu.monitor.base.BaseMonitorItem;
import com.hxuanyu.monitor.manager.MonitorItemBeanManager;
import com.hxuanyu.test.MainApplication;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MainApplication.class)
public class MonitorTest {

    private final Logger logger = LoggerFactory.getLogger(MonitorTest.class);
    @Autowired
    MonitorItemBeanManager monitorItemBeanManager;

    @Test
    public void testMonitorItemScan() {
        BaseMonitorItem testMonitorItem = monitorItemBeanManager.getMonitorItemMap().get("ScheduledTask-TestMonitorItem");
        if (testMonitorItem != null){
            assert testMonitorItem.getMonitorItemName() != null;
            logger.info("获取到的Bean：{}", testMonitorItem.toString());
        }
    }
}
