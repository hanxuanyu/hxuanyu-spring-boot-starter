## network-spring-boot-starter

### 简介

本模块对`HttpClient`进行了封装，实现了GET、POST、PUT、DELETE等Http请求的同步和异步方法



### 引入

```java

<dependency>
    <groupId>com.hxuanyu</groupId>
    <artifactId>network-spring-boot-starter</artifactId>
    <version>1</version>
</dependency>
```



### 使用

- 引入`HttpService`对象

```java
@Resource
HttpService httpService;
```



- 调用相关方法

```java
Msg<String> msg = httpService.doGet("https://baidu.com");
if (msg.isSuccess()) {
    logger.info(msg.toString());
}
```



- 本模块依赖了common-spring-boot-starter模块，引入本模块后会自动引入common模块下的相关类，同时`HttpService`的方法返回值使用了common模块下的`Msg`统一封装