package com.jit.AgriIn.uinew.first.data.autoctrl;

import com.jit.AgriIn.model.response.AutoStatusResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 获得所有塘口信息的View层
 */
public interface AutoCtrlView extends BaseView {
    void getAutoStatusSuccess(List<AutoStatusResponse> equipResponseList);
    void getAutoStatusFailure(String error);

    void ctrlAutoSuccess(Boolean isSucess);
    void ctrlAutoFailure(String error);
}
