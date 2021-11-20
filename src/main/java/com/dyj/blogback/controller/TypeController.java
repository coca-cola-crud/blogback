package com.dyj.blogback.controller;

import com.alibaba.fastjson.JSONObject;
import com.dyj.blogback.service.TagService;
import com.dyj.blogback.service.TypeService;
import com.dyj.blogback.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;

@RestController
public class TypeController {
    @Autowired
    private TypeService typeService;
    @GetMapping("/getType")
    String getTagList(){
        JSONObject jsonObject=typeService.getTypeList();
        return jsonObject.toJSONString();
    }
    @GetMapping("/user/getTypelist")
    Result getTypelist(){
        JSONObject jsonObject=typeService.getTypes();
        return Result.build(200,"",jsonObject.get("tableData"));

    }

    @GetMapping("/addType")
    Result addType(@RequestParam(value = "name", defaultValue = "") String name){
        if(name.equals("")){
            return Result.build(400, "传参失败");
        }else{
            if(typeService.addType(name)==1){
                return Result.build(200,"添加成功");
            }else{
                return Result.build(400,"添加失败");
            }
        }
    }

    @GetMapping("/editType")
    Result editType(@RequestParam(value = "typeId",defaultValue = "-1") Integer typeId,@RequestParam(value = "name",defaultValue = "-1") String name){
        if(typeId==-1||name.equals("-1")){
            return Result.build(400,"传参失败");
        }else{
            if(typeService.editType(typeId,name)==1){
                return Result.build(200,"修改成功");
            }else{
                return Result.build(400,"修改失败");
            }

        }
    }

    @GetMapping("/deleteType")
    Result deleteType(@RequestParam(value = "typeId",defaultValue = "-1")Integer typeId){
        if(typeId==-1){
            return Result.build(400,"传参失败");
        }else{
            if(typeService.deleteType(typeId)==1){
                return Result.build(200,"删除成功");
            }else{
                return Result.build(400,"删除失败");
            }

        }
    }


}
