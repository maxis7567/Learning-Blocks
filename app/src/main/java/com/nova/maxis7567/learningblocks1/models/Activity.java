package com.nova.maxis7567.learningblocks1.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Activity implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String desc;
    @SerializedName("image")
    private String image;
    @SerializedName("tags")
    private List<Category> tagList;
    @SerializedName("steps")
    private List<Step> stepList;
    @SerializedName("tips")
    private List<Tip> tipList;
    @SerializedName("bookmark")
    private boolean bookmark;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Category> getTagList() {
        return tagList;
    }

    public void setTagList(List<Category> tagList) {
        this.tagList = tagList;
    }

    public List<Step> getStepList() {
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }

    public List<Tip> getTipList() {
        return tipList;
    }

    public void setTipList(List<Tip> tipList) {
        this.tipList = tipList;
    }

    public boolean isBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }
}
