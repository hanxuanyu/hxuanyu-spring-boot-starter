package com.hxuanyu.monitor.manager;

import com.hxuanyu.common.message.Msg;
import com.hxuanyu.monitor.annotation.MonitorItem;
import com.hxuanyu.monitor.base.BaseMonitorItem;
import com.hxuanyu.monitor.common.CheckResult;
import com.hxuanyu.monitor.config.DefaultSchedulingConfigurer;
import com.hxuanyu.notify.enums.NotifyType;
import com.hxuanyu.notify.service.NotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 当Bean注入成功后对Bean进行拉取，并根据注解的值为Item的属性赋值
 *
 * @author hanxuanyu
 * @version 1.0
 */
@Component
public class MonitorItemBeanManager implements ApplicationListener<ContextRefreshedEvent> {
    private final Logger logger = LoggerFactory.getLogger(MonitorItemBeanManager.class);
    private final Map<String, BaseMonitorItem> MONITOR_ITEM_MAP = new HashMap<>();

    @Resource
    DefaultSchedulingConfigurer schedulingConfigurer;

    @Resource
    NotifyService notifyService;

    public Map<String, BaseMonitorItem> getMonitorItemMap() {
        return MONITOR_ITEM_MAP;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 根容器为Spring容器
        if (event.getApplicationContext().getParent() == null) {
            logger.info("=====ContextRefreshedEvent====={}", event.getSource().getClass().getName());
            Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(MonitorItem.class);
            for (Object bean : beans.values()) {
                if (bean instanceof BaseMonitorItem) {
                    BaseMonitorItem item = (BaseMonitorItem) bean;
                    MonitorItem annotation = item.getClass().getAnnotation(MonitorItem.class);
                    if (annotation != null) {
                        String cron = annotation.cron();
                        String name = annotation.name();
                        if ("".equals(name)) {
                            name = item.getClass().getSimpleName();
                        }
                        item.setMonitorItemName(name);
                        item.setCron(cron);
                        logger.info("获取到的Bean：{}", item);
                        Msg<String> msg = addMonitorTask(item);
                        logger.info("添加成功：{}", msg);
                    }
                }
            }
        }
    }

    public Msg<String> addMonitorTask(BaseMonitorItem item) {
        String taskId = "ScheduledTask-" + item.getMonitorItemName();
        if (MONITOR_ITEM_MAP.containsKey(taskId)) {
            return Msg.failed("任务已经存在，请执行修改操作");
        }
        MONITOR_ITEM_MAP.put(taskId, item);
        logger.info("添加定时任务：{}, 执行周期：{}", taskId, item.getCron());
        addTask(taskId, item);
        return Msg.success("添加成功");
    }

    public Msg<String> setMonitorTaskCron(String taskId, String cron) {
        if (MONITOR_ITEM_MAP.containsKey(taskId)) {
            schedulingConfigurer.cancelTriggerTask(taskId);
            BaseMonitorItem item = MONITOR_ITEM_MAP.get(taskId);
            item.setCron(cron);
            addTask(taskId, item);
            logger.info("修改定时任务：{}, 执行周期：{}", taskId, item.getCron());
            return Msg.success("修改成功");
        } else {
            return Msg.failed("修改失败，该任务不存在");
        }
    }

    public Msg<String> deleteMonitorTask(String taskId) {
        if (MONITOR_ITEM_MAP.containsKey(taskId)) {
            MONITOR_ITEM_MAP.remove(taskId);
            schedulingConfigurer.cancelTriggerTask(taskId);
            logger.info("删除定时任务：{}", taskId);
            return Msg.success("删除任务成功");
        } else {
            return Msg.failed("任务不存在");
        }
    }

    private void addTask(String taskId, BaseMonitorItem item) {
        String cron = item.getCron();
        schedulingConfigurer.resetTriggerTask(taskId, new TriggerTask(() -> {
            CheckResult checkResult = item.check();
            if (checkResult.isTriggered()) {
                if (NotifyType.TYPE_CUSTOM.equals(checkResult.getNotifyType())){
                    logger.info("定时任务[{}]触发成功，执行自定义通知", taskId);
                    notifyService.notify(checkResult.getCustomNotify());
                } else {
                    logger.info("定时任务[{}]触发成功，发送通知：[{}]", taskId, checkResult.getNotifyContent());
                    notifyService.notify(checkResult.getNotifyContent(), checkResult.getNotifyType());
                }
            }
        }, new CronTrigger(cron)));
    }
}
