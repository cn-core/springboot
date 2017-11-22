package com.common.tools.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
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
class HttpClientRequest {


    private static CloseableHttpClient httpClient;

    /**
     * connectionRequestTimeout:从连接池中获取连接的超时时间，超过该时间未拿到可用连接，会抛出
     * org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
     * connectTimeout:连接上服务器(握手成功)的时间，超出该时间抛出connect timeout
     * socketTimeout:服务器返回数据(response)的时间，超过该时间抛出read timeout
     */
    static {
        httpClient = HttpClients.custom().setDefaultRequestConfig(RequestConfig.custom()
                .setConnectionRequestTimeout(2000).setConnectTimeout(2000).setSocketTimeout(2000).build()).build();
    }

    /**
     * POST请求---JSON
     * @param url        请求URL
     * @param jsonObject 请求数据
     */
    static String endPost(final String url, JSONObject jsonObject) {
        HttpPost httpPost = new HttpPost(url);
        try {
            StringEntity entity = new StringEntity(jsonObject.toString(), Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json; charset=utf-8");
            // Content-Length报错
            // 执行请求操作,并拿到结果(同步阻塞)
            HttpResponse response = httpClient.execute(httpPost);
            return parserResponse(response);
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
     * GET请求---JSON
     * @param url           请求URL
     * @param jsonObject    请求数据
     */
    static String endGet(final String url,JSONObject jsonObject) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000).build();
        httpGet.setConfig(requestConfig);

        // 设置请求头
        httpGet.addHeader("Content-Type","text/plain;charset=UTF-8");
        HttpResponse response;
        try {
            response = httpClient.execute(httpGet);
            return parserResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 解析HttpResponse信息
     *
     * @param response 请求结构
     */
    private static String parserResponse(HttpResponse response) throws IOException {
        if (response == null) {
            throw new RuntimeException("请求失败!");
        }
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity responseEntity = response.getEntity();
            String result = EntityUtils.toString(responseEntity, "UTF-8");
            // 一般来说都要删除多余的字符  去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格
            return result.replaceAll("\r", "");
        } else {
            // 重新发送
            throw new RuntimeException("请求失败!");
        }
    }
}
