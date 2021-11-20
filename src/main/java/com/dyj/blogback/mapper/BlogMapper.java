package com.dyj.blogback.mapper;

import com.dyj.blogback.model.Blog;
import com.dyj.blogback.model.BlogTag;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface BlogMapper {
    //    values(#{title}, #{content},#{firstPicture},#{flag},#{appreciation},#{commentabled},
//            #{sharestatement},#{recommend},#{published},#{createTime},#{updateTime})
    int addBlog(String title, String content, String firstPicture, String flag, boolean appreciation, boolean commentabled, boolean sharestatement, boolean recommend,
                boolean published, Date createTime,Date updateTime,int typeId,String html,String description);
    int addBlogTag(int blogId,int tagId);

    Blog findIdByTitle(String title);

    int toPublish(int id);

    List<Blog> getBlogs();
    List<Blog> getPBlogs();

    int editBlog(int blogId,String title, String content, String firstPicture, String flag, boolean appreciation, boolean commentabled, boolean sharestatement, boolean recommend,
             Date updateTime,int typeId,String html,String description);
    int editBlogTag(int blogId,int tagId);

    Blog findPBlogById(int id);
    Blog findBlogById(int id);

    List<BlogTag> findTagIdByBlogId(int id);

    int deleteBlog(int id);

    List<Blog> findBlogOfType(int typeId);

    List<Blog> getRecommend();

    int setViews(int id,int views);







}
