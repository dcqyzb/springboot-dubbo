package com.example.customert.controller;

import com.example.api.entity.User;
import com.example.customert.dubboService.UserServicet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teas")
public class TeasController {

    @Autowired
    private UserServicet userService;

    @RequestMapping("/getUser")
    public User getUser() {
        return userService.getTeas();
    }
}
