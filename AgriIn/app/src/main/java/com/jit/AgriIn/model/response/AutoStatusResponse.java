package com.jit.AgriIn.model.response;

import java.io.Serializable;

public class AutoStatusResponse implements Serializable {


    /**
     * actdw : 0
     * actup : 0
     * autofg : 0
     * cellid : 0
     * id : 0
     * param : string
     * paramid : 0
     * wndw : 0
     * wnup : 0
     */

    private float actdw;
    private float actup;
    private int autofg;
    private int cellid;
    private int id;
    private String param;
    private int paramid;
    private float wndw;
    private float wnup;

    public float getActdw() {
        return actdw;
    }

    public void setActdw(float actdw) {
        this.actdw = actdw;
    }

    public float getActup() {
        return actup;
    }

    public void setActup(float actup) {
        this.actup = actup;
    }

    public int getAutofg() {
        return autofg;
    }

    public void setAutofg(int autofg) {
        this.autofg = autofg;
    }

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

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public int getParamid() {
        return paramid;
    }

    public void setParamid(int paramid) {
        this.paramid = paramid;
    }

    public float getWndw() {
        return wndw;
    }

    public void setWndw(float wndw) {
        this.wndw = wndw;
    }

    public float getWnup() {
        return wnup;
    }

    public void setWnup(float wnup) {
        this.wnup = wnup;
    }
}
