package com.dyj.blogback.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dyj.blogback.mapper.BlogMapper;
import com.dyj.blogback.mapper.CommentMapper;
import com.dyj.blogback.model.Blog;
import com.dyj.blogback.model.Comment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CommentService {
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private  BlogMapper blogMapper;

    public Integer addComment(Comment comment){
       return commentMapper.addComment(comment);

    }



    public List<Comment> getAllCommentByBlogId(String blogId) {
        List<Comment> commentVos = this.commentMapper.findComments(blogId);
        List<Comment> sons = getSons(commentVos);
        return sons;
    }

    private List<Comment> getSons1(String parentId){
        return commentMapper.getCommentByParentId(parentId);
    }


    private List<Comment> getSons(List<Comment> parents){
        if(parents == null || parents.size() == 0){
            return null;
        }
        for (Comment parent : parents) {

            List<Comment> sonCommentVos = getSons1(parent.getId());
            //递归找子评论
            parent.setChild(getSons(sonCommentVos));
        }
        return parents;
    }


    public JSONObject getComments(String blogId){
        List<Comment> allcomments =  getAllCommentByBlogId(blogId);
        Blog blog = blogMapper.findBlogById(Integer.valueOf(blogId));
        int views= blog.getViews();
        System.out.println(views);
        blogMapper.setViews(Integer.valueOf(blogId),views+1);



        JSONObject jsonObject  =  new JSONObject();
        JSONArray array= JSONArray.parseArray(JSON.toJSONString(allcomments));
        jsonObject.put("commentlist",array);
        return jsonObject;

    }
}
