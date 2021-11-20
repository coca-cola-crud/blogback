package com.dyj.blogback.mapper;

import com.dyj.blogback.model.*;
import org.apache.ibatis.annotations.Mapper;
import java.awt.*;

import java.time.LocalDateTime;
import  java.util.List;

@Mapper
public interface AuthMapper{
    User findByToken(String token);
    User findByUsername(String username);
    int setToken(String token, LocalDateTime expireTime,LocalDateTime loginTime,Long id);//将token存至数据库

}
