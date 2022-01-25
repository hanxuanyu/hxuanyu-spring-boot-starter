## notify-spring-boot-starter

### 简介

本模块用于对用户进行通知，目前支持邮件通知，后续会加入短信等更多类型

- MAIL：对springboot的mail模块进行了封装，实现了一个邮件发送队列，并支持html作为邮件内容



### 引入

```java
<dependency>
    <groupId>com.hxuanyu</groupId>
    <artifactId>notify-spring-boot-starter</artifactId>
    <version>1.0.4</version>
</dependency>
```



### 使用

#### Mail

- 引入`NotifyService`对象

```java
@Resource
NotifyService notifyService;
```



- 添加Mail配置

```java
notify:
  mail:
    host: your mail host
    protocol: smtp
    default-encoding: UTF-8
    password: your passwd
    username: your account
    port: 587
    properties:
      mail:
        debug: false
      stmp:
        socketFactory:
          class: javax.net.ssl.SSLSocketFactory
```



- 调用相关方法

```java
notifyService.notify(new Mail("2252193204@qq.com", "test subject", "test success"), NotifyType.TYPE_MAIL);
```



#### 短信和日志

- 引入`NotifyService`对象

```java
@Resource
NotifyService notifyService;
```

- 调用相关方法

```java
notifyService.notify("短信通知方式", NotifyType.TYPE_MSG);
notifyService.notify("日志输出方式", NotifyType.TYPE_LOG);
```



#### 自定义

- 引入`NotifyService`对象

```java
@Resource
NotifyService notifyService;
```

- 调用方法

```java
notifyService.notify(new NotifyService.CustomNotify() {
    @Override
    public void onNotify() {
        // do some things
        
    }
});
```

