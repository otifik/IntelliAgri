package com.jit.aquaculture.domain.Reagent;

public class Reagent {
    Integer id;
    String unit;
    String batchNumber;
    String pond;
    String reagent;
    Double amount;
    String time;
    String remarks;
    String username;

    public Reagent() {
    }

    public Reagent(Integer id, String name, String batchNumber, String pond, String reagent, Double amount, String time, String remarks, String username) {
        this.id = id;
        this.unit = name;
        this.batchNumber = batchNumber;
        this.pond = pond;
        this.reagent = reagent;
        this.amount = amount;
        this.time = time;
        this.remarks = remarks;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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
        return "Reagent{" +
                "id=" + id +
                ", name='" + unit + '\'' +
                ", batchNumber='" + batchNumber + '\'' +
                ", pond='" + pond + '\'' +
                ", reagent='" + reagent + '\'' +
                ", amount=" + amount +
                ", time='" + time + '\'' +
                ", remarks='" + remarks + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
