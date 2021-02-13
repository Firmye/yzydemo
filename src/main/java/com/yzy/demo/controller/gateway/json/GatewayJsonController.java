package com.yzy.demo.controller.gateway.json;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/gateway")
@Slf4j
public class GatewayJsonController {
    @GetMapping("/getInfo")
    public String index(HttpServletRequest request){
        String uid = request.getHeader("x-tif-uid");
        log.info(uid);

        // 业务鉴权

        return "gateway/index";
    }

}
