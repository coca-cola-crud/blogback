package com.dyj.blogback.mapper;

import com.dyj.blogback.model.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    Integer addComment(Comment comment);

    List<Comment> findComments(String blogId);
    List<Comment> getCommentByParentId(String parentId);
}

