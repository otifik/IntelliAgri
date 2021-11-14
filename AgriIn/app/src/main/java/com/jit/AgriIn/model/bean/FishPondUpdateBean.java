package com.jit.AgriIn.model.bean;

import com.jit.AgriIn.model.response.FishPondResponse;

public class FishPondUpdateBean {

    private int itemPosition;

    private FishPondResponse fishPondResponse;

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public FishPondResponse getFishPondResponse() {
        return fishPondResponse;
    }

    public void setFishPondResponse(FishPondResponse fishPondResponse) {
        this.fishPondResponse = fishPondResponse;
    }
}
