package com.hxuanyu.network.service;

import com.hxuanyu.common.message.Msg;
import org.apache.http.HttpResponse;

import java.util.Map;

/**
 * 网络请求服务，用于发起常规的Http请求，包括同步异步的方式，请求任务由线程池进行管理。
 *
 * @author hanxuanyu
 * @version 1.0
 */
@SuppressWarnings("unused")
public interface HttpService {

    /**
     * 获取原始HttpEntity对象
     *
     * @param url 请求地址
     * @return 统一结果报文
     */
    Msg<HttpResponse> doGetWithResponse(String url);



    /**
     * 发送get请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @return 统一请求报文
     */
    Msg<String> doGet(String url);


    /**
     * 异步Get请求
     *
     * @param url      请求地址
     * @param listener 异步请求结果监听
     */
    void doGetSync(String url, NetWorkListener<String> listener);

    /**
     * 异步Get请求
     *
     * @param url      请求地址
     * @param listener 异步请求结果监听
     */
    void doGetSyncWithResponse(String url, NetWorkListener<HttpResponse> listener);

    /**
     * 发送get请求；带请求参数
     *
     * @param url    请求地址
     * @param params 请求参数集合
     * @return 全局报文
     */
    Msg<String> doGet(String url, Map<String, String> params);


    /**
     * 发送get请求；带请求参数
     *
     * @param url    请求地址
     * @param params 请求参数集合
     * @return 全局报文
     */
    Msg<HttpResponse> doGetWithResponse(String url, Map<String, String> params);

    /**
     * 异步Get请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param listener 异步请求结果监听
     */
    void doGetSync(String url, Map<String, String> params, NetWorkListener<String> listener);

    /**
     * 异步Get请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param listener 异步请求结果监听
     */
    void doGetSyncWithResponse(String url, Map<String, String> params, NetWorkListener<HttpResponse> listener);

    /**
     * 发送get请求；带请求头和请求参数
     *
     * @param url     请求地址
     * @param headers 请求头集合
     * @param params  请求参数集合
     * @return 全局报文
     */
    Msg<String> doGet(String url, Map<String, String> headers, Map<String, String> params);

    /**
     * 发送get请求；带请求头和请求参数
     *
     * @param url     请求地址
     * @param headers 请求头集合
     * @param params  请求参数集合
     * @return 全局报文
     */
    Msg<HttpResponse> doGetWithResponse(String url, Map<String, String> headers, Map<String, String> params);

    /**
     * 异步Get请求
     *
     * @param url      请求地址
     * @param headers  请求头
     * @param params   请求体
     * @param listener 异步结果监听器
     */
    void doGetSync(String url, Map<String, String> headers, Map<String, String> params, NetWorkListener<String> listener);

    /**
     * 异步Get请求
     *
     * @param url      请求地址
     * @param headers  请求头
     * @param params   请求体
     * @param listener 异步结果监听器
     */
    void doGetSyncWithResponse(String url, Map<String, String> headers, Map<String, String> params, NetWorkListener<HttpResponse> listener);

    /**
     * 发送post请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @return 统一报文
     */
    Msg<String> doPost(String url);

    /**
     * 发送post请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @return 统一报文
     */
    Msg<HttpResponse> doPostWithResponse(String url);


    /**
     * 异步Post请求
     *
     * @param url      请求地址
     * @param listener 请求参数
     */
    void doPostSync(String url, NetWorkListener<String> listener);

    /**
     * 异步Post请求
     *
     * @param url      请求地址
     * @param listener 请求参数
     */
    void doPostSyncWithResponse(String url, NetWorkListener<HttpResponse> listener);

    /**
     * 同步Post请求
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 统一消息体
     */
    Msg<String> doPost(String url, Map<String, String> params);


    /**
     * 同步Post请求
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 统一消息体
     */
    Msg<HttpResponse> doPostWithResponse(String url, Map<String, String> params);

    /**
     * 异步Post请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param listener 异步请求结果监听
     */
    void doPostSync(String url, Map<String, String> params, NetWorkListener<String> listener);

    /**
     * 异步Post请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param listener 异步请求结果监听
     */
    void doPostSyncWithResponse(String url, Map<String, String> params, NetWorkListener<HttpResponse> listener);

    /**
     * 发送post请求；带请求头和请求参数
     *
     * @param url     请求地址
     * @param headers 请求头集合
     * @param params  请求参数集合
     * @return 统一返回报文
     */
    Msg<String> doPost(String url, Map<String, String> headers, Map<String, String> params);

    /**
     * 发送post请求；带请求头和请求参数
     *
     * @param url     请求地址
     * @param headers 请求头集合
     * @param params  请求参数集合
     * @return 统一返回报文
     */
    Msg<HttpResponse> doPostWithResponse(String url, Map<String, String> headers, Map<String, String> params);


    /**
     * 异步Post请求
     *
     * @param url      请求地址
     * @param headers  请求头
     * @param params   请求参数
     * @param listener 异步请求结果监听
     */
    void doPostSync(String url, Map<String, String> headers, Map<String, String> params, NetWorkListener<String> listener);

    /**
     * 异步Post请求
     *
     * @param url      请求地址
     * @param headers  请求头
     * @param params   请求参数
     * @param listener 异步请求结果监听
     */
    void doPostSyncWithResponse(String url, Map<String, String> headers, Map<String, String> params, NetWorkListener<HttpResponse> listener);

    /**
     * 发送put请求；不带请求参数
     *
     * @param url 请求地址
     * @return 统一消息返回报文
     */
    Msg<String> doPut(String url);


    /**
     * 发送put请求；不带请求参数
     *
     * @param url 请求地址
     * @return 统一消息返回报文
     */
    Msg<HttpResponse> doPutWithResponse(String url);

    /**
     * 发送put请求；带请求参数
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 统一消息报文
     */
    Msg<String> doPut(String url, Map<String, String> params);

    /**
     * 发送put请求；带请求参数
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 统一消息报文
     */
    Msg<HttpResponse> doPutWithResponse(String url, Map<String, String> params);

    /**
     * 发送delete请求；不带请求参数
     *
     * @param url 请求地址
     * @return 统一返回报文
     */
    Msg<String> doDelete(String url);

    /**
     * 发送delete请求；不带请求参数
     *
     * @param url 请求地址
     * @return 统一返回报文
     */
    Msg<HttpResponse> doDeleteWithResponse(String url);

    /**
     * 发送delete请求；带请求参数
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 统一返回报文
     */
    Msg<String> doDelete(String url, Map<String, String> params);


    /**
     * 发送delete请求；带请求参数
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 统一返回报文
     */
    Msg<HttpResponse> doDeleteWithResponse(String url, Map<String, String> params);

    interface NetWorkListener<T> {
        /**
         * 网络请求成功后调用
         *
         * @param msg 统一返回消息
         */
        void onSuccess(Msg<T> msg);

        /**
         * 网络请求失败后调用
         *
         * @param msg 统一返回消息
         */
        void onFailed(Msg<T> msg);
    }
}
