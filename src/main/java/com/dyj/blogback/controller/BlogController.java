package com.dyj.blogback.controller;

import com.alibaba.fastjson.JSONObject;
import com.dyj.blogback.model.BlogSave;
import com.dyj.blogback.model.LoginDTO;
import com.dyj.blogback.service.AuthService;
import com.dyj.blogback.service.BlogService;
import com.dyj.blogback.util.FileUtil;
import com.dyj.blogback.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;


@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Value("${file.upload.abpath}")
    private String abpath;

    //上传目录url
    @Value("${file.upload.mdImageDir}")
    private String mdImageDir;

    //端口号
    @Value("${server.port}")
    private String port;


    @PostMapping("/saveBlog")
    public Result login(@RequestBody BlogSave blogSave) {

        int blogId = blogService.saveBlog(blogSave);

        return Result.build(200, "保存成功",blogId);
    }

    @PostMapping("/uploadFile")
    public Result uploadFile(@RequestParam("image") MultipartFile file, HttpServletRequest request) {
        System.out.println(file);
        String imgAbPath = abpath + "/assets/";
        String imgUrlDir = "http:" + request.getHeader("Origin").split(":")[1] + ":" + port + "/v1" + mdImageDir;
        //返回对应的File类型f
        File f = FileUtil.upload(file, imgAbPath);

        //返回图片地址 http://localhost:4000/note/assets/image-20200924014614792.png  注意图片名子中间有加密
        return Result.build(200, "保存成功", imgUrlDir + "/" + f.getName());
    }

    @GetMapping("/publish")
    public Result publish(@RequestParam(value = "blogId", defaultValue = "-1") Integer blogId) {
        if(blogId==-1){
            return Result.build(400, "传参失败");
        }

        if (blogService.toPublish(blogId) == 1) {
            return Result.build(200, "发布成功");
        }else{
            return Result.build(400, "发布失败");
        }
    }

    @GetMapping("/getBlogs")
    public Result getBlogList(@RequestParam(value = "curPage", defaultValue = "-1") Integer curPage,
                              @RequestParam(value = "pageSize", defaultValue = "-1") Integer pageSize){
        if(curPage==-1||pageSize==-1){
            return Result.build(400,"传参失败");
        }
        JSONObject jsonObject=blogService.getBlogs(curPage,pageSize);
        return Result.build(200,"",jsonObject);
    }

    @GetMapping("/user/getPBlogs")
    public Result getPBlogList(@RequestParam(value = "curPage", defaultValue = "-1") Integer curPage,
                              @RequestParam(value = "pageSize", defaultValue = "-1") Integer pageSize){
        if(curPage==-1||pageSize==-1){
            return Result.build(400,"传参失败");
        }
        JSONObject jsonObject=blogService.getPBlogs(curPage,pageSize);
        return Result.build(200,"",jsonObject);
    }


    @GetMapping("/user/getABlog")
    public Result getBlog(@RequestParam(value = "blogId", defaultValue = "-1") Integer blogId){
        if(blogId==-1){
            return Result.build(400, "传参失败");
        }
        JSONObject jsonObject=blogService.getAblog(blogId);
        return Result.build(200,"",jsonObject.toJSONString());
    }

    @GetMapping("/deleteBlog")
    public Result deleteBlog(@RequestParam(value="blogId",defaultValue = "-1") Integer blogId){
        if(blogId==-1){
            return Result.build(400,"传参失败");
        }
        if(blogService.delete(blogId)==1){
            return Result.build(200,"删除成功");
        }else{
            return Result.build(400,"删除失败");
        }
    }

    @GetMapping("/user/getRecommend")
    public Result getRecommend(){
        return Result.build(200,"获取成功",blogService.getRecommned());
    }

    @GetMapping("/user/getBlogsByType")
    public Result getBlogsByType(@RequestParam(value = "type",defaultValue = "-1") String type,
                                 @RequestParam(value = "curPage",defaultValue = "-1") Integer curPage,
                                 @RequestParam(value = "pageSize",defaultValue = "-1") Integer pageSize){
        if(type.equals("-1")){
            return Result.build(400,"传参失败");
        }
        JSONObject jsonObject=blogService.getBlogByType(type,curPage,pageSize);
        return Result.build(200,"",jsonObject);

    }
    @GetMapping("/user/getBlogsByTag")
    public Result getBlogsByTag(@RequestParam(value = "tag",defaultValue = "-1") String tag,
                                 @RequestParam(value = "curPage",defaultValue = "-1") Integer curPage,
                                 @RequestParam(value = "pageSize",defaultValue = "-1") Integer pageSize){
        if(tag.equals("-1")){
            return Result.build(400,"传参失败");
        }
        JSONObject jsonObject=blogService.getBlogByTag(tag,curPage,pageSize);
        return Result.build(200,"",jsonObject);

    }

    @GetMapping("/user/getTimeLine")
    public Result getTimeline(){
        JSONObject jsonObject = blogService.getTimeLine();
        return Result.build(200,"",jsonObject);
    }
}
