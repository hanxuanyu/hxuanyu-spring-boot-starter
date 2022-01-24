package com.hxuanyu.notify.service.impl;


import com.hxuanyu.notify.common.MailQueue;
import com.hxuanyu.notify.config.MailProperties;
import com.hxuanyu.notify.model.Mail;
import com.hxuanyu.notify.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

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
    @Resource
    MailProperties mailProperties;
    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
    private static boolean isRunning = true;
    ExecutorService executor;
    @Resource
    private JavaMailSender javaMailSender;





    @PostConstruct
    public void startThread() {
        executor.submit(new PollMail());
    }

    class PollMail implements Runnable {
        @Override
        public void run() {
            while (isRunning) {
                try {
                    logger.info("剩余邮件总数:{}", MailQueue.getMailQueue().size());
                    Mail mail = MailQueue.getMailQueue().consume();
                    if (mail != null) {
                        //可以设置延时 以及重复校验等等操作
                        sendMailSync(mail);
                        Thread.sleep(mailProperties.getInterval());
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


    private void sendMailSync(Mail mail) {
        if (mailProperties == null){
            logger.error("未配置邮件信息，请在配置文件中添加配置信息后再发送邮件");
            return;
        }
        String from = mail.getFrom();
        if (from == null) {
            from = mailProperties.getUsername();
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
            String content = mail.getContent();
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


    @Autowired
    public void setExecutor(@Qualifier("mailExecutorService") ExecutorService executor) {
        this.executor = executor;
    }

}