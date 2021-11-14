package com.jit.aquaculture.dto.FishInputDto;

import com.jit.aquaculture.domain.FishInput.FishInput;

import java.util.List;

public class FishInputPageDto {
    private List<FishInput> fishinputs;
    private Integer pageNum;
    private Integer pageSize;
    private Integer pages;
    private Integer total;
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<FishInput> getFishinputs() {
        return fishinputs;
    }

    public void setFishinputs(List<FishInput> fishinputs) {
        this.fishinputs = fishinputs;
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
}
