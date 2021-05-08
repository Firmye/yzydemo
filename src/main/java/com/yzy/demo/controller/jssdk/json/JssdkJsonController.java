package com.yzy.demo.controller.jssdk.json;

import com.alibaba.fastjson.JSONObject;
import com.yzy.demo.utils.ApiRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/jssdk")
@Slf4j
public class JssdkJsonController {
    @Autowired
    ApiRequestUtil apiRequestUtil;

    @PostMapping("/getConfig")
    public String getConfig(String url) throws Exception {
        // 签名计算
        long now = new Date().getTime();
        String timestamp = Long.toString((long) Math.floor(now / 1000));
        String nonce = Long.toHexString(now) + "-" + Long.toHexString((long)
                Math.floor(Math.random() * 0xFFFFFF));

        // 获取AccessToken
        String apiPath = "/ebus/wwlapi/cgi-bin/gettoken?corpid=wld341060039&corpsecret=sO6Apif26CRkYDvwSWkbA-bkga4hdxLR3gbwqJvAJpc";
        CloseableHttpResponse response = apiRequestUtil.apiGatewayRequest("Get", apiPath, null);
        // HttpEntity entity = response.getEntity();
        String responseStr = EntityUtils.toString(response.getEntity(), "UTF-8");
        JSONObject jo = JSONObject.parseObject(responseStr);
        String accessToken = jo.getString("access_token");

        // 获取用户ID
        apiPath = "/ebus/wwlapi/cgi-bin/user/getuserinfo?access_token=" + accessToken + "&code=" + url;
        response = apiRequestUtil.apiGatewayRequest("Get", apiPath, null);
        responseStr = EntityUtils.toString(response.getEntity(), "UTF-8");
        jo = JSONObject.parseObject(responseStr);
        String userId = jo.getString("UserId");

        // 获取用户信息
        apiPath = "/ebus/org/v2/getuserbyuserid";
        List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("userid",userId));
        response = apiRequestUtil.apiGatewayRequest("Post", apiPath, list);
        responseStr = EntityUtils.toString(response.getEntity(), "UTF-8");
        log.info(responseStr);
        return responseStr;
    }
}
