package com.dyj.blogback.mapper;
import com.dyj.blogback.model.*;
import org.apache.ibatis.annotations.Mapper;
import java.awt.*;

import  java.util.List;

@Mapper
public interface TagMapper {
    List<Tag> getTagList();
    Tag findIdByName(String tag);
    Tag findNameById(int id);
    int addTag(String name);

    int deleteTag(int id);

    int editTag(int id,String name);

    List<BlogTag> findBlogOfTag(int tagId);

}
