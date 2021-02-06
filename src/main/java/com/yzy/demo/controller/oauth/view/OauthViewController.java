package com.yzy.demo.controller.oauth.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/oauth")
public class OauthViewController {
    @GetMapping("/")
    public String index(){
        return "oauth/index";
    }
}
