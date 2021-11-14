package com.jit.AgriIn.uinew.second.guding;

import com.jit.AgriIn.model.response.IncomeResponse;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe:
 */
public interface GudingUpdateView extends BaseView {
    void updateGudingSuccess(IncomeResponse incomeResponse);
    void updateGudingFailure(String error);

    void getGudingTypeSuccess(String [] gudingType);

    void getGudingTypeFailure(String error);
}
