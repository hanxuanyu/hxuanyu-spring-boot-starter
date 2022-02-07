package com.hxuanyu.starter.test.network;

import com.hxuanyu.common.message.Msg;
import com.hxuanyu.network.service.HttpService;
import com.hxuanyu.test.MainApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = MainApplication.class)
public class NetworkTest {

    @Autowired
    HttpService httpService;

    private final Logger logger = LoggerFactory.getLogger(NetworkTest.class);

    @Test
    @Timeout(3000)
    public void testGet(){
        Msg<String> msg = httpService.doGet("https://baidu.com");
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
