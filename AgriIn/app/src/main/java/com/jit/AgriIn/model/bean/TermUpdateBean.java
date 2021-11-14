package com.jit.AgriIn.model.bean;

import com.jit.AgriIn.model.response.TermResponse;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 用于更新生产单元的bean
 *
 */
public class TermUpdateBean {
    /**
     * 标示 对应生产单元的条目的ID
     */
    private int itemPosition;
    /**
     * 条目的具体数据
     */
    private TermResponse mTermResponse;

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public TermResponse getTermResponse() {
        return mTermResponse;
    }

    public void setTermResponse(TermResponse termResponse) {
        mTermResponse = termResponse;
    }
}
