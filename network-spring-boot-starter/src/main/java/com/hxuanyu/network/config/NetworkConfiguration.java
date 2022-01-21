package com.hxuanyu.network.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.hxuanyu.network.service.HttpService;
import com.hxuanyu.network.service.impl.HttpServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * 网络模块配置
 *
 * @author hanxuanyu
 * @version 1.0
 */
@Configuration
@ComponentScan("com.hxuanyu.network.service")
public class NetworkConfiguration {
    @Bean(name = "networkExecutorService")
    public ExecutorService networkExecutorService() {
        // 使用 ThreadFactoryBuilder 创建自定义线程名称的 ThreadFactory
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("Default-pool-%d").build();
        return new ThreadPoolExecutor(2,
                10,
                1, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(5, true),
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }
}
