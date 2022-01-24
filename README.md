## 项目简介

本项目基于springboot进行开发，实现了一系列的spring-boot-starter，可以作为开发中的工具包进行使用。



### 模块划分

- common-spring-boot-starter：常用的基础类，比如用作消息流转的`Msg`以及一些工具类
- monitor-spring-boot-starter：监控工具包，可以实现定时监控某些数据并在触发条件后实时通知
- network-spring-boot-starter：网络工具，实现了一系列http请求发送方法，引入后即可快速发送http请求
- notify-spring-boot-starter：通知工具，集成了邮件通知、短信通知等功能



## 快速开始

### 1. 引入maven依赖

本项目已经上传到中央仓库，使用时在pom文件中添加如下依赖即可：

- network-spring-boot-starter

```xml
<!--network-spring-boot-starter-->
<dependency>
    <groupId>com.hxuanyu</groupId>
    <artifactId>network-spring-boot-starter</artifactId>
    <version>1.0.1</version>
</dependency>
```

- notify-spring-boot-starter

```xml
<!--notify-spring-boot-starter-->
<dependency>
    <groupId>com.hxuanyu</groupId>
    <artifactId>notify-spring-boot-starter</artifactId>
    <version>1.0.1</version>
</dependency>
```

- monitor-spring-boot-starter

```xml
<!--monitor-spring-boot-starter-->
<dependency>
    <groupId>com.hxuanyu</groupId>
    <artifactId>monitor-spring-boot-starter</artifactId>
    <version>1.0.1</version>
</dependency>
```

- monitor-spring-boot-starter

```xml
<!--monitor-spring-boot-starter-->
<dependency>
    <groupId>com.hxuanyu</groupId>
    <artifactId>monitor-spring-boot-starter</artifactId>
    <version>1.0.1</version>
</dependency>
```