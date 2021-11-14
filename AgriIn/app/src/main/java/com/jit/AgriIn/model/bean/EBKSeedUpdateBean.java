package com.jit.AgriIn.model.bean;

import com.jit.AgriIn.model.response.BaikeSeedBean;

/**
 * @author crazyZhangxl on 2018/10/30.
 * Describe:
 */
public class EBKSeedUpdateBean {
    private int index;
    private BaikeSeedBean mBaikeSeedBean;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public BaikeSeedBean getBaikeSeedBean() {
        return mBaikeSeedBean;
    }

    public void setBaikeSeedBean(BaikeSeedBean baikeSeedBean) {
        mBaikeSeedBean = baikeSeedBean;
    }
}
