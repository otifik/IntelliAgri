package com.jit.AgriIn.uinew.second.feeding;

import com.jit.AgriIn.model.response.FeedingFoodResponse;
import com.jit.AgriIn.model.response.FeedingTemplateResponse;
import com.jit.AgriIn.model.response.InputResponse;
import com.zxl.baselib.bean.response.BaseResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

public interface FeedingFoodView extends BaseView {
    void addFeedingFoodSuccess(BaseResponse baseResponse);
    void addFeedingFoodFailure(String error);

    void getAllUserFeedingTemplateSuccess(List<FeedingTemplateResponse> feedingTemplateResponseList);
    void getAllUserFeedingTemplateFailure(String error);

    void getAllUserInputsSuccess(List<InputResponse> inputResponseList);
    void getAllUserInputsFailure(String error);
}
