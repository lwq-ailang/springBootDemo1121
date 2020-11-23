package com.example.demo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.demo.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    //@Reference 用来注入dubbo的服务对象，用的时候不要导错包。
    @Reference
    //客户端接口类名称要与服务端接口名称一致；路径也要一致。
    private TestService testService;

    @RequestMapping()
    @ResponseBody
    public String hello(){
        String dalao = testService.hello("dalao");
        System.out.println(dalao);
        return dalao;
    }

}
