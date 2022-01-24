package com.hxuanyu.starter.test.common;


import com.hxuanyu.common.message.Msg;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsgTest {

    private final Logger logger = LoggerFactory.getLogger(MsgTest.class);

    @Test
    public void test() {
        Msg<String> msg = Msg.success("测试内容", "测试体");
        logger.info(msg.toString());
        assert msg.isSuccess();
        msg = Msg.failed("失败消息");
        logger.info(msg.toString());
        assert msg.isFailed();
    }
}
