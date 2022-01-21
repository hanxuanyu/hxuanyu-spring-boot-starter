package com.hxuanyu.notify.service;


import com.hxuanyu.notify.model.Mail;

/**
 * 邮件服务
 *
 * @author hanxuanyu
 * @version 1.0
 */
public interface MailService {

    /**
     * 发送简单邮件
     *
     * @param mail 发件人
     * @throws InterruptedException 异常
     */
    void sendMail(Mail mail) throws InterruptedException;
}
