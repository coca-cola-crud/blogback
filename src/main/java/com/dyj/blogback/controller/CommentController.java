package com.dyj.blogback.controller;

import com.alibaba.fastjson.JSONObject;
import com.dyj.blogback.model.Comment;
import com.dyj.blogback.service.CommentService;
import com.dyj.blogback.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;
    @GetMapping("/user/getComments")
    public Result getCommnents(@RequestParam(value = "blogId",defaultValue = "-1") String blogId){
        return Result.build(200,"",commentService.getComments(blogId));
    }

    @GetMapping("/user/saveComment")
    public Result addComment(@RequestParam(value = "name", defaultValue = "-1") String nickname,
                              @RequestParam(value = "email", defaultValue = "-1") String email,
                             @RequestParam(value = "inputComment", defaultValue = "-1") String content,
                             @RequestParam(value = "avatar", defaultValue = "-1") String avatar,
                             @RequestParam(value = "blogId", defaultValue = "-1") String blogId,
                             @RequestParam(value = "fromId", defaultValue = "0") String pid){
        if(nickname.equals("-1")){
            return Result.build(400,"传参失败");
        }else{
            Comment comment = new Comment();
            comment.setNickname(nickname);
            comment.setAvatar(avatar);
            comment.setContent(content);
            comment.setEmail(email);
            comment.setCreateTime(new Date());
            comment.setBlogId(blogId);
            comment.setPid(pid);
            if(commentService.addComment(comment)==1){
                return Result.build(200,"评论成功");

            }else{
                return Result.build(400,"评论失败");
            }
        }

    }
}
