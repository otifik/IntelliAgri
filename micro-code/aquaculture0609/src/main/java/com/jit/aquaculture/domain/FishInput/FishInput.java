package com.jit.aquaculture.domain.FishInput;

public class FishInput {
    Integer Id;
    String type;
    Double amount;
    String unit;
    String date;
    String pond;
    String batchNumber;
    String username;

    public FishInput() {
    }

    public FishInput(Integer id, String type, Double amount, String unit, String date, String pond, String batch_number, String username) {
        Id = id;
        this.type = type;
        this.amount = amount;
        this.unit = unit;
        this.date = date;
        this.pond = pond;
        this.batchNumber = batch_number;
        this.username = username;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPond() {
        return pond;
    }

    public void setPond(String pond) {
        this.pond = pond;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batch_number) {
        this.batchNumber = batch_number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "FishInput{" +
                "Id=" + Id +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", unit='" + unit + '\'' +
                ", date='" + date + '\'' +
                ", pond='" + pond + '\'' +
                ", batch_number='" + batchNumber + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
