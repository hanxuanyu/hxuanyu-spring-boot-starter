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
    MAIL_TYPE("邮件"),
    SMS_TYPE("短信");

    private final String typeName;

    NotifyType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
