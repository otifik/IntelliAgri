package com.jit.aquaculture.dto.Input;

import com.jit.aquaculture.domain.FishInput.FishInput;
import com.jit.aquaculture.domain.Input.input;


import java.util.List;

public class InputPageDto {
    private List<input> inputs;
    private Integer pageNum;
    private Integer pageSize;
    private Integer pages;
    private Integer total;

    public InputPageDto() {
    }

    public InputPageDto(List<input> inputs, Integer pageNum, Integer pageSize, Integer pages, Integer total) {
        this.inputs = inputs;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = pages;
        this.total = total;
    }

    public List<input> getInputs() {
        return inputs;
    }

    public void setInputs(List<input> inputs) {
        this.inputs = inputs;
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
