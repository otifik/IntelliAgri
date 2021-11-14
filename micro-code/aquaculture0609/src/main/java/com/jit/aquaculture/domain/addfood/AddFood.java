package com.jit.aquaculture.domain.addfood;

public class AddFood {
    private Integer id;
    private String name;
    private String batchNumber;
    private String pond;
    private String food;
    private Double amount;
    private String unit;
    private String time;
    private String remarks;
    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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
        return "AddFood{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", batchNumber='" + batchNumber + '\'' +
                ", pond='" + pond + '\'' +
                ", food='" + food + '\'' +
                ", amount=" + amount +
                ", unit='" + unit + '\'' +
                ", time='" + time + '\'' +
                ", reamarks='" + remarks + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
