package com.jit.AgriIn.model.response;

public class OrgResponse {
    /**
     * snpid : 169
     * type : temp
     * typeCHN : 空气温度
     * suffix : ℃
     * name : 温湿度
     */

    private int snpid;
    private String type;
    private String typeCHN;
    private String suffix;
    private String name;

    public int getSnpid() {
        return snpid;
    }

    public void setSnpid(int snpid) {
        this.snpid = snpid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeCHN() {
        return typeCHN;
    }

    public void setTypeCHN(String typeCHN) {
        this.typeCHN = typeCHN;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * name : string
     * snpid : 0
     * type : string
     * typeCHN : string
     */


}
