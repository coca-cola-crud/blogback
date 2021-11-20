package com.dyj.blogback.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dyj.blogback.mapper.BlogMapper;
import com.dyj.blogback.mapper.TagMapper;
import com.dyj.blogback.mapper.TypeMapper;
import com.dyj.blogback.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.github.pagehelper.PageHelper;
import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

@Service
public class BlogService {
    @Resource
    private BlogMapper blogMapper;
    @Resource
    private TypeMapper typeMapper;

    @Resource
    private TagMapper tagMapper;



    public int saveBlog(BlogSave blogtosave){
//        int addBlog(String title, String content, String firstPicture, String flag, boolean appreciation, boolean commentabled, boolean sharestatement, boolean recommend,
//        boolean published, String createTime,String updateTime);
        String title= blogtosave.getTitle();
        String content=blogtosave.getContent();
        String firstPicture = blogtosave.getFirstPicture();
        String flag = blogtosave.getFlag();
        String type = blogtosave.getType();
        String html=blogtosave.getHtml();
        List<String> tag = blogtosave.getTag();
        System.out.println(tag);
        boolean commentabled = blogtosave.isCommentabled();
        boolean appreciation = blogtosave.isAppreciation();
        boolean sharestatement = blogtosave.isShareStatement();
        boolean recommend = blogtosave.isRecommend();
        boolean published = blogtosave.isPublished();
        Date createTime = new Date();
        Date updateTime = new Date();
        String description = blogtosave.getDescription();
        int state = blogtosave.getState();
        System.out.println(type);

//        values(#{title}, #{content},#{firstPicture},#{flag},#{appreciation},#{commentabled},
//      #{sharestatement},#{recommend},#{published},#{createTime},#{updateTime},#{typeId})
        Type typetarget = typeMapper.findIdByName(type);

        int typeId =typetarget.getId().intValue();


        if(state==0){
            blogMapper.addBlog(title,content,firstPicture,flag,appreciation,commentabled,sharestatement,recommend,published,createTime,updateTime,typeId,html,description);
            Blog blogtarget = blogMapper.findIdByTitle(title);
            int blogId =blogtarget.getId().intValue();
            for(int i=0;i<tag.size();i++){
                Tag  tagtarget = tagMapper.findIdByName(tag.get(i));
                int tagId = tagtarget.getId();
                blogMapper.addBlogTag(blogId,tagId);

            }
            return blogId;
        }else {
            blogMapper.editBlog(state,title,content,firstPicture,flag,appreciation,commentabled,sharestatement,recommend,updateTime,typeId,html,description);
            for(int i=0;i<tag.size();i++){
                //先把已有的删除
                Tag  tagtarget = tagMapper.findIdByName(tag.get(i));
                int tagId = tagtarget.getId();
                System.out.println(tagId);
                try{
                    blogMapper.addBlogTag(state,tagId);
                }catch (Exception e){
                    continue;
                }


            }

            return state;
       }


    }
    public int toPublish(int id){
      return  blogMapper.toPublish(id);
    }
    public JSONObject getPBlogs(int curPage,int pageSize){
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray = new JSONArray();

        List<Blog> totalBlogs = blogMapper.getPBlogs();
        PageHelper.startPage(curPage, pageSize);
        List<Blog> allBlogs = blogMapper.getPBlogs();
        for(Blog blog:allBlogs){
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("title",blog.getTitle());
            jsonObject1.put("id",blog.getId());
            try{
                jsonObject1.put("type",typeMapper.findNameById(blog.getTypeId()).getName());
            }catch(Exception e){

                jsonObject1.put("type","空");

            }
            jsonObject1.put("avatar", "https://dev-file.iviewui.com/userinfoPDvn9gKWYihR24SpgC319vXY8qniCqj4/avatar");
            jsonObject1.put("firstPicture",blog.getFirstPicture());
            jsonObject1.put("content",blog.getContent());
            jsonObject1.put("description",blog.getDescription());
            //    System.out.println(blog.getHtml());

            String recommend = (blog.isRecommend()==true)?"是":"否";
            jsonObject1.put("recommend",recommend);
            jsonObject1.put("updatetime",blog.getUpdateTime().toString());
            jsonObject1.put("views",blog.getViews());
            jsonArray.add(jsonObject1);
        }
        jsonObject.put("tableData",jsonArray);
        jsonObject.put("total",totalBlogs.size());
        return jsonObject;
    }

