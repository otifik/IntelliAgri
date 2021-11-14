package com.jit.aquaculture.domain.Input;

public class input {
    Integer id;
    String type;
    String name;
    String manufacture;
    String pictures;
    String remarks;
    String username;

    public input() {
    }

    public input(Integer id, String type, String name, String manufacture, String pictures, String remarks, String username) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.manufacture = manufacture;
        this.pictures = pictures;
        this.remarks = remarks;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "input{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", manufacture='" + manufacture + '\'' +
                ", pictures='" + pictures + '\'' +
                ", remarks='" + remarks + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
