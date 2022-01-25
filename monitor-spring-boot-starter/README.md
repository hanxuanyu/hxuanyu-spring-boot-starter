## monitor-spring-boot-starter

### 简介

- 本模块实现了一个定时监控并推送通知的服务，适用于处理一些定时监控并实时通知的场景，举个栗子，可以用来监控火车票，当检测到有票之后触发通知，通过配置好的通知方式告知我们，目前内置了邮件通知、日志输出方式，以及自定义通知。



### 引入

```java
<dependency>
    <groupId>com.hxuanyu</groupId>
    <artifactId>monitor-spring-boot-starter</artifactId>
    <version>1.0.4</version>
</dependency>
```



### 使用

- 创建监控项

```java
@MonitorItem(cron = "0/20 * * * * *")
public class TestMonitorItem extends BaseMonitorItem {

    private final Logger logger = LoggerFactory.getLogger(TestMonitorItem.class);

    @Override
    public CheckResult check() {

        double random = Math.random();
        int result = (int) (random * NotifyType.values().length);
        logger.info("随机索引值：{}", result);
        if (NotifyType.values().length > 0) {
            NotifyType notifyType = NotifyType.values()[result];
            switch (notifyType) {
                case TYPE_LOG:
                    return CheckResult.triggered("日志输出", NotifyType.TYPE_LOG);
                case TYPE_CUSTOM:
                    return CheckResult.triggered(new NotifyService.CustomNotify() {
                        @Override
                        public void onNotify() {
                            logger.info("自定义通知");
                        }
                    });
                case TYPE_MAIL:
                    return CheckResult.triggered(new Mail("2252193204@qq.com", "测试邮件主题", "测试邮件内容"), NotifyType.TYPE_MAIL);
                case TYPE_MSG:
                    return CheckResult.triggered("短信通知", NotifyType.TYPE_MSG);
                default:
                    return CheckResult.triggered("默认通知", null);
            }
        }

        return CheckResult.nonTriggered();
    }
}

```

> MonitorManager会在启动时扫描标注了`@MonitorItem`的类，并创建对应的实例，定时执行类中实现的`check()`方法，当方法返回`CheckResult`的`triggered`字段为`true`时，会自动执行`CheckResult`中传入的通知。



- 新增任务

```java
Msg<String> msg = monitorItemBeanManager.addMonitorTask(new BaseMonitorItem("CustomBean", "0/10 * * * * *") {
            @Override
            public CheckResult check() {
                return CheckResult.triggered("动态新增通知", NotifyType.TYPE_LOG);
            }
        });
```



- 定时任务管理器：用于修改监控间隔、删除任务或者查看当前所有任务

```java
@Resource
MonitorItemBeanManager monitorItemBeanManager;
```



- 查看任务列表

```java
Map<String, BaseMonitorItem> monitorItemMap = monitorItemBeanManager.getMonitorItemMap();
```



- 设置任务监控频率

```java
Msg<String> msg = monitorItemBeanManager.setMonitorTaskCron(taskId, taskCron);
```



- 移除定时任务

```java
Msg<String> msg = monitorItemBeanManager.deleteMonitorTask(taskId);
```

