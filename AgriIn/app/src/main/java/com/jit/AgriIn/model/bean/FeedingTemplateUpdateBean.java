package com.jit.AgriIn.model.bean;

import com.jit.AgriIn.model.response.FeedingTemplateResponse;
import com.jit.AgriIn.model.response.ReagentInputResponse;

public class FeedingTemplateUpdateBean {
    private int itemPosition;

    private FeedingTemplateResponse feedingTemplateResponse;

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public FeedingTemplateResponse getFeedingTemplateResponse() {
        return feedingTemplateResponse;
    }

    public void setFeedingTemplateResponse(FeedingTemplateResponse feedingTemplateResponse) {
        this.feedingTemplateResponse = feedingTemplateResponse;
    }
}
