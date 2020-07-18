package com.nova.maxis7567.learningblocks.models;

import java.util.Date;

public class RecentActivity {
    private int id;
    private Date lastViewedIn;
    private String name;
    private String description;
    private String image;

    public RecentActivity(Activity activity) {
        this.id = activity.getId();
        this.lastViewedIn = new Date();
        this.name = activity.getTitle();
        this.description = activity.getDesc();
        this.image = activity.getImage();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getLastViewedIn() {
        return lastViewedIn;
    }

    public void setLastViewedIn(Date lastViewedIn) {
        this.lastViewedIn = lastViewedIn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
