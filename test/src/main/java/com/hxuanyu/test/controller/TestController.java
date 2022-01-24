package com.hxuanyu.test.controller;

import com.hxuanyu.common.message.Msg;
import com.hxuanyu.notify.model.Mail;
import com.hxuanyu.notify.service.MailService;
import com.hxuanyu.notify.service.NotifyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试控制器
 *
 * @author hanxuanyu
 * @version 1.0
 */

@RestController
public class TestController {


    @Resource
    MailService mailService;

    @RequestMapping("/testMail")
    public Msg<Mail> testMail(){
        Mail mail = new Mail();
        mail.setContent("邮件内容");
        mail.setSubject("邮件主题");
        mail.setTo("2252193204@qq.com");

        try {
            mailService.sendMail(mail);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Msg.success("发送邮件成功", mail);
    }
}
