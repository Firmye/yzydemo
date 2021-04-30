package com.yzy.demo.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author firmye
 * @Date 2017年11月21日 上午11:16:42
 * @Description Api网关请求工具类
 */
public class ApiRequestUtil {

    @Value("${yzy.paasToken}")
    String paasId;
    @Value("${yzy.paasToken}")
    String paasToken;
    @Value("${yzy.paasToken}")
    String domain;

    public static CloseableHttpResponse apiGatewayRequest(String requestMethod, String apiPath, List<NameValuePair> params) throws Exception {
        // 预发布网关参数
        // String paasId = "yzytest";
        // String paasToken = "xWcDutakroKCARvlZCXS32DfbH4XeWuQ";
        // String domain = "https://xtbg.digitalgd.com.cn";
        // 生产网关参数
        String paasId = "yzy_test";
        String paasToken = "j4ZklFT6ehAjT5IOHhzOVEOu9xdOryGh";
        String domain = "https://xtbg.gdzwfw.gov.cn";

        // 签名计算
        long now = new Date().getTime();
        String timestamp = Long.toString((long) Math.floor(now / 1000));
        String nonce = Long.toHexString(now) + "-" + Long.toHexString((long)
                Math.floor(Math.random() * 0xFFFFFF));
        String signature = EncodeUtil.encodeBySHA256(timestamp + paasToken + nonce +
                timestamp);

        // 请求头设置
        Map<String, String> headers = new HashMap<>();
        headers.put("x-tif-paasid", paasId);
        headers.put("x-tif-timestamp", timestamp);
        headers.put("x-tif-signature", signature);
        headers.put("x-tif-nonce", nonce);

        if ("Get".equals(requestMethod))
            return HttpUtil.doGet(domain + apiPath, headers, null);
        else
            return HttpUtil.doPost(domain + apiPath, headers, params);
    }

}
