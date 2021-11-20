package com.dyj.blogback.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dyj.blogback.mapper.BlogMapper;
import com.dyj.blogback.mapper.TagMapper;
import com.dyj.blogback.mapper.TypeMapper;
import com.dyj.blogback.model.Blog;
import com.dyj.blogback.model.Tag;
import com.dyj.blogback.model.Type;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:wjup
 * @Date: 2018/9/26 0026
 * @Time: 15:23
 */
@Service
public class TypeService {
    @Resource
    private TypeMapper typeMapper;
    @Resource
    private BlogMapper blogMapper;
    public JSONObject getTypeList(){
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<Type> allTypes = typeMapper.getTypeList();
        
        for(Type type:allTypes){
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("label",type.getName());
            jsonObject1.put("value",type.getName());
            jsonArray.add(jsonObject1);
        }
        
        jsonObject.put("ret",1);
        jsonObject.put("retList",jsonArray);

        return jsonObject;
    }

    public JSONObject getTypes(){
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<Type> allTypes = typeMapper.getTypeList();
        for(Type type:allTypes){
            JSONObject jsonObject1=new JSONObject();
            jsonObject1.put("typeId",type.getId());
            jsonObject1.put("name",type.getName());
            int num = blogMapper.findBlogOfType(type.getId()).size();
            jsonObject1.put("num",num);
            jsonArray.add(jsonObject1);

        }
        jsonObject.put("tableData",jsonArray);

        return jsonObject;
    }

    public int addType(String name){
        try{
            return typeMapper.addType(name);
        }catch (Exception e){
            return 0;
        }
    }

    public int editType(int typeId,String name){
        try {
            return typeMapper.editType(typeId,name);
        }catch (Exception e){
            return 0;
        }
    }

    public int deleteType(int typeId){
        try{
            return typeMapper.deleteType(typeId);
        }catch (Exception e){
            return 0;
        }
    }

}