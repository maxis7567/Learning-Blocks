package com.nova.maxis7567.learningblocks1.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubCat {
    @SerializedName("subCategories")
    private List<Category> categoryList;
    @SerializedName("activities")
    private List<Activity> activityList;
    @SerializedName("bookmark")
    private boolean bookmark;

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public boolean isBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }
}
