package com.hxuanyu.test.controller;

import com.hxuanyu.common.message.Msg;
import com.hxuanyu.monitor.manager.MonitorItemBeanManager;
import com.hxuanyu.notify.model.Mail;
import com.hxuanyu.notify.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 测试控制器
 *
 * @author hanxuanyu
 * @version 1.0
 */

@RestController
public class NetworkTestController {

    private final Logger logger = LoggerFactory.getLogger(NetworkTestController.class);

    @ResponseBody
    @GetMapping("/")
    public Msg<String> testGet() {
        logger.info("收到GET请求");
        return Msg.success("GET 测试");
    }

    @ResponseBody
    @PostMapping("/")
    public Msg<String> testPost() {
        logger.info("收到POST请求");
        return Msg.success("POST 测试");
    }

    @ResponseBody
    @PutMapping("/")
    public Msg<String> testPut() {
        logger.info("收到PUT请求");
        return Msg.success("PUT 测试");
    }

    @ResponseBody
    @DeleteMapping("/")
    public Msg<String> testDelete() {
        logger.info("收到DELETE请求");
        return Msg.success("DELETE 测试");
    }
}
