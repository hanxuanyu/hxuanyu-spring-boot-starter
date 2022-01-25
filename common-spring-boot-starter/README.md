## common-spring-boot-starter

### 简介

本模块封装了常用的工具类，对外提供基础的功能，当前包含：

- Msg：统一消息对象



### 引入

```java

<dependency>
    <groupId>com.hxuanyu</groupId>
    <artifactId>common-spring-boot-starter</artifactId>
    <version>1.0.4</version>
</dependency>
```



### 功能

#### Msg

用于规范方法间调用返回的结果，即方法无论调用成功还是失败，都以Msg对象作为返回值，实际的返回值封装在msg中，调用者可根据Msg的状态码进行状态判断，成功则从Msg对象中取值，失败则做相应的失败处理，避免了直接调用时可能出现的空指针问题。



使用方式：

```java
public void test() {
    Msg<String> msg = doSomeThing("args");
    if (msg.isSuccess()) {
        // do some things
    } else if (msg.isFailed()) {
        // do some things
    }
}

private Msg<String> doSomeThing(String args) {
    if (args != null) {
        return Msg.success("your success msg", "your data");
    } else {
        return Msg.failed("your failed msg");
    }
}
```



- 如果方法调用成功，则在返回值中传入成功消息以及可选的返回对象，该对象为泛型，可以在声明方法时指定
- 如果方法调用失败，则在返回值中传入失败原因，**注意，失败时不可设置data字段，只能传入失败消息**
- 调用者可以根据错误码或者直接调用`msg.isSuccess()`方法判断是否调用成功，并对结果进行相应处理
- 在链式调用时，位于中间的方法不建议直接将上游请求到的msg结果传递给下游，应重新创建新的msg对象
