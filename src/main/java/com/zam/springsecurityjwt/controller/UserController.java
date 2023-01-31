package com.zam.springsecurityjwt.controller;


import com.zam.springsecurityjwt.entity.User;
import com.zam.springsecurityjwt.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/all")
    public List<User> findAllUser(){
        return userService.findAll();
    }

}
