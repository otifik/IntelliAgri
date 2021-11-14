package com.jit.AgriIn.model.response;

import java.util.List;

public class BaseFeedingFoodPageResponse<T> {
    private List<T> addFoods;
    private int pageNum;
    private int pageSize;
    private int pages;
    private int total;

    public List<T> getAddFoods() {
        return addFoods;
    }

    public void setAddFoods(List<T> addFoods) {
        this.addFoods = addFoods;
    }

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
}
