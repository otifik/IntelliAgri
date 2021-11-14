package com.jit.aquaculture.dto.pond;
import com.jit.aquaculture.domain.pond.Pond;
import java.util.List;
public class PondPageDto {
    private List<Pond> ponds;
    private Integer pageNum;
    private Integer pageSize;
    private Integer pages;
    private Integer total;
    public PondPageDto() {
    }
    public List<Pond> getPonds() {
        return ponds;
    }
    public void setPonds(List<Pond> ponds) {
        this.ponds = ponds;
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
