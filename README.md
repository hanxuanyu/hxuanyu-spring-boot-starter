## 项目简介

本项目基于springboot进行开发，实现了一系列的spring-boot-starter，可以作为开发中的工具包进行使用。



### 模块划分

- common-spring-boot-starter：常用的基础类，比如用作消息流转的`Msg`以及一些工具类
- monitor-spring-boot-starter：监控工具包，可以实现定时监控某些数据并在触发条件后实时通知
- network-spring-boot-starter：网络工具，实现了一系列http请求发送方法，引入后即可快速发送http请求
- notify-spring-boot-starter：通知工具，集成了邮件通知、短信通知等功能



## 快速开始

### 引入maven依赖

```xml
<dependency>
    <groupId>com.hxuanyu</groupId>
    <artifactId>hxuanyu-spring-boot-starter-parent</artifactId>
    <version>1.0.4</version>
</dependency>
```

**子模块的依赖详见子模块的Readme**：

- [common-spring-boot-starter](https://git.hxuanyu.com/hxuanyu/hxuanyu-spring-boot-starter/src/branch/master/common-spring-boot-starter)
- [monitor-spring-boot-starter](https://git.hxuanyu.com/hxuanyu/hxuanyu-spring-boot-starter/src/branch/master/monitor-spring-boot-starter/README.md)
- [network-spring-boot-starter](https://git.hxuanyu.com/hxuanyu/hxuanyu-spring-boot-starter/src/branch/master/network-spring-boot-starter)
- [notify-spring-boot-starter](https://git.hxuanyu.com/hxuanyu/hxuanyu-spring-boot-starter/src/branch/master/notify-spring-boot-starter)

