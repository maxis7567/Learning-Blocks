package com.nova.maxis7567.learningblocks1.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Category  implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("image")
    private String image;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String desc;
    @SerializedName("activityCount")
    private int counter;
    private boolean bookmark;

    public Category(int id, String image, String name, String desc, int counter) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.desc = desc;
        this.counter = counter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public boolean isBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }
}
