package com.nova.maxis7567.learningblocks.models;


import com.google.gson.annotations.SerializedName;

public class Account {
     @SerializedName("permiume")
     private boolean premium;
     @SerializedName("expireTime")
     private long expireTime;
     @SerializedName("item")
     private String name;

     public boolean isPremium() {
          return premium;
     }

     public void setPremium(boolean premium) {
          this.premium = premium;
     }

     public long getExpireTime() {
          return expireTime;
     }

     public void setExpireTime(long expireTime) {
          this.expireTime = expireTime;
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }
}
