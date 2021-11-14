package com.jit.AgriIn.model.response;

import java.io.Serializable;

/**
 * @author crazyZhangxl on 2018/10/11.
 * Describe:
 */
public class CultureLogResponse implements Serializable {

    /**
     * alkali : string
     * count1 : 0
     * count2 : 0
     * count3 : 0
     * count4 : 0
     * count5 : 0
     * count6 : 0
     * count_total : 0
     * date : 2018-10-11T00:42:58.997Z
     * id : 0
     * image : string
     * medicine : string
     * nano2 : string
     * nh : string
     * o2 : string
     * ph : string
     * pound_id : 0
     * remark : string
     * temperature : string
     * username : string
     * weather : string
     */

    private String alkali;
    private long count1;
    private long count2;
    private long count3;
    private long count4;
    private long count5;
    private long count6;
    private long count_total;
    private String date;
    private int id;
    private String image;
    private String medicine;
    private String nano2;
    private String nh;
    private String o2;
    private String ph;
    private long pound_id;
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

    public long getCount1() {
        return count1;
    }

    public void setCount1(long count1) {
        this.count1 = count1;
    }

    public long getCount2() {
        return count2;
    }

    public void setCount2(long count2) {
        this.count2 = count2;
    }

    public long getCount3() {
        return count3;
    }

    public void setCount3(long count3) {
        this.count3 = count3;
    }

    public long getCount4() {
        return count4;
    }

    public void setCount4(long count4) {
        this.count4 = count4;
    }

    public long getCount5() {
        return count5;
    }

    public void setCount5(long count5) {
        this.count5 = count5;
    }

    public long getCount6() {
        return count6;
    }

    public void setCount6(long count6) {
        this.count6 = count6;
    }

    public long getCount_total() {
        return count_total;
    }

    public void setCount_total(long count_total) {
        this.count_total = count_total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public long getPound_id() {
        return pound_id;
    }

    public void setPound_id(long pound_id) {
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
