package com.jit.AgriIn.model.bean;

import com.jit.AgriIn.model.response.BaikeProductBean;

/**
 * @author crazyZhangxl on 2018/10/30.
 * Describe:
 */
public class EBKProductUpdateBean {
    private int index;
    private BaikeProductBean mBaikeProductBean;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public BaikeProductBean getBaikeProductBean() {
        return mBaikeProductBean;
    }

    public void setBaikeProductBean(BaikeProductBean baikeProductBean) {
        mBaikeProductBean = baikeProductBean;
    }
}
