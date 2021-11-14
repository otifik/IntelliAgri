package com.jit.AgriIn.model.response;

import java.io.Serializable;

public class ReagentInputResponse implements Serializable {
    private int id;
    private String batchNumber;
    private String pond;
    private String reagent;
    private double amount;
    private String unit;
    private String time;
    private String remarks;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getPond() {
        return pond;
    }

    public void setPond(String pond) {
        this.pond = pond;
    }

    public String getReagent() {
        return reagent;
    }

    public void setReagent(String reagent) {
        this.reagent = reagent;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ReagentInputResponse{" +
                "id=" + id +
                ", batchNumber='" + batchNumber + '\'' +
                ", pond='" + pond + '\'' +
                ", reagent='" + reagent + '\'' +
                ", amount=" + amount +
                ", unit='" + unit + '\'' +
                ", time='" + time + '\'' +
                ", remarks='" + remarks + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
