package com.jit.AgriIn.uinew.second.moban_d;

import com.jit.AgriIn.model.response.TypeTemplateResponse;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 获得所有塘口信息的View层
 */
public interface MobanView extends BaseView {
    void deleteMobanSuccess();
    void deleteMobanFailure(String error);

    void getMobanSuccess(TypeTemplateResponse templateResponse);
    void getMobanFailure(String error);

}
