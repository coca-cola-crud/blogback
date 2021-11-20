package com.dyj.blogback.mapper;

import com.dyj.blogback.model.*;
import org.apache.ibatis.annotations.Mapper;
import java.awt.*;

import  java.util.List;

@Mapper
public interface UserMapper{
   User getUserInfoById(int id);
}
