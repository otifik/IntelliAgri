package com.jit.AgriIn.model.bean;

import com.jit.AgriIn.model.response.PondMainResponse;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 用于更新生产单元的bean
 *
 */
public class PondUpdateBean {
    /**
     * 标示 对应生产单元的条目的ID
     */
    private int itemPosition;
    /**
     * 条目的具体数据
     */
    private PondMainResponse mPondMainResponse;

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public PondMainResponse getPondMainResponse() {
        return mPondMainResponse;
    }

    public void setPondMainResponse(PondMainResponse pondMainResponse) {
        mPondMainResponse = pondMainResponse;
    }
}
