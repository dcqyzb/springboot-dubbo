package com.example.customert.dubboService;

import com.example.api.entity.User;
import com.example.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class UserServicet {

    @Reference(version = "${dubbo.version}", check = true, interfaceClass = UserService.class)
    private UserService userService;

    public User getTeas() {
        return userService.getUser();
    }
}
