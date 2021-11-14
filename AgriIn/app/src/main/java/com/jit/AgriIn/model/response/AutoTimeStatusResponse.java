package com.jit.AgriIn.model.response;

import java.io.Serializable;

public class AutoTimeStatusResponse implements Serializable {

    /**
     * autofg : 0
     * cellid : 0
     * id : 0
     * opt : 0
     * param : string
     * time : string
     */

    private int autofg;
    private int cellid;
    private int id;
    private int opt;
    private String param;
    private String time;

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
}
