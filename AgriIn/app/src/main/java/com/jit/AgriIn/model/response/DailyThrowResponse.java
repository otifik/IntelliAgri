package com.jit.AgriIn.model.response;

import java.io.Serializable;

public class DailyThrowResponse implements Serializable {

    /**
     * breed : string
     * cellId : 0
     * count : 0
     * description : string
     * id : 0
     * image : string
     * name : string
     * position : string
     * price : 0
     * remark : string
     * safeCount : 0
     * server : string
     * sysTime : 2020-06-19T01:17:45.125Z
     * tel : string
     * total : 0
     * type : string
     * unit : string
     * username : string
     */

    private String breed;
    private int cellId;
    private int count;
    private String description;
    private int id;
    private String image;
    private String name;
    private String position;
    private int price;
    private String remark;
    private int safeCount;
    private String server;
    private String sysTime;
    private String tel;
    private int total;
    private String type;
    private String unit;
    private String username;

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getCellId() {
        return cellId;
    }

    public void setCellId(int cellId) {
        this.cellId = cellId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public int getSafeCount() {
        return safeCount;
    }

    public void setSafeCount(int safeCount) {
        this.safeCount = safeCount;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
