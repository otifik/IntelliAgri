package com.jit.AgriIn.model.response;

import java.io.Serializable;

public class EquipResponse implements Serializable {

    /**
     * addr : 0
     * cellid : 0
     * defname : string
     * id : 0
     * road : 0
     * status : 0
     * termid : 0
     * type : string
     */

    private int addr;
    private int cellid;
    private String defname;
    private int id;
    private int road;
    private int status;
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

    public String getDefname() {
        return defname;
    }

    public void setDefname(String defname) {
        this.defname = defname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoad() {
        return road;
    }

    public void setRoad(int road) {
        this.road = road;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
