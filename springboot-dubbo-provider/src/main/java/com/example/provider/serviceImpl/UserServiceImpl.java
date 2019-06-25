package com.example.provider.serviceImpl;

import com.example.api.entity.User;
import com.example.api.service.UserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Component
@Service(version = "${dubbo.version}", timeout = 10000, interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Override
    public User getUser() {
        User user = new User();
        user.setPassword("12344");
        user.setUserName("admin");
        return user;
    }
}
