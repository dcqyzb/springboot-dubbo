package com.example.customer.dubboService;

import com.example.api.entity.User;
import com.example.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class DubboService {

    @Reference(version = "${dubbo.version}", check = true, interfaceClass = UserService.class)
    private UserService userService;

    public User getUser() {
        return userService.getUser();
    }
}
