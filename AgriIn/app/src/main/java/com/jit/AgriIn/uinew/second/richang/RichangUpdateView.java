package com.jit.AgriIn.uinew.second.richang;

import com.jit.AgriIn.model.response.DailyThrowResponse;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe:
 */
public interface RichangUpdateView extends BaseView {
    void updateRichangSuccess(DailyThrowResponse dailyThrowResponse);
    void updateRichangFailure(String error);
}
