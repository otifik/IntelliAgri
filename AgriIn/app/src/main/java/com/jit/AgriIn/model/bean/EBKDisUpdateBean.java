package com.jit.AgriIn.model.bean;

import com.jit.AgriIn.model.response.BaikeDiseaseBean;

/**
 * @author crazyZhangxl on 2018/10/30.
 * Describe:
 */
public class EBKDisUpdateBean {
    private int index;
    private BaikeDiseaseBean mBaikeDiseaseBean;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public BaikeDiseaseBean getBaikeDiseaseBean() {
        return mBaikeDiseaseBean;
    }

    public void setBaikeDiseaseBean(BaikeDiseaseBean baikeDiseaseBean) {
        mBaikeDiseaseBean = baikeDiseaseBean;
    }
}
