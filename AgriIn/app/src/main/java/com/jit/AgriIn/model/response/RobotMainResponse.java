package com.jit.AgriIn.model.response;

import java.io.Serializable;

/**
 * @author crazyZhangxl on 2018/9/28.
 * Describe: 机器人管理的总体Bean
 */
public class RobotMainResponse implements Serializable {
    /**
     * address : string
     * age : 0
     * category : string
     * customer_id : 0
     * feed_name : string
     * id : 0
     * medicine_name : string
     * number : string
     * registertime : 2018-09-28T07:28:04.921Z
     * sex : 0
     * tel : string
     * type : string
     * username : string
     */

    private String address;
    private int age;
    private String category;
    private int customer_id;
    private String feed_name;
    private int id;
    private String medicine_name;
    private String number;
    private String registertime;
    private int sex;
    private String tel;
    private String type;
    private String username;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getFeed_name() {
        return feed_name;
    }

    public void setFeed_name(String feed_name) {
        this.feed_name = feed_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRegistertime() {
        return registertime;
    }

    public void setRegistertime(String registertime) {
        this.registertime = registertime;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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
}
