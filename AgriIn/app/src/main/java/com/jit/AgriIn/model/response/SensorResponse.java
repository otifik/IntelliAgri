package com.jit.AgriIn.model.response;

import java.io.Serializable;

public class SensorResponse implements Serializable {

    /**
     * addr : 0
     * cellid : 0
     * id : 0
     * name : string
     * termid : 0
     * type : string
     */

    private int addr;
    private int cellid;
    private int id;
    private String name;
    private int termid;
    private String type;

    public int getAddr() {
        return addr;
    }

    public void setAddr(int addr) {
        this.addr = addr;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTermid() {
        return termid;
    }

    public void setTermid(int termid) {
        this.termid = termid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
