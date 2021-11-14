package com.jit.AgriIn.model.response;

import java.io.Serializable;

public class IncomeResponse implements Serializable {

    /**
     * count : 0
     * id : 0
     * image : string
     * name : string
     * price : 0
     * remark : string
     * server : string
     * sysTime : 2020-06-19T07:57:50.083Z
     * tel : string
     * total : 0
     * type : 0
     * unit : string
     * username : string
     */

    private int count;
    private int id;
    private String image;
    private String name;
    private int price;
    private String remark;
    private String server;
    private String sysTime;
    private String tel;
    private int total;
    private int type;
    private String unit;
    private String username;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getSysTime() {
        return sysTime;
    }

    public void setSysTime(String sysTime) {
        this.sysTime = sysTime;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
