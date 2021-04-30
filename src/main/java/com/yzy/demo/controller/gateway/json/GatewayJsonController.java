package com.yzy.demo.controller.gateway.json;

import com.yzy.demo.utils.EncodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("/gateway")
@Slf4j
public class GatewayJsonController {
    @Value("${yzy.paasToken}")
    String paasToken;

    @GetMapping("/getInfo")
    public String index(HttpServletRequest request) throws NoSuchAlgorithmException {
        // 生产环境需要把x-rio换成x-tif
        String signature = request.getHeader("x-tif-signature");
        String timestamp = request.getHeader("x-tif-timestamp");
        String nonce = request.getHeader("x-tif-nonce");
        String uid = request.getHeader("x-tif-uid");
        String uinfo = request.getHeader("x-tif-uinfo");
        String ext = request.getHeader("x-tif-ext");

        // 验签
        String sign = EncodeUtil.encodeBySHA256(timestamp + paasToken + nonce + "," + uid + ","
                + uinfo + "," + ext + timestamp).toUpperCase();
        if (signature.equals(sign)) {
            log.info("签名通过");
        }

        // 业务鉴权

        return "gateway/index";
    }

}
