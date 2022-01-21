package com.hxuanyu.notify.service.impl;


import com.hxuanyu.notify.common.MailQueue;
import com.hxuanyu.notify.model.Mail;
import com.hxuanyu.notify.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.ExecutorService;

/**
 * @author 22521
 */
@Service
public class MailServiceImpl implements MailService {
    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
    private static boolean isRunning = true;
    ExecutorService executor;

    private static JavaMailSender javaMailSender;
    private static TemplateEngine templateEngine;


    private static String defaultFrom;




    @PostConstruct
    public void startThread() {
        executor.submit(new PollMail());
    }

    static class PollMail implements Runnable {
        @Override
        public void run() {
            while (isRunning) {
                try {
                    logger.info("剩余邮件总数:{}", MailQueue.getMailQueue().size());
                    Mail mail = MailQueue.getMailQueue().consume();
                    if (mail != null) {
                        //可以设置延时 以及重复校验等等操作
                        sendMailSync(mail);
                        Thread.sleep(10000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void sendMail(Mail mail) throws InterruptedException {
        MailQueue.getMailQueue().produce(mail);
    }


    private static void sendMailSync(Mail mail) {
        String from = mail.getFrom();
        if (from == null) {
            from = defaultFrom;
            logger.info("未传入发件人，从配置中读取：{}", from);
        }
        MimeMessage mimeMessage;

        try {
            mimeMessage = javaMailSender.createMimeMessage();
            // true 表示多部分，可添加内联资源
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            // 设置邮件信息
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(mail.getTo());
            mimeMessageHelper.setSubject(mail.getSubject());
            // 利用 Thymeleaf 引擎渲染 HTML
            Context context = new Context();
            // 设置注入的变量
            context.setVariable("templates/mail", mail);
            // 模板设置为 "mail"
            String content = templateEngine.process("templates/mail/mail", context);
            // 设置邮件内容
            // true 表示开启 html
            mimeMessageHelper.setText(content, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("发送邮件出错：" + e.getMessage() + e.getCause());
        }
    }

    public static void setIsRunning(boolean isRunning) {
        MailServiceImpl.isRunning = isRunning;
    }

    @PreDestroy
    public void stopThread() {
        logger.info("destroy");
    }


    @Resource
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        MailServiceImpl.javaMailSender = javaMailSender;
    }

    @Autowired
    public void setExecutor(@Qualifier("mailExecutorService") ExecutorService executor) {
        this.executor = executor;
    }


    @Autowired
    public void setTemplateEngine(TemplateEngine templateEngine) {
        MailServiceImpl.templateEngine = templateEngine;
    }

    @Value("${spring.mail.username}")
    public void setDefaultFrom(String defaultFrom) {
        MailServiceImpl.defaultFrom = defaultFrom;
    }
}