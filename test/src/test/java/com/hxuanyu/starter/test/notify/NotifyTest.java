package com.hxuanyu.starter.test.notify;

import com.hxuanyu.notify.enums.NotifyType;
import com.hxuanyu.notify.model.Mail;
import com.hxuanyu.notify.service.NotifyService;
import com.hxuanyu.test.MainApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static java.lang.Thread.sleep;

@SpringBootTest(classes = MainApplication.class)
public class NotifyTest {

    @Autowired
    NotifyService notifyService;

    @Test
    public void testNotify() throws InterruptedException {
        notifyService.notify(new Mail("2252193204@qq.com", "test subject", "test success"), NotifyType.TYPE_MAIL);
        sleep(2000);
        notifyService.notify("短信通知方式", NotifyType.TYPE_MSG);
        notifyService.notify("日志输出方式", NotifyType.TYPE_LOG);
        notifyService.notify(() -> {
            // do some things

        });
    }

}
