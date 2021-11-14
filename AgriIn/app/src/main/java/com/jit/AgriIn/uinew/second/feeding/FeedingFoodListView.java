package com.jit.AgriIn.uinew.second.feeding;

import com.jit.AgriIn.model.response.FeedingFoodResponse;
import com.jit.AgriIn.model.response.FishPondResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

public interface FeedingFoodListView extends BaseView {
    void deleteFeedingFoodSuccess();
    void deleteFeedingFoodFailure(String error);

    void getFeedingFoodSuccess(List<FeedingFoodResponse> feedingFoodResponseList);
    void getFeedingFoodFailure(String error);
}
