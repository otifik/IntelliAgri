package com.jit.AgriIn.model.response;

public class WarnLogResponse {

    /**
     * cellid : 0
     * id : 0
     * isdown : true
     * param : string
     * sta : 0
     * time : 2020-09-03T02:01:20.972Z
     * value : 0
     */

    private int cellid;
    private int id;
    private boolean isdown;
    private String param;
    private int sta;
    private String time;
    private String value;

    public int getCellid() {
        return cellid;
    }

    public void setCellid(int cellid) {
        this.cellid = cellid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIsdown() {
        return isdown;
    }

    public void setIsdown(boolean isdown) {
        this.isdown = isdown;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public int getSta() {
        return sta;
    }

    public void setSta(int sta) {
        this.sta = sta;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
