package com.jit.AgriIn.uinew.second.guding;

import com.jit.AgriIn.model.response.IncomeResponse;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe:
 */
public interface GudingAddView extends BaseView {

    void addGudingSuccess(IncomeResponse incomeResponse);

    void addGudingFailure(String error);

    void getGudingTypeSuccess(String [] gudingType);

    void getGudingTypeFailure(String error);

    void getErliaoTypeSuccess(String[] erliaoType);
    void getErliaoTypeFailure(String error);

    void getMedicineTypeSuccess(String[] erliaoType);
    void getMedicineTypeFailure(String error);
}
