package com.jit.AgriIn.model.response;

import java.io.Serializable;

public class TermResponse implements Serializable {

    /**
     * deveui : string
     * id : 0
     * manu : string
     * name : string
     * product : string
     * status : 0
     * type : 0
     * username : string
     */

    private String deveui;
    private int id;
    private String manu;
    private String name;
    private String product;
    private int status;
    private int type;
    private String username;

    public String getDeveui() {
        return deveui;
    }

    public void setDeveui(String deveui) {
        this.deveui = deveui;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManu() {
        return manu;
    }

    public void setManu(String manu) {
        this.manu = manu;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
