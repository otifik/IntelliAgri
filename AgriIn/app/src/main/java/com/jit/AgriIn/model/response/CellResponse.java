package com.jit.AgriIn.model.response;

import java.io.Serializable;

public class CellResponse implements Serializable {

    /**
     * id : 0
     * latitude : 0
     * length : 0
     * longitude : 0
     * name : string
     * product : string
     * type : string
     * username : string
     * width : 0
     */

    private int id;
    private double latitude;
    private double length;
    private double longitude;
    private String name;
    private String product;
    private String type;
    private String username;
    private double width;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}
