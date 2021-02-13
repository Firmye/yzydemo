package com.yzy.demo.controller.gateway.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/gateway")
@Slf4j
public class GatewayViewController {
    @GetMapping("/")
    public String index(HttpServletRequest request){
        String uid = request.getHeader("x-tif-uid");
        log.info(uid);

        // 业务鉴权

        return "gateway/index";
    }
}
