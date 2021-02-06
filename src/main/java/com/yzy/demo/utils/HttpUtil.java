package com.yzy.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.List;
import java.util.Map;

/**
 * @Author firmye
 * @Date 2017年11月21日 上午11:16:42
 * @Description Http请求工具类
 */
@Slf4j
public class HttpUtil {

    /**
     * Get请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static CloseableHttpResponse doGet(String url, Map<String, String> headers, String charset) throws Exception {
        if (null == charset)
            charset = "UTF-8";
        // 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建代表请求的对象,参数是访问的服务器地址
        HttpGet httpGet = new HttpGet(url);
        if (null != headers)
            httpGet = (HttpGet) addHeaders(httpGet, headers);
        try {
            // 执行请求，获取服务器发还的相应对象
            return httpClient.execute(httpGet);
            // 从相应对象当中取出数据，放到entity当中
            // HttpEntity entity = response.getEntity();
            // return EntityUtils.toString(entity, charset);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Post请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static CloseableHttpResponse doPost(String url, Map<String, String> headers, List<NameValuePair> params) throws Exception {
        // 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建代表请求的对象,参数是访问的服务器地址
        HttpPost httpPost = new HttpPost(url);
        // 转换参数并设置编码格式
        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        // httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        if (null != headers)
            httpPost = (HttpPost) addHeaders(httpPost, headers);
        try {
            // 执行请求，获取服务器发还的相应对象
            return httpClient.execute(httpPost);
            // 从相应对象当中取出数据，放到entity当中
            // HttpEntity entity = response.getEntity();
            // return EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            throw e;
        }
    }

    public static HttpRequestBase addHeaders(HttpRequestBase httpRequestBase, Map<String, String> headers) {
        if (null != headers) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpRequestBase.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return httpRequestBase;
    }

}
