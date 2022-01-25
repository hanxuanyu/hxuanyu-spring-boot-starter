package com.hxuanyu.monitor.common;

import com.hxuanyu.notify.enums.NotifyType;
import com.hxuanyu.notify.service.NotifyService;

/**
 * 触发器通知
 *
 * @author hanxuanyu
 * @version 1.0
 */
@SuppressWarnings("unused")
public class CheckResult {
    private boolean triggered;
    private Object notifyContent;
    private NotifyType notifyType;
    private NotifyService.CustomNotify customNotify;

    public CheckResult() {
    }

    private CheckResult(boolean triggered, Object notifyContent, NotifyType notifyType, NotifyService.CustomNotify customNotify) {
        this.triggered = triggered;
        this.notifyContent = notifyContent;
        this.notifyType = notifyType;
        this.customNotify = customNotify;
    }

    public NotifyService.CustomNotify getCustomNotify() {
        return customNotify;
    }

    public void setCustomNotify(NotifyService.CustomNotify customNotify) {
        this.customNotify = customNotify;
    }

    @Override
    public String toString() {
        return "CheckResult{" +
                "triggered=" + triggered +
                ", notifyContent=" + notifyContent +
                ", notifyType=" + notifyType +
                ", customNotify=" + customNotify +
                '}';
    }

    /**
     * 未触发，直接调用即可，后续用来判断是否触发
     *
     * @return 通知对象
     */
    public static CheckResult nonTriggered() {
        return new CheckResult(false, null, null, null);
    }

    /**
     * 通知触发，需要传入通知信息
     *
     * @param notifyContent 通知内容
     * @param notifyType    通知类型
     * @return 返回结果
     */
    public static CheckResult triggered(Object notifyContent, NotifyType notifyType) {
        return new CheckResult(true, notifyContent, notifyType, null);
    }



    /**
     * 自定义通知类型触发
     *
     * @param customNotify 自定义通知
     * @return 返回结果
     */
    public static CheckResult triggered(NotifyService.CustomNotify customNotify) {
        return new CheckResult(true, null, NotifyType.TYPE_CUSTOM, customNotify);
    }

    public boolean isTriggered() {
        return triggered;
    }

    public void setTriggered(boolean triggered) {
        this.triggered = triggered;
    }

    public Object getNotifyContent() {
        return notifyContent;
    }

    public void setNotifyContent(Object notifyContent) {
        this.notifyContent = notifyContent;
    }

    public NotifyType getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(NotifyType notifyType) {
        this.notifyType = notifyType;
    }
}
