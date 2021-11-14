package com.jit.AgriIn.uinew.second.richang;

import com.jit.AgriIn.model.response.DailyThrowResponse;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe:
 */
public interface RichangAddView extends BaseView {

    void addRichangSuccess(DailyThrowResponse dailyThrowResponse);

    void addRichangFailure(String error);

    void getErliaoTypeSuccess(String[] erliaoType);
    void getErliaoTypeFailure(String error);

    void getMedicineTypeSuccess(String[] erliaoType);
    void getMedicineTypeFailure(String error);
}
