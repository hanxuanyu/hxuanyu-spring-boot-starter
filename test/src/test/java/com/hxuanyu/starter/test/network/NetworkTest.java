package com.hxuanyu.starter.test.network;

import com.hxuanyu.common.message.Msg;
import com.hxuanyu.network.service.HttpService;
import com.hxuanyu.test.MainApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

@SpringBootTest(classes = MainApplication.class)
public class NetworkTest {

    @Autowired
    HttpService httpService;

    private final Logger logger = LoggerFactory.getLogger(NetworkTest.class);
    private static final String STATION_NAME_URL = "https://kyfw.12306.cn/otn/resources/js/framework/station_name.js?station_version=1.9226";


    @Test
    @Timeout(3000)
    public void testGet() throws InterruptedException {
        Map<String, String> header = new HashMap<>(1);
//        header.put("Accept", "*/*");
        header.put("Connection", "keep-alive");
//        header.put("User-Agent", "PostmanRuntime/7.29.0");
//        header.put("Accept-Encoding", "gzip, deflate, br");
        Msg<String> msg = httpService.doGet(STATION_NAME_URL, header, null);
        logger.info("GET测试：{}", msg);
        assert msg.isSuccess();
    }

    @Test
    @Timeout(3000)
    public void testPost() {
        Msg<String> msg = httpService.doPost("https://baidu.com");
        logger.info("POST测试：{}", msg);
        assert msg.isSuccess();
    }

    @Test
    @Timeout(3000)
    public void testDelete() {
        Msg<String> msg = httpService.doDelete("https://baidu.com");
        logger.info("DELETE测试：{}", msg);
        assert msg.isSuccess();
    }

    @Test
    @Timeout(3000)
    public void testPut() {
        Msg<String> msg = httpService.doPut("https://baidu.com");
        logger.info("PUT测试：{}", msg);
        assert msg.isSuccess();
    }
}
