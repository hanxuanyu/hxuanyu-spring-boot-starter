package com.hxuanyu.network.service.impl;

import com.hxuanyu.common.message.Msg;
import com.hxuanyu.network.service.HttpService;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.ConnectionConfig;
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
import java.net.URISyntaxException;
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
    public Msg<HttpResponse> doGetWithResponse(String url) {

        return doGetWithResponse(url, null, null);
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
    public void doGetSyncWithResponse(String url, NetWorkListener<HttpResponse> listener) {
        doGetSyncWithResponse(url, null, listener);
    }


    @Override
    public Msg<String> doGet(String url, Map<String, String> params) {
        return doGet(url, null, params);
    }

    @Override
    public Msg<HttpResponse> doGetWithResponse(String url, Map<String, String> params) {
        return doGetWithResponse(url, null, params);
    }

    @Override
    public void doGetSync(String url, Map<String, String> params, NetWorkListener<String> listener) {
        doGetSync(url, null, params, listener);
    }

    @Override
    public void doGetSyncWithResponse(String url, Map<String, String> params, NetWorkListener<HttpResponse> listener) {
        doGetSyncWithResponse(url, null, params, listener);
    }


    @Override
    public Msg<String> doGet(String url, Map<String, String> headers, Map<String, String> params) {
        // 创建httpClient对象
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 创建访问的地址
            HttpGet httpGet = buildHttpGet(url, params, headers);
            // 创建httpResponse对象
            CloseableHttpResponse httpResponse = null;
            // 执行请求并获得响应结果
            Msg<HttpResponse> httpClientResult = getHttpClientResult(httpClient, httpGet);
            if (httpClientResult.isSuccess()) {
                String result = EntityUtils.toString(httpClientResult.getData().getEntity());
                return Msg.success("请求成功[" + httpClientResult.getCode() + "]", result);
            } else {
                return Msg.failed("请求失败[" + httpClientResult.getCode() + "]");
            }

        } catch (Exception e) {
            logger.error("请求过程出现异常: {}", e.getMessage());
            return Msg.failed("请求过程中出现异常" + e.getMessage());
        }
    }

    @Override
    public Msg<HttpResponse> doGetWithResponse(String url, Map<String, String> headers, Map<String, String> params) {
        // 创建httpClient对象
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = buildHttpGet(url, params, headers);
            // 执行请求并获得响应结果
            return getHttpClientResult(httpClient, httpGet);
        } catch (Exception e) {
            logger.error("请求过程出现异常: {}", e.getMessage());
            return Msg.failed("请求过程中出现异常" + e.getMessage());
        }
    }

    private HttpGet buildHttpGet(String url, Map<String, String> params, Map<String, String> headers) throws URISyntaxException {
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
        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .build();
        httpGet.setConfig(requestConfig);
        // 设置请求头
        packageHeader(headers, httpGet);
        return httpGet;
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
    public void doGetSyncWithResponse(String url, Map<String, String> headers, Map<String, String> params, NetWorkListener<HttpResponse> listener) {
        executorService.execute(() -> {
            try {
                Msg<HttpResponse> msg = doGetWithResponse(url, headers, params);
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
    public Msg<HttpResponse> doPostWithResponse(String url) {
        return doPostWithResponse(url, null, null);
    }

    @Override
    public void doPostSync(String url, NetWorkListener<String> listener) {
        doPostSync(url, null, listener);
    }

    @Override
    public void doPostSyncWithResponse(String url, NetWorkListener<HttpResponse> listener) {
        doPostSyncWithResponse(url, null, listener);
    }


    @Override
    public Msg<String> doPost(String url, Map<String, String> params) {
        return doPost(url, null, params);
    }

    @Override
    public Msg<HttpResponse> doPostWithResponse(String url, Map<String, String> params) {
        return doPostWithResponse(url, null, params);
    }

    @Override
    public void doPostSync(String url, Map<String, String> params, NetWorkListener<String> listener) {
        doPostSync(url, null, params, listener);
    }

    @Override
    public void doPostSyncWithResponse(String url, Map<String, String> params, NetWorkListener<HttpResponse> listener) {
        doPostSyncWithResponse(url, null, params, listener);
    }

    @Override
    public Msg<String> doPost(String url, Map<String, String> headers, Map<String, String> params) {
        Msg<HttpResponse> httpResponseMsg = doPostWithResponse(url, headers, params);
        String stringResult;
        try {
            stringResult = EntityUtils.toString(httpResponseMsg.getData().getEntity());
            return Msg.success("请求成功", stringResult);
        } catch (IOException e) {
            e.printStackTrace();
            return Msg.failed("转换字符串失败：" + e.getMessage());
        }
    }

    @Override
    public Msg<HttpResponse> doPostWithResponse(String url, Map<String, String> headers, Map<String, String> params) {
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建http对象
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPost.setConfig(requestConfig);
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
    public void doPostSyncWithResponse(String url, Map<String, String> headers, Map<String, String> params, NetWorkListener<HttpResponse> listener) {
        executorService.execute(() -> {
            try {
                Msg<HttpResponse> msg = doPostWithResponse(url, headers, params);
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
    public Msg<HttpResponse> doPutWithResponse(String url) {
        return doPutWithResponse(url, null);
    }

    @Override
    public Msg<String> doPut(String url, Map<String, String> params) {
        Msg<HttpResponse> httpResponseMsg = doPutWithResponse(url, params);
        String stringResult;
        try {
            stringResult = EntityUtils.toString(httpResponseMsg.getData().getEntity());
            return Msg.success("请求成功", stringResult);
        } catch (IOException e) {
            e.printStackTrace();
            return Msg.failed("转换字符串失败：" + e.getMessage());
        }
    }

    @Override
    public Msg<HttpResponse> doPutWithResponse(String url, Map<String, String> params) {
        // 创建httpClient对象
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


    @Override
    public Msg<String> doDelete(String url) {
        Msg<HttpResponse> httpResponseMsg = doDeleteWithResponse(url);
        String stringResult;
        try {
            stringResult = EntityUtils.toString(httpResponseMsg.getData().getEntity());
            return Msg.success("请求成功", stringResult);
        } catch (IOException e) {
            e.printStackTrace();
            return Msg.failed("转换字符串失败：" + e.getMessage());
        }
    }

    @Override
    public Msg<HttpResponse> doDeleteWithResponse(String url) {
        // 创建httpClient对象
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
        Msg<HttpResponse> httpResponseMsg = doDeleteWithResponse(url, params);
        String stringResult;
        try {
            stringResult = EntityUtils.toString(httpResponseMsg.getData().getEntity());
            return Msg.success("请求成功", stringResult);
        } catch (IOException e) {
            e.printStackTrace();
            return Msg.failed("转换字符串失败：" + e.getMessage());
        }
    }

    @Override
    public Msg<HttpResponse> doDeleteWithResponse(String url, Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>(0);
        }

        params.put("_method", "delete");
        return doPostWithResponse(url, params);
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

    private Msg<HttpResponse> getHttpClientResult(CloseableHttpClient httpClient, HttpRequestBase httpMethod) throws IOException {
        // 执行请求
        CloseableHttpResponse httpResponse;
        logger.info("执行请求：{}，请求方式：{}", httpMethod.getURI().toString(), httpMethod.getMethod());
        httpResponse = httpClient.execute(httpMethod);
        // 获取返回结果
        if (httpResponse != null && httpResponse.getStatusLine() != null) {
            return Msg.success("请求成功", httpResponse);
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