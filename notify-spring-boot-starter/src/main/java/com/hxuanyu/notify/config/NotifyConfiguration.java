package com.hxuanyu.notify.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author hxuanyu
 */
@Configuration
@ComponentScan("com.hxuanyu.notify.service")
public class NotifyConfiguration {
    @Bean(name = "mailExecutorService")
    public ExecutorService mailExecutorService() {
        // 使用 ThreadFactoryBuilder 创建自定义线程名称的 ThreadFactory
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("Mail-pool-%d").build();

        // 创建线程池，其中任务队列需要结合实际情况设置合理的容量
        return new ThreadPoolExecutor(1,
                1,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024),
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }
}
