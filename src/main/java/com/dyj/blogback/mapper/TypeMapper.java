package com.dyj.blogback.mapper;

import com.dyj.blogback.model.Type;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TypeMapper {
    List<Type> getTypeList();
    Type findIdByName(String name);

    Type findNameById(Integer id);

    int addType(String name);

    int deleteType(int id);

    int editType(int id,String name);

}
