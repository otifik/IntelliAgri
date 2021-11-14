package com.jit.AgriIn.model.response;

import java.util.List;

public class BaseFishInputPageResponse<T> {

    private List<T> fishinputs;
    private int pageNum;
    private int pageSize;
    private int pages;
    private int total;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getFishinputs() {
        return fishinputs;
    }

    public void setFishinputs(List<T> fishinputs) {
        this.fishinputs = fishinputs;
    }
}
