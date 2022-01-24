package com.hxuanyu.notify.enums;

/**
 * 通知类型，目前只有邮件
 *
 * @author hanxuanyu
 * @version 1.0
 */
public enum NotifyType {


    /**
     * 邮件类型
     */
    TYPE_LOG("日志输出"),
    TYPE_MAIL("邮件"),
    TYPE_MSG("短信");

    private final String typeName;

    NotifyType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
