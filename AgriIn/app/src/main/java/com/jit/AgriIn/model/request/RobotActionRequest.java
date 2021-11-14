package com.jit.AgriIn.model.request;

/**
 * @author crazyZhangxl on 2018/9/28.
 * Describe: 机器人操作的请求体
 */
public class RobotActionRequest {
    /**
     * customer_id : 0
     * id : 0
     * number : string
     * type : string
     */

    private int customer_id;
    private String number;
    private String type;

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
