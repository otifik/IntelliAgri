package com.jit.AgriIn.uinew.first.data;

import com.jit.AgriIn.model.response.CellProTypeResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe:
 */
public interface CellUpdateView extends BaseView {
    void updateFishPondSuccess();

    void updateFishPondFailure(String error);

//    void getFishPondProductSuccess(String[] FishPondList);
//
//    void getFishPondProductFailure(String error);

}
