package com.dyj.blogback.controller;

import com.dyj.blogback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/getAllJob/{id}")
    public String getUserInfoById(@PathVariable int id){
        return userService.getUserInfoById(id).getType();
    }
}
