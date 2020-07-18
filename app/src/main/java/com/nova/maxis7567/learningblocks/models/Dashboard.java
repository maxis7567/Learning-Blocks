package com.nova.maxis7567.learningblocks.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Dashboard {
    @SerializedName("userProfile")
    private User user;
    @SerializedName("new blogs")
    private List<Blog> blogList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Blog> getBlogList() {
        return blogList;
    }

    public void setBlogList(List<Blog> blogList) {
        this.blogList = blogList;
    }
}
