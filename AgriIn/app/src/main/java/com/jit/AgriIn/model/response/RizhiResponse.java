package com.jit.AgriIn.model.response;

import java.io.Serializable;

public class RizhiResponse implements Serializable {

    /**
     * content : string
     * id : 0
     * image : string
     * name : string
     * pond_id : 0
     * sysTime : 2020-01-09T02:36:45.493Z
     * username : string
     * video : string
     */

    private String content;
    private int id;
    private String image;
    private String name;
    private int pond_id;
    private String sysTime;
    private String username;
    private String video;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int getPond_id() {
        return pond_id;
    }

    public void setPond_id(int pond_id) {
        this.pond_id = pond_id;
    }

    public String getSysTime() {
        return sysTime;
    }

    public void setSysTime(String sysTime) {
        this.sysTime = sysTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
