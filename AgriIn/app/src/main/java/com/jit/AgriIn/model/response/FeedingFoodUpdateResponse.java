package com.jit.AgriIn.model.response;

public class FeedingFoodUpdateResponse {

    private int itemPosition;

    private FeedingFoodResponse feedingFoodResponse;

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public FeedingFoodResponse getFeedingFoodResponse() {
        return feedingFoodResponse;
    }

    public void setFeedingFoodResponse(FeedingFoodResponse feedingFoodResponse) {
        this.feedingFoodResponse = feedingFoodResponse;
    }
}
