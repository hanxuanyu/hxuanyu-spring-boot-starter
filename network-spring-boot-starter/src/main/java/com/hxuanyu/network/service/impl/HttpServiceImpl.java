package com.hxuanyu.network.service.impl;

import com.hxuanyu.common.message.Msg;
import com.hxuanyu.network.service.HttpService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ExecutorService;


/**
 * @author hxuanyu
 */
@SuppressWarnings("unused")
@Service
public class HttpServiceImpl implements HttpService {
    private final Logger logger = LoggerFactory.getLogger(HttpServiceImpl.class);
    private ExecutorService executorService;

    @Autowired
    public void setExecutorService(@Qualifier("networkExecutorService") ExecutorService executorService) {
        this.executorService = executorService;
    }

    /**
     * 编码格式。发送编码格式统一用UTF-8
     */
    private static final String ENCODING = "UTF-8";

    /**
     * 设置连接超时时间，单位毫秒。
     */
    private static final int CONNECT_TIMEOUT = 10000;

    /**
     * 请求获取数据的超时时间(即响应时间)，单位毫秒。
     */
    private static final int SOCKET_TIMEOUT = 10000;


    @Override
    public Msg<HttpEntity> doGetWithEntity(String url) {

        return doGetWithEntity(url, null, null);
    }

    @Override
    public Msg<String> doGet(String url) {
        return doGet(url, null, null);
    }

    @Override
    public void doGetSync(String url, NetWorkListener<String> listener) {
        doGetSync(url, null, listener);
    }

    @Override
    public void doGetSyncWithEntity(String url, NetWorkListener<HttpEntity> listener) {
        doGetSyncWithEntity(url, null, listener);
    }


    @Override
    public Msg<String> doGet(String url, Map<String, String> params) {
        return doGet(url, null, params);
    }

    @Override
    public Msg<HttpEntity> doGetWithEntity(String url, Map<String, String> params) {
        return doGetWithEntity(url, null, params);
    }

    @Override
    public void doGetSync(String url, Map<String, String> params, NetWorkListener<String> listener) {
        doGetSync(url, null, params, listener);
    }

    @Override
    public void doGetSyncWithEntity(String url, Map<String, String> params, NetWorkListener<HttpEntity> listener) {
        doGetSyncWithEntity(url, null, params, listener);
    }


    @Override
    public Msg<String> doGet(String url, Map<String, String> headers, Map<String, String> params) {
        Msg<HttpEntity> httpEntityMsg = doGetWithEntity(url, headers, params);
        return getStringMsg(httpEntityMsg);
    }

