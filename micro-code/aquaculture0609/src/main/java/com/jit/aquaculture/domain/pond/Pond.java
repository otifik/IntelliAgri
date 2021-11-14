package com.jit.aquaculture.domain.pond;

public class Pond {
    private Integer id;
    private String name ;
    private  Double length;
    private Double width;
    private  Double depth;
    private String orientation;
    private  Double longitude;
    private  Double latitude;
    private String address;
    private  String product;
    private String username;

    public Pond() {
    }

    public Pond(Integer id, String name, Double length, Double width, Double depth, String orientation, Double longitude, Double latitude, String address, String product, String username) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.width = width;
        this.depth = depth;
        this.orientation = orientation;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.product = product;
        this.username = username;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Pond{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", length=" + length +
                ", width=" + width +
                ", depth=" + depth +
                ", orientation='" + orientation + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", address='" + address + '\'' +
                ", product='" + product + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
