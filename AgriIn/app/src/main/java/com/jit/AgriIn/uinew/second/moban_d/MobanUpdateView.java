package com.jit.AgriIn.uinew.second.moban_d;

import com.jit.AgriIn.model.response.TemplateResponse;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe:
 */
public interface MobanUpdateView extends BaseView {
    void updateMobanSuccess(TemplateResponse templateResponse);
    void updateMobanFailure(String error);

    void getErliaoTypeSuccess(String[] erliaoType);
    void getErliaoTypeFailure(String error);
}
