package com.jit.AgriIn.model.response;

public class AutoLogResponse {

    /**
     * cellid : 0
     * id : 0
     * opt : 0
     * param : string
     * time : 2020-09-03T04:09:17.508Z
     * value : 0
     */

    private int cellid;
    private int id;
    private int opt;
    private String param;
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

    public int getOpt() {
        return opt;
    }

    public void setOpt(int opt) {
        this.opt = opt;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
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
