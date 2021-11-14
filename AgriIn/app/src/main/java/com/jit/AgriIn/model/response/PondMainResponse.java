package com.jit.AgriIn.model.response;

import java.io.Serializable;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 生产单元组信息的bean
 */
public class PondMainResponse implements Serializable{

    /**
     * depth : 0
     * id : 0
     * latitude1 : 0
     * latitude2 : 0
     * latitude3 : 0
     * latitude4 : 0
     * length : 0
     * location : string
     * longitude1 : 0
     * longitude2 : 0
     * longitude3 : 0
     * longitude4 : 0
     * max_depth : 0
     * name : string
     * toward : string
     * username : string
     * width : 0
     */

    private float depth;
    private int id;
    private float latitude1;
    private float latitude2;
    private float latitude3;
    private float latitude4;
    private float length;
    private String location;
    private float longitude1;
    private float longitude2;
    private float longitude3;
    private float longitude4;
    private float max_depth;
    private String name;
    private String toward;
    private String username;
    private float width;

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLatitude1() {
        return latitude1;
    }

    public void setLatitude1(float latitude1) {
        this.latitude1 = latitude1;
    }

    public float getLatitude2() {
        return latitude2;
    }

    public void setLatitude2(float latitude2) {
        this.latitude2 = latitude2;
    }

    public float getLatitude3() {
        return latitude3;
    }

    public void setLatitude3(float latitude3) {
        this.latitude3 = latitude3;
    }

    public float getLatitude4() {
        return latitude4;
    }

    public void setLatitude4(float latitude4) {
        this.latitude4 = latitude4;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getLongitude1() {
        return longitude1;
    }

    public void setLongitude1(float longitude1) {
        this.longitude1 = longitude1;
    }

    public float getLongitude2() {
        return longitude2;
    }

    public void setLongitude2(float longitude2) {
        this.longitude2 = longitude2;
    }

    public float getLongitude3() {
        return longitude3;
    }

    public void setLongitude3(float longitude3) {
        this.longitude3 = longitude3;
    }

    public float getLongitude4() {
        return longitude4;
    }

    public void setLongitude4(float longitude4) {
        this.longitude4 = longitude4;
    }

    public float getMax_depth() {
        return max_depth;
    }

    public void setMax_depth(float max_depth) {
        this.max_depth = max_depth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToward() {
        return toward;
    }

    public void setToward(String toward) {
        this.toward = toward;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
}
