package com.yzy.demo.controller.oauth.json;

import com.alibaba.fastjson.JSONObject;
import com.yzy.demo.utils.ApiRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class OauthJsonController {
    @PostMapping("/getInfo")
    public String getUserInfo(String code) throws Exception {
        // 获取AccessToken
        String apiPath = "/ebus/wwlapi/cgi-bin/gettoken?corpid=wld341060039&corpsecret=sO6Apif26CRkYDvwSWkbA-bkga4hdxLR3gbwqJvAJpc";
        CloseableHttpResponse response = ApiRequestUtil.apiGatewayRequest("Get", apiPath, null);
        // HttpEntity entity = response.getEntity();
        String responseStr = EntityUtils.toString(response.getEntity(), "UTF-8");
        JSONObject jo = JSONObject.parseObject(responseStr);
        String accessToken = jo.getString("access_token");

        // 获取用户ID
        apiPath = "/ebus/wwlapi/cgi-bin/user/getuserinfo?access_token=" + accessToken + "&code=" + code;
        response = ApiRequestUtil.apiGatewayRequest("Get", apiPath, null);
        responseStr = EntityUtils.toString(response.getEntity(), "UTF-8");
        jo = JSONObject.parseObject(responseStr);
        String userId = jo.getString("UserId");

        // 获取用户信息
        apiPath = "/ebus/org/v2/getuserbyuserid";
        List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("userid",userId));
        response = ApiRequestUtil.apiGatewayRequest("Post", apiPath, list);
        responseStr = EntityUtils.toString(response.getEntity(), "UTF-8");
        log.info(responseStr);
        return responseStr;
    }
}