    public JSONObject getBlogs(int curPage,int pageSize){
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray = new JSONArray();

        List<Blog> totalBlogs = blogMapper.getBlogs();
        PageHelper.startPage(curPage, pageSize);
        List<Blog> allBlogs = blogMapper.getBlogs();
        for(Blog blog:allBlogs){
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("title",blog.getTitle());
            jsonObject1.put("id",blog.getId());
            try{
                jsonObject1.put("type",typeMapper.findNameById(blog.getTypeId()).getName());
            }catch(Exception e){

                jsonObject1.put("type","空");

            }
            jsonObject1.put("avatar", "https://dev-file.iviewui.com/userinfoPDvn9gKWYihR24SpgC319vXY8qniCqj4/avatar");
            jsonObject1.put("firstPicture",blog.getFirstPicture());
            jsonObject1.put("content",blog.getContent());
            jsonObject1.put("description",blog.getDescription());
        //    System.out.println(blog.getHtml());

            String recommend = (blog.isRecommend()==true)?"是":"否";
            String published = (blog.isPublished())?"已发布":"未发布";
            jsonObject1.put("published",published);
            jsonObject1.put("recommend",recommend);
            jsonObject1.put("updatetime",blog.getUpdateTime().toString());
            jsonObject1.put("views",blog.getViews());
            jsonArray.add(jsonObject1);
        }
        jsonObject.put("tableData",jsonArray);
        jsonObject.put("total",totalBlogs.size());
        return jsonObject;
    }

