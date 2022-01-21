package com.hxuanyu.monitor.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 监控项注解，标识在监控项实现类上，带有该注解的类会被扫描并根据配置的时间进行检查
 *
 * @author hxuanyu
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MonitorItem {
    String name() default "";
    String cron();
}
