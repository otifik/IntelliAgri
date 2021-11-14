package com.jit.AgriIn.model.bean;

public class QuestionBean {

    /**
     * description : string
     * id : 0
     * image : string
     * publishTime : 2019-06-11T08:29:38.931Z
     * type : 0
     * username : string
     */

    private String description;
    private int id;
    private String image;
    private String publishTime;
    private int type;
    private String username;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
