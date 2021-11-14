package com.jit.AgriIn.ui.view.config;

import com.jit.AgriIn.model.response.PondMainResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 获得所有塘口信息的View层
 */
public interface PondMainAtView extends BaseView {
    void queryPondSuccess(List<PondMainResponse> responses);

    void queryPondFailure(String error);

    void delPondSuccess(int itemPosition);

    void delPondFailure(String error);
}
