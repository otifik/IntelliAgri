package com.jit.AgriIn.uinew.second.template;

import com.jit.AgriIn.model.response.FeedingTemplateResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

public interface FeedingTemplateListView extends BaseView {
    void deleteFeedingTemplateSuccess();
    void deleteFeedingTemplateFailure(String error);

    void getFeedingTemplateSuccess(List<FeedingTemplateResponse> feedingTemplateResponsesList);
    void getFeedingTemplateFailure(String error);
}
