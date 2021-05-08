package com.yzy.demo.controller.jssdk.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/jssdk")
public class JssdkViewController {
    @GetMapping("/")
    public String index(){
        return "jssdk/index";
    }
}
