package com.jit.AgriIn.model.response;

/**
 * @author crazyZhangxl on 2018/11/8.
 * Describe: 设备状态返回的Bean信息
 */
public class RepairStateResponse {

    /**
     * description : string
     * id : 0
     * robert_id : 0
     * status : 0
     * technology_id : 0
     * time : 2018-11-08T05:13:36.073Z
     */

    private String description;
    private int id;
    private int robert_id;
    private int status;
    private int technology_id;
    private String time;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRobert_id() {
        return robert_id;
    }

    public void setRobert_id(int robert_id) {
        this.robert_id = robert_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTechnology_id() {
        return technology_id;
    }

    public void setTechnology_id(int technology_id) {
        this.technology_id = technology_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
