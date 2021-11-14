package com.jit.AgriIn.uinew.second.guding;

import com.jit.AgriIn.model.response.TypeIncomeResponse;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 获得所有塘口信息的View层
 */
public interface GudingView extends BaseView {

    void addIncomeSuccess();
    void addIncomeFailure(String error);
    void updateIncomeSuccess();
    void updateIncomeFailure(String error);
    void deleteIncomeSuccess();
    void deleteIncomeFailure(String error);

    void getIncomeSuccess(TypeIncomeResponse typeIncomeResponse);
    void getIncomeFailure(String error);

}
