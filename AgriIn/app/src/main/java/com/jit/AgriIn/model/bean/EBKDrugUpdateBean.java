package com.jit.AgriIn.model.bean;

import com.jit.AgriIn.model.response.BaikeDrugBean;

/**
 * @author crazyZhangxl on 2018/10/30.
 * Describe:
 */
public class EBKDrugUpdateBean {
    private int index;
    private BaikeDrugBean mBaikeDrugBean;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public BaikeDrugBean getBaikeDrugBean() {
        return mBaikeDrugBean;
    }

    public void setBaikeDrugBean(BaikeDrugBean baikeDrugBean) {
        mBaikeDrugBean = baikeDrugBean;
    }
}
