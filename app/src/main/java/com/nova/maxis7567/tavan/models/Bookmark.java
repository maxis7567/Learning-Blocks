package com.nova.maxis7567.tavan.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Bookmark implements Serializable {
    @SerializedName("blogs")
    private List<Blog> blogList;
    @SerializedName("activities")
    private List<Activity> activityList;
    @SerializedName("categories")
    private List<Category> categoryList;

    public List<Blog> getBlogList() {
        return blogList;
    }

    public void setBlogList(List<Blog> blogList) {
        this.blogList = blogList;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
