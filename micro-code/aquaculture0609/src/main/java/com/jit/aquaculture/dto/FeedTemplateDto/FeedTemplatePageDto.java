package com.jit.aquaculture.dto.FeedTemplateDto;

import com.jit.aquaculture.domain.FeedTemplate.FeedTemplate;
import com.jit.aquaculture.domain.FishInput.FishInput;

import java.util.List;

public class FeedTemplatePageDto {
    private List<FeedTemplate> feedTemplates;
    private Integer pageNum;
    private Integer pageSize;
    private Integer pages;
    private Integer total;

    public List<FeedTemplate> getFeedTemplates() {
        return feedTemplates;
    }

    public void setFeedTemplates(List<FeedTemplate> feedTemplates) {
        this.feedTemplates = feedTemplates;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
