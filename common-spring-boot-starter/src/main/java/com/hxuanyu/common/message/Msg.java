package com.hxuanyu.common.message;

/**
 * 用户端统一返回报文实体类
 *
 * @author hanxuanyu
 * @version 1.0
 */
@SuppressWarnings("unused")
public class Msg<T> {
    /**
     * 成功状态码
     */
    public static final Integer MSG_CODE_SUCCESS = 200;
    /**
     * 失败状态码
     */
    public static final Integer MSG_CODE_FAILED = -1;
    /**
     * 返回码，用于标识是否成功
     */
    private Integer code;
    /**
     * 返回信息，用于描述调用或请求状态，如果成功则添加成功的信息，若失败须写明失败原因
     */
    private String msg;
    /**
     * 调用或请求的实际结果，如果请求失败，该值应为空
     */
    private T data;

    /**
     * 构造器
     */
    private Msg() {
    }

    /**
     * 全参构造器
     *
     * @param code 状态码
     * @param msg  消息内容
     * @param data 消息体
     */
    private Msg(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 构造成功消息，如果本次返回需要返回数据，则调用该方法并将数据传入
     *
     * @param <E>  消息体类型
     * @param msg  消息内容
     * @param data 消息体
     * @return 统一消息报文
     */
    public static <E> Msg<E> success(String msg, E data) {
        return new Msg<>(Msg.MSG_CODE_SUCCESS, msg, data);
    }

    /**
     * 构造成功消息，本方法适用于不需要数据返回的情况
     *
     * @param <E> 消息体类型
     * @param msg 消息内容
     * @return 统一消息报文
     */
    public static <E> Msg<E> success(String msg) {
        return success(msg, null);
    }

    /**
     * 构造简单的成功消息，使用默认的msg
     *
     * @param <E> 消息体类型
     * @return 统一消息报文
     */
    public static <E> Msg<E> success() {
        return success("成功");
    }

    /**
     * 构造失败消息，由于请求失败，调用方无法获得正确的数据，因此该类型消息的数据区为null，但是msg中必须注明失败原因，
     * 失败原因由被调用方分析并设置
     *
     * @param msg 消息内容
     * @param <T> 消息体类型
     * @return 失败消息
     */
    public static <T> Msg<T> failed(String msg) {
        return new Msg<>(Msg.MSG_CODE_FAILED, msg, null);
    }

    /**
     * 构造带有默认msg字段的失败消息
     *
     * @param <T> 消息体的类型
     * @return 失败报文
     */
    public static <T> Msg<T> failed() {
        return failed("失败");
    }

    /**
     * 返回是否成功，可直接用于if判断
     *
     * @return true：成功，false：失败
     */
    public boolean isSuccess() {
        return Msg.MSG_CODE_SUCCESS.equals(this.code);
    }

    /**
     * 返回是否失败，可直接用于if判断
     *
     * @return true：失败，false：失败
     */
    public boolean isFailed() {
        return Msg.MSG_CODE_FAILED.equals(this.code);
    }


    /**
     * 获取状态码
     *
     * @return 状态码
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取消息内容
     *
     * @return 消息内容
     */
    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
