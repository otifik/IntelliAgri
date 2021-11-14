package com.jit.aquaculture.dto.addfood;

import com.jit.aquaculture.domain.FeedTemplate.FeedTemplate;
import com.jit.aquaculture.domain.addfood.AddFood;

import java.util.List;

public class AddFoodPageDto {
    private List<AddFood> addFoods;
    private Integer pageNum;
    private Integer pageSize;
    private Integer pages;
    private Integer total;

    public List<AddFood> getAddFoods() {
        return addFoods;
    }

    public void setAddFoods(List<AddFood> addFoods) {
        this.addFoods = addFoods;
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
