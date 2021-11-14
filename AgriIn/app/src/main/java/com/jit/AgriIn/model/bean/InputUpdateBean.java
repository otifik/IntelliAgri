package com.jit.AgriIn.model.bean;

import com.jit.AgriIn.model.response.FishPondResponse;
import com.jit.AgriIn.model.response.InputResponse;

public class InputUpdateBean {

    private int itemPosition;

    private InputResponse mFishPondResponse;

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public InputResponse getmFishPondResponse() {
        return mFishPondResponse;
    }

    public void setmFishPondResponse(InputResponse mFishPondResponse) {
        this.mFishPondResponse = mFishPondResponse;
    }
}
