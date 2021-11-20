package com.dyj.blogback.service;
import com.dyj.blogback.mapper.TagMapper;
import com.dyj.blogback.model.Tag;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:wjup
 * @Date: 2018/9/26 0026
 * @Time: 15:23
 */
@Service
public class TagService {
    @Resource
    private TagMapper tagMapper;
    public JSONObject getTagList(){
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<Tag> allTags = tagMapper.getTagList();
        
        for(Tag tag:allTags){
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("label",tag.getName());
            jsonObject1.put("value",tag.getName());

            jsonArray.add(jsonObject1);
        }
        
        jsonObject.put("ret",1);
        jsonObject.put("retList",jsonArray);

        return jsonObject;
    }

    public JSONObject getTags(){
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<Tag> allTags = tagMapper.getTagList();
        for(Tag tag:allTags){
            JSONObject jsonObject1=new JSONObject();
            jsonObject1.put("tagId",tag.getId());
            jsonObject1.put("name",tag.getName());


            int num = tagMapper.findBlogOfTag(tag.getId()).size();
            jsonObject1.put("num",num);
            jsonArray.add(jsonObject1);
        }
        jsonObject.put("tableData",jsonArray);

        return jsonObject;
    }

    public int addTag(String name){
        try{
            return tagMapper.addTag(name);
        }catch (Exception e){
            return 0;
        }
    }

    public int editTag(int typeId,String name){
        try {
            return tagMapper.editTag(typeId,name);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int deleteTag(int typeId){
        try{
            return tagMapper.deleteTag(typeId);
        }catch (Exception e){
            return 0;
        }
    }
}