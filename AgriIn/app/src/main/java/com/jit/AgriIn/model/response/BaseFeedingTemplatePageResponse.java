package com.jit.AgriIn.model.response;

import java.util.List;

public class BaseFeedingTemplatePageResponse<T> {
    private List<T> feedTemplates;
    private int pageNum;
    private int pageSize;
    private int pages;
    private int total;

    public List<T> getFeedTemplates() {
        return feedTemplates;
    }

    public void setFeedTemplates(List<T> feedTemplates) {
        this.feedTemplates = feedTemplates;
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
