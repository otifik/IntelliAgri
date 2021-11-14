package com.jit.AgriIn.model.response;

import java.io.Serializable;

public class TemplateResponse implements Serializable {

    /**
     * content : string
     * count : 0
     * endDate : 2020-01-13T06:16:56.137Z
     * id : 0
     * poundId : 0
     * price : 0
     * startDate : 2020-01-13T06:16:56.137Z
     * status : 0
     * sysTime : 2020-01-13T06:16:56.137Z
     * templateName : string
     * throwCount : 0
     * throwTime : string
     * username : string
     */

    private String content;
    private int count;
    private String endDate;
    private int id;
    private int poundId;
    private int price;
    private String startDate;
    private int status;
    private String sysTime;
    private String templateName;
    private int throwCount;
    private String throwTime;
    private String username;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoundId() {
        return poundId;
    }

    public void setPoundId(int poundId) {
        this.poundId = poundId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSysTime() {
        return sysTime;
    }

    public void setSysTime(String sysTime) {
        this.sysTime = sysTime;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public int getThrowCount() {
        return throwCount;
    }

    public void setThrowCount(int throwCount) {
        this.throwCount = throwCount;
    }

    public String getThrowTime() {
        return throwTime;
    }

    public void setThrowTime(String throwTime) {
        this.throwTime = throwTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
