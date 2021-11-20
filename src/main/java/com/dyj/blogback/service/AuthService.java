package com.dyj.blogback.service;

import com.dyj.blogback.mapper.AuthMapper;
import com.dyj.blogback.mapper.UserMapper;
import com.dyj.blogback.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {
    @Resource
    private AuthMapper authMapper;


    public User findByToken(String token){
        System.out.println(token);
        return authMapper.findByToken(token);

    }

    public User findByUsername(String name){
        return authMapper.findByUsername(name);
    }

    private final static int EXPIRE = 12;

    public String createToken(User user) {
        //用UUID生成token
        String token = UUID.randomUUID().toString();
        //当前时间
        LocalDateTime now = LocalDateTime.now();
        //过期时间
        LocalDateTime expireTime = now.plusHours(EXPIRE);
        //保存到数据库
        user.setLoginTime(now);
        user.setExpireTime(expireTime);
        user.setToken(token);

        authMapper.setToken(token,expireTime,now,Long.parseLong(user.getId().toString()));
        return token;
    }

    public void logout(String token) {
        User userEntity = authMapper.findByToken(token);
        //用UUID生成token
        token = UUID.randomUUID().toString();
        userEntity.setToken(token);
        authMapper.setToken(token,userEntity.getExpireTime(),userEntity.getLoginTime(), Long.parseLong(userEntity.getId().toString()));
       // userRepository.save(userEntity);

    }
}
