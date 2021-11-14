package com.jit.AgriIn.uinew.second.rizhi;

import com.jit.AgriIn.model.response.RizhiResponse;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe:
 */
public interface RizhiUpdateView extends BaseView {
    void updateRizhiSuccess(RizhiResponse rizhiResponse);
    void updateRizhiFailure(String error);

    void getRizhiTypeSuccess(String [] gudingType);
    void getRizhiTypeFailure(String error);
}
