package com.jit.AgriIn.model.response;

public class CtrlResponse {

    /**
     * equipid : 2
     * isctlsuss : false
     * msg : connClose
     */

    private int equipid;
    private boolean isctlsuss;
    private String msg;

    public int getEquipid() {
        return equipid;
    }

    public void setEquipid(int equipid) {
        this.equipid = equipid;
    }

    public boolean isIsctlsuss() {
        return isctlsuss;
    }

    public void setIsctlsuss(boolean isctlsuss) {
        this.isctlsuss = isctlsuss;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
