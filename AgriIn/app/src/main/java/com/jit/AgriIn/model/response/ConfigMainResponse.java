package com.jit.AgriIn.model.response;

import java.io.Serializable;

/**
 * @author crazyZhangxl on 2018/9/29.
 * Describe: 总体配置的Bean类
 */
public class ConfigMainResponse implements Serializable{

    /**
     * bird_status : 0
     * camera_depth : 0
     * circle : 0
     * cruise_velocity : 0
     * feed_name : string
     * feed_velocity : 0
     * feed_weight : 0
     * id : 0
     * medicine_name : string
     * medicine_velocity : 0
     * medicine_weight : 0
     * pound_id : 0
     * return_velocity : 0
     * robert_id : 0
     * sensor_depth : 0
     * type : 0
     */

    private float camera_depth;
    private float sensor_depth;
    private float medicine_weight;
    private float feed_weight;
    /**
     * 0 = 关 ； 1 =开
     */
    private int bird_status;
    private int circle;
    /**
     * 0 = 高速  ； 1 = 中速  2 = 低速
     */
    private int cruise_velocity;
    private int return_velocity;
    private int medicine_velocity;
    private String feed_name;
    private int id;
    private String medicine_name;
    private int pound_id;
    private int robert_id;
    private  String robert_name;
    private String pound_name;
    private int feed_velocity;
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRobert_name() {
        return robert_name;
    }

    public void setRobert_name(String robert_name) {
        this.robert_name = robert_name;
    }

    public String getPound_name() {
        return pound_name;
    }

    public void setPound_name(String pound_name) {
        this.pound_name = pound_name;
    }

    /**
     * 0 = 巡航 ;
     * 1= 巡航 and 投喂
     * 2 =  巡航 and 投喂 and 喷药
     */
    private int type;

    public int getBird_status() {
        return bird_status;
    }

    public void setBird_status(int bird_status) {
        this.bird_status = bird_status;
    }

    public float getCamera_depth() {
        return camera_depth;
    }

    public void setCamera_depth(float camera_depth) {
        this.camera_depth = camera_depth;
    }

    public int getCircle() {
        return circle;
    }

    public void setCircle(int circle) {
        this.circle = circle;
    }

    public int getCruise_velocity() {
        return cruise_velocity;
    }

    public void setCruise_velocity(int cruise_velocity) {
        this.cruise_velocity = cruise_velocity;
    }

    public String getFeed_name() {
        return feed_name;
    }

    public void setFeed_name(String feed_name) {
        this.feed_name = feed_name;
    }

    public int getFeed_velocity() {
        return feed_velocity;
    }

    public void setFeed_velocity(int feed_velocity) {
        this.feed_velocity = feed_velocity;
    }

    public float getFeed_weight() {
        return feed_weight;
    }

    public void setFeed_weight(float feed_weight) {
        this.feed_weight = feed_weight;
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

    public int getMedicine_velocity() {
        return medicine_velocity;
    }

    public void setMedicine_velocity(int medicine_velocity) {
        this.medicine_velocity = medicine_velocity;
    }

    public float getMedicine_weight() {
        return medicine_weight;
    }

    public void setMedicine_weight(float medicine_weight) {
        this.medicine_weight = medicine_weight;
    }

    public int getPound_id() {
        return pound_id;
    }

    public void setPound_id(int pound_id) {
        this.pound_id = pound_id;
    }

    public int getReturn_velocity() {
        return return_velocity;
    }

    public void setReturn_velocity(int return_velocity) {
        this.return_velocity = return_velocity;
    }

    public int getRobert_id() {
        return robert_id;
    }

    public void setRobert_id(int robert_id) {
        this.robert_id = robert_id;
    }

    public float getSensor_depth() {
        return sensor_depth;
    }

    public void setSensor_depth(float sensor_depth) {
        this.sensor_depth = sensor_depth;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
