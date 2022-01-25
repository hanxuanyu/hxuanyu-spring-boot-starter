package com.hxuanyu.test.monitor;

import com.hxuanyu.monitor.annotation.MonitorItem;
import com.hxuanyu.monitor.base.BaseMonitorItem;
import com.hxuanyu.monitor.common.CheckResult;
import com.hxuanyu.notify.enums.NotifyType;
import com.hxuanyu.notify.model.Mail;
import com.hxuanyu.notify.service.NotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定时任务监控测试
 *
 * @author hxuanyu
 */
@MonitorItem(cron = "0/20 * * * * *")
public class TestMonitorItem extends BaseMonitorItem {

    private final Logger logger = LoggerFactory.getLogger(TestMonitorItem.class);

    @Override
    public CheckResult check() {

        double random = Math.random();
        int result = (int) (random * NotifyType.values().length);
        logger.info("随机索引值：{}", result);
        if (NotifyType.values().length > 0) {
            NotifyType notifyType = NotifyType.values()[result];
            switch (notifyType) {
                case TYPE_LOG:
                    return CheckResult.triggered("日志输出", NotifyType.TYPE_LOG);
                case TYPE_CUSTOM:
                    return CheckResult.triggered(() -> logger.info("自定义通知"));
                case TYPE_MAIL:
                    return CheckResult.triggered(new Mail("2252193204@qq.com", "测试邮件主题", "测试邮件内容"), NotifyType.TYPE_MAIL);
                case TYPE_MSG:
                    return CheckResult.triggered("短信通知", NotifyType.TYPE_MSG);
                default:
                    return CheckResult.triggered("默认通知", null);
            }
        }

        return CheckResult.nonTriggered();
    }
}
