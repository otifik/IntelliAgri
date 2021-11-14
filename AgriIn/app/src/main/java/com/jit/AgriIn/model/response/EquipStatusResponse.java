package com.jit.AgriIn.model.response;

import java.io.Serializable;

public class EquipStatusResponse implements Serializable {

    /**
     * epid : 0
     * name : string
     * status : 0
     * time : 2020-05-22T02:06:51.689Z
     * type : string
     */

    private int epid;
    private String name;
    private int status;
    private String time;
    private String type;

    public int getEpid() {
        return epid;
    }

    public void setEpid(int epid) {
        this.epid = epid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
