package com.jit.AgriIn.model.response;

public class FishPondUpdateResponse {
    /**
     * 标示 对应生产单元的条目的ID
     */
    private int itemPosition;
    /**
     * 条目的具体数据
     */
    private FishPondResponse mFishPondResponse;

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public FishPondResponse getFishPondResponse() {
        return mFishPondResponse;
    }

    public void setFishPondResponse(FishPondResponse fishPondResponse) {
        mFishPondResponse = fishPondResponse;
    }
}
