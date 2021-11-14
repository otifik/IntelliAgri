package com.jit.AgriIn.model.bean;

import com.jit.AgriIn.model.response.BaikeFeedBean;

/**
 * @author crazyZhangxl on 2018/10/30.
 * Describe:
 */
public class EBKFeedUpdateBean {
    private int index;
    private BaikeFeedBean mBaikeFeedBean;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public BaikeFeedBean getBaikeFeedBean() {
        return mBaikeFeedBean;
    }

    public void setBaikeFeedBean(BaikeFeedBean baikeFeedBean) {
        mBaikeFeedBean = baikeFeedBean;
    }
}
