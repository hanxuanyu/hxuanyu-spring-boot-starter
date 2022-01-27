package com.hxuanyu.monitor.config;

import com.hxuanyu.common.util.BeanUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * 定时任务配置类
 *
 * @author hanxuanyu
 * @version 1.0
 */
@SuppressWarnings({"unchecked", "unused"})
@Configuration
@EnableScheduling
public class DefaultSchedulingConfigurer implements SchedulingConfigurer {
    private ScheduledTaskRegistrar taskRegistrar;
    private Set<ScheduledFuture<?>> scheduledFutures = null;
    private final Map<String, ScheduledFuture<?>> taskFutures = new ConcurrentHashMap<>();

    @Resource
    ScheduledExecutorService scheduledExecutorService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        this.taskRegistrar = taskRegistrar;
        taskRegistrar.setScheduler(scheduledExecutorService);
    }

    private Set<ScheduledFuture<?>> getScheduledFutures() {
        if (scheduledFutures == null) {
            try {
                // spring版本不同选用不同字段scheduledFutures
                scheduledFutures = (Set<ScheduledFuture<?>>) BeanUtils.getProperty(taskRegistrar, "scheduledTasks");
            } catch (NoSuchFieldException e) {
                throw new SchedulingException("not found scheduledFutures field.");
            }
        }
        return scheduledFutures;
    }

    /**
     * 添加任务
     *
     * @param taskId      taskId
     * @param triggerTask triggerTask
     */
    public void addTriggerTask(String taskId, TriggerTask triggerTask) {
        if (taskFutures.containsKey(taskId)) {
            throw new SchedulingException("the taskId[" + taskId + "] was added.");
        }
        TaskScheduler scheduler = taskRegistrar.getScheduler();
        if (scheduler == null) {
            throw new SchedulingException("scheduler为空");
        }
        ScheduledFuture<?> future = scheduler.schedule(triggerTask.getRunnable(), triggerTask.getTrigger());
        getScheduledFutures().add(future);
        taskFutures.put(taskId, future);
    }

    /**
     * 取消任务
     *
     * @param taskId taskId
     */
    public void cancelTriggerTask(String taskId) {
        ScheduledFuture<?> future = taskFutures.get(taskId);
        if (future != null) {
            future.cancel(true);
        }
        taskFutures.remove(taskId);
        getScheduledFutures().remove(future);
    }

    /**
     * 重置任务
     *
     * @param taskId      taskId
     * @param triggerTask triggerTask
     */
    public void resetTriggerTask(String taskId, TriggerTask triggerTask) {
        cancelTriggerTask(taskId);
        addTriggerTask(taskId, triggerTask);
    }

    /**
     * 任务编号
     *
     * @return 任务编号列表
     */
    public Set<String> taskIds() {
        return taskFutures.keySet();
    }

    /**
     * 是否存在任务
     *
     * @param taskId taskId
     * @return true: 存在任务；false：不存在任务
     */
    public boolean hasTask(String taskId) {
        return this.taskFutures.containsKey(taskId);
    }

    /**
     * 任务调度是否已经初始化完成
     *
     * @return true: 初始化完成；false：初始化未完成
     */
    public boolean inited() {
        return this.taskRegistrar != null && this.taskRegistrar.getScheduler() != null;
    }
}

