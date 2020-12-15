package com.example.demo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.demo.service.IUserService;
import org.springframework.stereotype.Component;

@Component //注册到spring容器中
@Service(interfaceClass = IUserService.class) //dubbo的service
public class UserServiceImpl implements IUserService {
    @Override
    public String sayHello(String name) {
        return "hello:"+name;
    }
}
