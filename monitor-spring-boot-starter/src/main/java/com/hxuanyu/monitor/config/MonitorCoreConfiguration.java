package com.hxuanyu.monitor.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/**
 * MonitorCore配置类
 *
 * @author hanxuanyu
 * @version 1.0
 */

@Configuration
@ComponentScan("com.hxuanyu.monitor")
public class MonitorCoreConfiguration {
    @Bean("scheduledThreadPoolExecutor")
    public ScheduledExecutorService scheduledThreadPoolExecutor() {
        // 使用 ThreadFactoryBuilder 创建自定义线程名称的 ThreadFactory
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("scheduled-%d").build();
        return new ScheduledThreadPoolExecutor(10, namedThreadFactory);
    }
}