    @Override
    public Msg<HttpEntity> doGetWithEntity(String url, Map<String, String> headers, Map<String, String> params) {
        // 创建httpClient对象
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 创建访问的地址
            URIBuilder uriBuilder = new URIBuilder(url);
            if (params != null) {
                Set<Map.Entry<String, String>> entrySet = params.entrySet();
                for (Map.Entry<String, String> entry : entrySet) {
                    uriBuilder.setParameter(entry.getKey(), entry.getValue());
                }
            }

            // 创建http对象
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            /*
             * setConnectTimeout：设置连接超时时间，单位毫秒。
             * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
             * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
             * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
             */
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
            httpGet.setConfig(requestConfig);

            // 设置请求头
            packageHeader(headers, httpGet);

            // 创建httpResponse对象
            CloseableHttpResponse httpResponse = null;
            // 执行请求并获得响应结果
            return getHttpClientResult(httpClient, httpGet);
        } catch (Exception e) {
            logger.error("请求过程出现异常: {}", e.getMessage());
            return Msg.failed("网络请求发生异常：" + e.getMessage());
        }
    }


    @Override
    public void doGetSync(String url, Map<String, String> headers, Map<String, String> params, NetWorkListener<String> listener) {
        executorService.execute(() -> {
            try {
                Msg<String> msg = doGet(url, headers, params);
                logger.debug("异步请求结果：{}", msg);
                if (msg.getCode().equals(Msg.MSG_CODE_SUCCESS)) {
                    listener.onSuccess(msg);
                } else if (msg.getCode().equals(Msg.MSG_CODE_FAILED)) {
                    listener.onFailed(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
                listener.onFailed(Msg.failed("网络请求时发生异常"));
            }
        });
    }

    @Override
    public void doGetSyncWithEntity(String url, Map<String, String> headers, Map<String, String> params, NetWorkListener<HttpEntity> listener) {
        executorService.execute(() -> {
            try {
                Msg<HttpEntity> msg = doGetWithEntity(url, headers, params);
                logger.debug("异步请求结果：{}", msg);
                if (msg.getCode().equals(Msg.MSG_CODE_SUCCESS)) {
                    listener.onSuccess(msg);
                } else if (msg.getCode().equals(Msg.MSG_CODE_FAILED)) {
                    listener.onFailed(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
                listener.onFailed(Msg.failed("网络请求时发生异常"));
            }
        });
    }

    @Override
    public Msg<String> doPost(String url) {
        return doPost(url, null, null);
    }

    @Override
    public Msg<HttpEntity> doPostWithEntity(String url) {
        return doPostWithEntity(url, null, null);
    }

    @Override
    public void doPostSync(String url, NetWorkListener<String> listener) {
        doPostSync(url, null, listener);
    }

    @Override
    public void doPostSyncWithEntity(String url, NetWorkListener<HttpEntity> listener) {
        doPostSyncWithEntity(url, null, listener);
    }


    @Override
    public Msg<String> doPost(String url, Map<String, String> params) {
        return doPost(url, null, params);
    }

    @Override
    public Msg<HttpEntity> doPostWithEntity(String url, Map<String, String> params) {
        return doPostWithEntity(url, null, params);
    }

    @Override
    public void doPostSync(String url, Map<String, String> params, NetWorkListener<String> listener) {
        doPostSync(url, null, params, listener);
    }

    @Override
    public void doPostSyncWithEntity(String url, Map<String, String> params, NetWorkListener<HttpEntity> listener) {
        doPostSyncWithEntity(url, null, params, listener);
    }

    @Override
    public Msg<String> doPost(String url, Map<String, String> headers, Map<String, String> params) {
        Msg<HttpEntity> httpEntityMsg = doPostWithEntity(url, headers, params);
        return getStringMsg(httpEntityMsg);
    }

    @Override
    public Msg<HttpEntity> doPostWithEntity(String url, Map<String, String> headers, Map<String, String> params) {
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建http对象
        HttpPost httpPost = new HttpPost(url);
        /*
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPost.setConfig(requestConfig);
        // 设置请求头
//        httpPost.setHeader("Cookie", "");
//        httpPost.setHeader("Connection", "keep-alive");
//        httpPost.setHeader("Accept", "application/json");
//        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
//        httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
//        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        packageHeader(headers, httpPost);

        // 封装请求参数
        try {
            packageParam(params, httpPost);
            // 执行请求并获得响应结果
            return getHttpClientResult(httpClient, httpPost);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("请求过程出现异常: {}", e.getMessage());
            return Msg.failed("请求过程出现异常: " + e.getMessage());
        }
    }

    @Override
    public void doPostSync(String url, Map<String, String> headers, Map<String, String> params, NetWorkListener<String> listener) {
        executorService.execute(() -> {
            try {
                Msg<String> msg = doPost(url, headers, params);
                if (msg.getCode().equals(Msg.MSG_CODE_SUCCESS)) {
                    listener.onSuccess(msg);
                } else if (msg.getCode().equals(Msg.MSG_CODE_FAILED)) {
                    listener.onFailed(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
                listener.onFailed(Msg.failed("网络请求时发生异常"));
            }
        });
    }

    @Override
    public void doPostSyncWithEntity(String url, Map<String, String> headers, Map<String, String> params, NetWorkListener<HttpEntity> listener) {
        executorService.execute(() -> {
            try {
                Msg<HttpEntity> msg = doPostWithEntity(url, headers, params);
                if (msg.getCode().equals(Msg.MSG_CODE_SUCCESS)) {
                    listener.onSuccess(msg);
                } else if (msg.getCode().equals(Msg.MSG_CODE_FAILED)) {
                    listener.onFailed(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
                listener.onFailed(Msg.failed("网络请求时发生异常"));
            }
        });
    }


    @Override
    public Msg<String> doPut(String url) {
        return doPut(url, null);
    }

    @Override
    public Msg<HttpEntity> doPutWithEntity(String url) {
        return doPutWithEntity(url, null);
    }

    @Override
    public Msg<String> doPut(String url, Map<String, String> params) {
        Msg<HttpEntity> httpEntityMsg = doPutWithEntity(url, params);
        return getStringMsg(httpEntityMsg);
    }

    @Override
    public Msg<HttpEntity> doPutWithEntity(String url, Map<String, String> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPut.setConfig(requestConfig);

        CloseableHttpResponse httpResponse = null;
        try {
            packageParam(params, httpPut);

            return getHttpClientResult(httpClient, httpPut);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("请求过程出现异常: {}", e.getMessage());
            return Msg.failed("网络请求时发生异常: " + e.getMessage());
        }
    }

    private String parseEntityToString(HttpEntity httpEntity) throws IOException {
        return EntityUtils.toString(httpEntity, ENCODING);
    }

    private Msg<String> getStringMsg(Msg<HttpEntity> httpEntityMsg) {
        if (Msg.MSG_CODE_SUCCESS.equals(httpEntityMsg.getCode())) {
            try {
                String result = parseEntityToString(httpEntityMsg.getData());
                return Msg.success(httpEntityMsg.getMsg(), result);
            } catch (IOException e) {
                e.printStackTrace();
                return Msg.failed("转换字符串过程中发生异常：" + e.getMessage());
            }
        } else {
            return Msg.failed(httpEntityMsg.getMsg());
        }
    }

    @Override
    public Msg<String> doDelete(String url) {
        Msg<HttpEntity> httpEntityMsg = doDeleteWithEntity(url);
        return getStringMsg(httpEntityMsg);
    }

    @Override
    public Msg<HttpEntity> doDeleteWithEntity(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpDelete.setConfig(requestConfig);
        try {
            return getHttpClientResult(httpClient, httpDelete);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.failed("转换String时发生IO异常");
        }
    }

    @Override
    public Msg<String> doDelete(String url, Map<String, String> params) {
        Msg<HttpEntity> httpEntityMsg = doDeleteWithEntity(url, params);
        return getStringMsg(httpEntityMsg);
    }

    @Override
    public Msg<HttpEntity> doDeleteWithEntity(String url, Map<String, String> params){
        if (params == null) {
            params = new HashMap<>(0);
        }

        params.put("_method", "delete");
        return doPostWithEntity(url, params);
    }


    private void packageHeader(Map<String, String> params, HttpRequestBase httpMethod) {
        // 封装请求头
        if (params != null) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                // 设置到请求头到HttpRequestBase对象中
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    private void packageParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod)
            throws UnsupportedEncodingException {
        // 封装请求参数
        if (params != null) {
            List<NameValuePair> nvps = new ArrayList<>();
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            // 设置到请求的http对象中
            httpMethod.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
        }
    }

    private Msg<HttpEntity> getHttpClientResult(CloseableHttpClient httpClient, HttpRequestBase httpMethod) {
        // 执行请求
        CloseableHttpResponse httpResponse;
        try {
            logger.info("执行请求：{}，请求方式：{}", httpMethod.getURI().toString(), httpMethod.getMethod());
            httpResponse = httpClient.execute(httpMethod);
            // 获取返回结果
            if (httpResponse != null && httpResponse.getStatusLine() != null) {
                HttpEntity content;
                if (httpResponse.getEntity() != null) {
                    content = httpResponse.getEntity();
                    return Msg.success(httpResponse.getStatusLine().getStatusCode() + "请求成功", content);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("请求过程中出现异常: {}", e.getMessage());
            return Msg.failed("请求过程出现异常" + e.getMessage());
        }


        return Msg.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR + "请求失败");
    }

    private void release(CloseableHttpClient httpClient) throws IOException {
        // 释放资源
        if (httpClient != null) {
            httpClient.close();
        }
    }
}