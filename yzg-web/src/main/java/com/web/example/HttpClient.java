package com.web.example;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author YZG 2017/11/13.
 */
class HttpClient {

    /**
     * HTTP POST REQEUST
     *
     * @param jsonObject 请求数据
     * @param parameterConfig REQUEST HEADER VALUE
     */
    static HttpResponse endPost(JSONObject jsonObject, ParameterConfig parameterConfig) {
        /*
          connectionRequestTimeout:从连接池中获取连接的超时时间，超过该时间未拿到可用连接，会抛出
               org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
          connectTimeout:连接上服务器(握手成功)的时间，超出该时间抛出connect timeout
          socketTimeout:服务器返回数据(response)的时间，超过该时间抛出read timeout
         */
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(RequestConfig.custom()
                .setConnectionRequestTimeout(2000).setConnectTimeout(2000).setSocketTimeout(2000).build()).build();
        HttpPost httpPost = new HttpPost(parameterConfig.getReqeustUrl());
        try {
            StringEntity entity = new StringEntity(jsonObject.toString(), Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json; charset=utf-8");
            // Content-Length报错
            httpPost.setHeader("Authorization", parameterConfig.getAuthorization());
            // 执行请求操作,并拿到结果(同步阻塞)
            HttpResponse response = httpClient.execute(httpPost);
            parserResponse(response);
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 解析HttpResponse信息
     * @param response      请求结构
     */
    private static Boolean parserResponse(HttpResponse response) throws IOException {
        if (response == null) {
            throw new RuntimeException("请求失败!");
        }
        HttpEntity responseEntity = null;
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            responseEntity = response.getEntity();
        } else {
            // 重新发送
            throw new RuntimeException("请求失败!");
        }
        if (responseEntity != null){
            String result = EntityUtils.toString(responseEntity, "UTF-8");
            System.out.println(result);
            String statusCode = (String) JSONObject.parseObject(result).get("statusCode");
            if ("000000".equals(statusCode)) {
                return true;
            }else {
                return false;
            }
        } else {
            return false;
        }
    }
}
