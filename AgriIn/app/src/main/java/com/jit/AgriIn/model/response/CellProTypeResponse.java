package com.jit.AgriIn.model.response;

import java.util.List;

public class CellProTypeResponse {

    /**
     * agprods : ["string"]
     * celltype : string
     * desc : string
     * id : 0
     */

    private String celltype;
    private String desc;
    private int id;
    private List<String> agprods;

    public String getCelltype() {
        return celltype;
    }

    public void setCelltype(String celltype) {
        this.celltype = celltype;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getAgprods() {
        return agprods;
    }

    public void setAgprods(List<String> agprods) {
        this.agprods = agprods;
    }
}
