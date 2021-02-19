package com.nova.maxis7567.learningblocks1.models;

import com.google.gson.annotations.SerializedName;
public class Step {
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String desc;
    @SerializedName("file")
    private String file;
    @SerializedName("size_file")
    private String fileSize;
    @SerializedName("image1")
    private String image1;
    @SerializedName("image2")
    private String image2;
    @SerializedName("image3")
    private String image3;

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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
}
