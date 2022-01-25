package com.hxuanyu.notify.service;

import com.hxuanyu.notify.enums.NotifyType;

/**
 * 通知服务
 *
 * @author hanxuanyu
 * @version 1.0
 */
public interface NotifyService {
    /**
     * 向用户发送通知
     *
     * @param content    通知内容
     * @param notifyType 通知类型
     */
    void notify(Object content, NotifyType notifyType);

    /**
     * 自定义通知
     *
     * @param customNotify 自定义通知接口，需手动实现
     */
    void notify(CustomNotify customNotify);

    interface CustomNotify {
        /**
         * 通知
         */
        void onNotify();
    }
}
