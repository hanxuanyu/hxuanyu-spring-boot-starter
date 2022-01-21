package com.hxuanyu.notify.service;

import com.hxuanyu.notify.enums.NotifyType;
import org.springframework.stereotype.Service;

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
}
