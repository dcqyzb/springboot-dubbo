package com.example.customer.controller;

import com.example.api.entity.User;
import com.example.api.service.UserService;
import com.example.customer.dubboService.DubboService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private DubboService dubboService;

    @RequestMapping("/getUser")
    public User getUser() {
        return dubboService.getUser();
    }
}
