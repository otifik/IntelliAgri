package com.jit.AgriIn.model.response;

import java.util.List;

/**
 * @author crazyZhangxl on 2018/10/11.
 * Describe: 机器人含有分页的response Bean
 */
public class RobotPageResponse {

    /**
     * list : [{"address":"string","age":0,"category":"string","customer_id":0,"feed_name":"string","id":0,"medicine_name":"string","number":"string","registertime":"2018-10-11T01:08:58.715Z","sex":0,"tel":"string","type":"string","username":"string"}]
     * pageNum : 0
     * pageSize : 0
     * pages : 0
     * total : 0
     */

    private int pageNum;
    private int pageSize;
    private int pages;
    private int total;
    private List<RobotMainResponse> list;

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

    public List<RobotMainResponse> getList() {
        return list;
    }

    public void setList(List<RobotMainResponse> list) {
        this.list = list;
    }

}
