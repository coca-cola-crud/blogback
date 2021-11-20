package com.dyj.blogback.model;

import java.util.Date;
import java.util.List;

public class Comment {
    private String id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;
    private Date createTime;
    private String pid;
    private String blogId;
    private List<Comment> child;

    public String getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public List<Comment> getChild() {
        return child;
    }

    public void setChild(List<Comment> child) {
        this.child = child;
    }
}
