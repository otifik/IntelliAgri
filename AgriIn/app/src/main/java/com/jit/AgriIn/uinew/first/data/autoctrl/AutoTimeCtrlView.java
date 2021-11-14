package com.jit.AgriIn.uinew.first.data.autoctrl;

import com.jit.AgriIn.model.response.AutoTimeStatusResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 获得所有塘口信息的View层
 */
public interface AutoTimeCtrlView extends BaseView {
    void getAutoTimeStatusSuccess(List<AutoTimeStatusResponse> equipResponseList);
    void getAutoTimeStatusFailure(String error);

    void ctrlAutoTimeSuccess(Boolean isSucess);
    void ctrlAutoTimeFailure(String error);
}
