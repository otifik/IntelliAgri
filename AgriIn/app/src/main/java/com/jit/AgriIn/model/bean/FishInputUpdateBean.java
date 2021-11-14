package com.jit.AgriIn.model.bean;

import com.jit.AgriIn.model.response.CellResponse;
import com.jit.AgriIn.model.response.FishInputResponse;

public class FishInputUpdateBean {

    private int itemPosition;

    private FishInputResponse fishInputResponse;

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public FishInputResponse getFishInputResponse() {
        return fishInputResponse;
    }

    public void setFishInputResponse(FishInputResponse fishInputResponse) {
        this.fishInputResponse = fishInputResponse;
    }
}