    public JSONObject getAblog(int id){
        Blog blog = blogMapper.findBlogById(id);
        List<BlogTag> blogTag = blogMapper.findTagIdByBlogId(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag",blog.getFlag());
        jsonObject.put("title",blog.getTitle());
        jsonObject.put("content",blog.getContent());
        jsonObject.put("description",blog.getDescription());
        jsonObject.put("views",blog.getViews());
        jsonObject.put("type",typeMapper.findNameById(blog.getTypeId()).getName());
        jsonObject.put("updateTime",blog.getUpdateTime().toString());

        JSONArray jsonArray=new JSONArray();
        for(BlogTag blogtag:blogTag){
            jsonArray.add(tagMapper.findNameById(blogtag.getTagId()).getName());

        }
        jsonObject.put("tag",jsonArray);


        jsonObject.put("firstPicture",blog.getFirstPicture());
        jsonObject.put("appreciation", blog.isAppreciation());
        jsonObject.put("recommend",blog.isRecommend());
        jsonObject.put("commentabled",blog.isCommentabled());
        jsonObject.put("shareStatement",blog.isShareStatement());
        jsonObject.put("html",blog.getHtml());
        return jsonObject;
    }


    public  int delete(int blogId){
        try{
            return blogMapper.deleteBlog(blogId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return 0;
        }

    }

    public JSONObject getRecommned(){
        List<Blog> blogs = blogMapper.getRecommend();
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(Blog blog:blogs){
            JSONObject jsonObject1=new JSONObject();
            jsonObject1.put("id",blog.getId());
            jsonObject1.put("title",blog.getTitle());
            jsonArray.add(jsonObject1);
        }
        jsonObject.put("recommend",jsonArray);
        return jsonObject;

    }

    public JSONObject getBlogByType(String type,int curPage,int pageSize){

        Type target = typeMapper.findIdByName(type);

        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray = new JSONArray();

        List<Blog> totalBlogs = blogMapper.findBlogOfType(target.getId());
        PageHelper.startPage(curPage, pageSize);
        List<Blog> blogs = blogMapper.findBlogOfType(target.getId());
        for(Blog blog:blogs){
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("title",blog.getTitle());
            jsonObject1.put("id",blog.getId());
            try{
                jsonObject1.put("type",typeMapper.findNameById(blog.getTypeId()).getName());
            }catch(Exception e){

                jsonObject1.put("type","空");

            }
            jsonObject1.put("avatar", "https://dev-file.iviewui.com/userinfoPDvn9gKWYihR24SpgC319vXY8qniCqj4/avatar");
            jsonObject1.put("firstPicture",blog.getFirstPicture());
            jsonObject1.put("content",blog.getContent());
            jsonObject1.put("views",blog.getViews());
            //    System.out.println(blog.getHtml());

            String recommend = (blog.isRecommend()==true)?"是":"否";
            jsonObject1.put("recommend",recommend);
            jsonObject1.put("updatetime",blog.getUpdateTime().toString());
            jsonObject1.put("description",blog.getDescription());

            jsonArray.add(jsonObject1);


        }
        jsonObject.put("tableData",jsonArray);
        jsonObject.put("total",totalBlogs.size());
        return jsonObject;



    }
    public JSONObject getBlogByTag(String tag,int curPage,int pageSize){

        Tag target = tagMapper.findIdByName(tag);

        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray = new JSONArray();

        List<BlogTag> totalBlogs = tagMapper.findBlogOfTag(target.getId());
        PageHelper.startPage(curPage, pageSize);
        List<BlogTag> blogs = tagMapper.findBlogOfTag(target.getId());
        for(BlogTag blogtag:blogs){

            Blog blog = blogMapper.findPBlogById(blogtag.getBlogId());
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("title",blog.getTitle());
            jsonObject1.put("id",blog.getId());

            try{
                jsonObject1.put("type",typeMapper.findNameById(blog.getTypeId()).getName());
            }catch(Exception e){

                jsonObject1.put("type","空");

            }
            jsonObject1.put("avatar", "https://dev-file.iviewui.com/userinfoPDvn9gKWYihR24SpgC319vXY8qniCqj4/avatar");
            jsonObject1.put("firstPicture",blog.getFirstPicture());
            jsonObject1.put("content",blog.getContent());
            //    System.out.println(blog.getHtml());

            String recommend = (blog.isRecommend()==true)?"是":"否";
            jsonObject1.put("recommend",recommend);
            jsonObject1.put("updatetime",blog.getUpdateTime().toString());
            jsonObject1.put("description",blog.getDescription());
            jsonObject1.put("views",blog.getViews());
            jsonArray.add(jsonObject1);


        }
        jsonObject.put("tableData",jsonArray);
        jsonObject.put("total",totalBlogs.size());
        return jsonObject;

    }

    public JSONObject getTimeLine(){
        List<Blog> blogs = blogMapper.getPBlogs();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject=new JSONObject();

        HashMap<Integer, ArrayList<Blog>> map = new HashMap<>();
        for(Blog blog:blogs){

            int year = Integer.valueOf(blog.getCreateTime().toString().split(" ")[5]);
            System.out.println(year);
            ArrayList<Blog> bloglist = map.getOrDefault(year,new ArrayList<Blog>());
            bloglist.add(blog);
            if(map.containsKey(year)){
                map.replace(year,map.getOrDefault(year,new ArrayList<Blog>()),bloglist);

            }else{
                map.put(year,bloglist);

            }


        }
        System.out.println(map);

        for(HashMap.Entry<Integer, ArrayList<Blog>> entry : map.entrySet()){
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("time",entry.getKey());
            jsonObject1.put("blogs",entry.getValue());
            jsonArray.add(jsonObject1);

        }
        jsonObject.put("TimeLine",jsonArray);
        return jsonObject;
    }



}
