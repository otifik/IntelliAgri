package com.jit.aquaculture.dto.Reagent;

import com.jit.aquaculture.domain.Reagent.Reagent;
import com.jit.aquaculture.domain.pond.Pond;

import java.util.List;

public class ReagentPageDto {

    private List<Reagent> reagents;
    private Integer pageNum;
    private Integer pageSize;
    private Integer pages;
    private Integer total;

    public ReagentPageDto() {
    }

    public ReagentPageDto(List<Reagent> reagents, Integer pageNum, Integer pageSize, Integer pages, Integer total) {
        this.reagents = reagents;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = pages;
        this.total = total;
    }

    public List<Reagent> getReagents() {
        return reagents;
    }

    public void setReagents(List<Reagent> reagents) {
        this.reagents = reagents;
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

    @Override
    public String toString() {
        return "ReagentPageDto{" +
                "reagents=" + reagents +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", pages=" + pages +
                ", total=" + total +
                '}';
    }
}
