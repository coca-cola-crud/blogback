package com.dyj.blogback.service;

import com.dyj.blogback.mapper.UserMapper;
import com.dyj.blogback.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:wjup
 * @Date: 2018/9/26 0026
 * @Time: 15:23
 */
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    public User getUserInfoById(int id){
        System.out.println(id);
        return userMapper.getUserInfoById(id);
    }

}