package com.hxuanyu.monitor.base;


import com.hxuanyu.monitor.common.CheckResult;

/**
 * 监控项模型类
 *
 * @author hanxuanyu
 * @version 1.0
 */
public abstract class BaseMonitorItem {
    private String monitorItemName;
    private String cron;

    public BaseMonitorItem() {
    }

    public BaseMonitorItem(String monitorItemName, String cron) {
        this.monitorItemName = monitorItemName;
        this.cron = cron;
    }

    public String getMonitorItemName() {
        return monitorItemName;
    }

    public void setMonitorItemName(String monitorItemName) {
        this.monitorItemName = monitorItemName;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }


    /**
     * 监控项检查器，需子类手动实现触发通知的条件，监控服务会定时调用该方法
     * 并根据返回结果判断是否通知
     *
     * @return true：触发，false：不触发
     */
    public abstract CheckResult check();


    @Override
    public String toString() {
        return "BaseMonitorItem{" +
                "monitorItemName='" + monitorItemName + '\'' +
                ", cron='" + cron + '\'' +
                '}';
    }
}
