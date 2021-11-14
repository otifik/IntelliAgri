package com.jit.AgriIn.uinew.first.data.log;

import com.jit.AgriIn.model.response.WarnLogResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 获得所有塘口信息的View层
 */
public interface WarnLogView extends BaseView {
    void getWarnLogSuccess(List<WarnLogResponse> equipResponseList);
    void getWarnLogFailure(String error);
}
