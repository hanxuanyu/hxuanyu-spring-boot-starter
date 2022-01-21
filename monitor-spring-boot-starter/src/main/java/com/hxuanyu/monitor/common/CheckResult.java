package com.hxuanyu.monitor.common;

import com.hxuanyu.notify.enums.NotifyType;

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

    public CheckResult() {
    }

    public CheckResult(boolean triggered, Object notifyContent, NotifyType notifyType) {
        this.triggered = triggered;
        this.notifyContent = notifyContent;
        this.notifyType = notifyType;
    }

    @Override
    public String toString() {
        return "Notify{" +
                "triggered=" + triggered +
                ", notifyContent='" + notifyContent + '\'' +
                '}';
    }

    /**
     * 未触发，直接调用即可，后续用来判断是否触发
     *
     * @return 通知对象
     */
    public static CheckResult nonTriggered() {
        return new CheckResult(false, null, null);
    }

    /**
     * 通知触发，需要传入通知信息
     *
     * @param notifyContent 通知内容
     * @param notifyType 通知类型
     * @return 返回结果
     */
    public static CheckResult triggered(Object notifyContent, NotifyType notifyType) {
        return new CheckResult(true, notifyContent, notifyType);
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
