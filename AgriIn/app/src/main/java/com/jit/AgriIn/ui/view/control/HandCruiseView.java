package com.jit.AgriIn.ui.view.control;

import com.jit.AgriIn.model.response.ConfigMainResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

/**
 * @author crazyZhangxl on 2018/9/29.
 * Describe:
 */
public interface HandCruiseView extends BaseView {
    void queryConfigSuccess(List<ConfigMainResponse> configMainResponseList);

    void queryConfigFailure(String error);
}
