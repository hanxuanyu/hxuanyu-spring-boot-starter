package com.hxuanyu.test.controller;

import com.hxuanyu.common.message.Msg;
import com.hxuanyu.monitor.base.BaseMonitorItem;
import com.hxuanyu.monitor.common.CheckResult;
import com.hxuanyu.monitor.manager.MonitorItemBeanManager;
import com.hxuanyu.notify.enums.NotifyType;
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
        return monitorItemBeanManager.addMonitorTask(new BaseMonitorItem("CustomBean", "0/10 * * * * *") {
            @Override
            public CheckResult check() {
                return CheckResult.triggered("动态新增通知", NotifyType.TYPE_LOG);
            }
        });
    }


}
