package com.nova.maxis7567.tavan.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
public class Package implements Serializable {
     @SerializedName("id")
    private int id;
     @SerializedName("title")
    private String title;
     @SerializedName("description")
    private List<Desc> descList;
     @SerializedName("color")
    private String color;
     @SerializedName("price")
    private int price;
     @SerializedName("salePrice")
    private int salePrice;
     @SerializedName("day")
    private String time;

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

    public List<Desc> getDescList() {
        return descList;
    }

    public void setDescList(List<Desc> descList) {
        this.descList = descList;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public static class Desc {
        @SerializedName("item")
        private String item;

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }
    }
}
