package com.hxuanyu.test.monitor;

import com.hxuanyu.monitor.annotation.MonitorItem;
import com.hxuanyu.monitor.base.BaseMonitorItem;
import com.hxuanyu.monitor.common.CheckResult;
import com.hxuanyu.notify.enums.NotifyType;

/**
 * 定时任务监控测试
 *
 * @author hxuanyu
 */
@MonitorItem(cron = "0/5 * * * * *")
public class TestMonitorItem extends BaseMonitorItem {

    @Override
    public CheckResult check() {
        return CheckResult.triggered("Hello", NotifyType.TYPE_LOG);
    }
}
