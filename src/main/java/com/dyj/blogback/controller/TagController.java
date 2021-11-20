package com.dyj.blogback.controller;

import com.dyj.blogback.service.TagService;
import com.dyj.blogback.util.Result;
import net.sf.json.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {
    @Autowired
    private TagService tagService;
    @GetMapping("/getTag")
    String getTagList(){
        JSONObject jsonObject=tagService.getTagList();
        return jsonObject.toJSONString();
    }
    @GetMapping("/user/getTaglist")
    Result getTaglist(){
        JSONObject jsonObject=tagService.getTags();
        return Result.build(200,"",jsonObject.get("tableData"));

    }

    @GetMapping("/addTag")
    Result addTag(@RequestParam(value = "name", defaultValue = "") String name){
        if(name.equals("")){
            return Result.build(400, "传参失败");
        }else{
            if(tagService.addTag(name)==1){
                return Result.build(200,"添加成功");
            }else{
                return Result.build(400,"添加失败");
            }
        }
    }

    @GetMapping("/editTag")
    Result editTag(@RequestParam(value = "tagId",defaultValue = "-1") Integer tagId,@RequestParam(value = "name",defaultValue = "-1") String name){
        if(tagId==-1||name.equals("-1")){
            return Result.build(400,"传参失败");
        }else{
            if(tagService.editTag(tagId,name)==1){
                return Result.build(200,"修改成功");
            }else{
                return Result.build(400,"修改失败");
            }

        }
    }

    @GetMapping("/deleteTag")
    Result deleteTag(@RequestParam(value = "tagId",defaultValue = "-1")Integer tagId){
        if(tagId==-1){
            return Result.build(400,"传参失败");
        }else{
            if(tagService.deleteTag(tagId)==1){
                return Result.build(200,"删除成功");
            }else{
                return Result.build(400,"删除失败");
            }

        }
    }


}
