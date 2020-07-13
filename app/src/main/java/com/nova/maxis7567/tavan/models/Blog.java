package com.nova.maxis7567.tavan.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Blog implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("image")
    private String image;
    @SerializedName("createdAt")
    private long date;
    @SerializedName("categoryTags")
    private List<Category> categoryList;
    @SerializedName("contentHtml")
    private String content;
    @SerializedName("bookmark")
    private boolean bookmark;

    public Blog(int id, String title, String image, long date) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.date = date;
        bookmark=false;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
