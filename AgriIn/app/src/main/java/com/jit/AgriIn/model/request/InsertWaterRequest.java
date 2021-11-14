package com.jit.AgriIn.model.request;

/**
 * @author crazyZhangxl on 2018/9/30.
 * Describe:
 */
public class InsertWaterRequest {

    /**
     * alkali : string
     * date : 2018-09-30T05:27:36.030Z
     * id : 0
     * image : string
     * medicine : string
     * nano2 : string
     * nh : string
     * o2 : string *
     * ph : string *
     * pound_id : 0
     * remark : string
     * temperature : string *
     * username : string
     * weather : string
     */

    private String alkali;
    private String date;
    private String image;
    private String medicine;
    private String nano2;
    private String nh;
    private String o2;
    private String ph;
    private int pound_id;
    private String remark;
    private String temperature;
    private String username;
    private String weather;

    public String getAlkali() {
        return alkali;
    }

    public void setAlkali(String alkali) {
        this.alkali = alkali;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getNano2() {
        return nano2;
    }

    public void setNano2(String nano2) {
        this.nano2 = nano2;
    }

    public String getNh() {
        return nh;
    }

    public void setNh(String nh) {
        this.nh = nh;
    }

    public String getO2() {
        return o2;
    }

    public void setO2(String o2) {
        this.o2 = o2;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public int getPound_id() {
        return pound_id;
    }

    public void setPound_id(int pound_id) {
        this.pound_id = pound_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
