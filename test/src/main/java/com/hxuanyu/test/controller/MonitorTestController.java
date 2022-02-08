package com.hxuanyu.test.controller;

import com.hxuanyu.common.message.Msg;
import com.hxuanyu.monitor.base.BaseMonitorItem;
import com.hxuanyu.monitor.manager.MonitorItemBeanManager;
import com.hxuanyu.test.monitor.TestMonitorItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;

/**
 * 监控项测试控制器
 *
 * @author hanxuanyu
 * @version 1.0
 */

@RequestMapping("/monitor")
@RestController
public class MonitorTestController {
    private final Logger logger = LoggerFactory.getLogger(MonitorTestController.class);

    @Resource
    MonitorItemBeanManager monitorItemBeanManager;

    @PutMapping("/")
    public Msg<String> setCron(String taskId, String taskCron) {
        if (taskId == null || taskCron == null) {
            return Msg.failed("参数不匹配");
        }
        return monitorItemBeanManager.setMonitorTaskCron(taskId, taskCron);
    }

    @DeleteMapping("/")
    public Msg<String> deleteTask(String taskId) {
        if (taskId == null) {
            return Msg.failed("taskId 未填写");
        }
        return monitorItemBeanManager.deleteMonitorTask(taskId);
    }

    @GetMapping("/")
    public Msg<Collection<BaseMonitorItem>> getTaskList() {
        Map<String, BaseMonitorItem> monitorItemMap = monitorItemBeanManager.getMonitorItemMap();
        return Msg.success("获取成功", monitorItemMap.values());
    }

    @PostMapping("/")
    public Msg<String> addTaskList() {
        return monitorItemBeanManager.addMonitorTask(TestMonitorItem.class, "0/5 * * * * *");
    }


}
