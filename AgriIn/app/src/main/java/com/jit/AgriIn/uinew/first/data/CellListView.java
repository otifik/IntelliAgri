package com.jit.AgriIn.uinew.first.data;

import com.jit.AgriIn.model.response.CellResponse;
import com.jit.AgriIn.model.response.FishPondResponse;
import com.jit.AgriIn.model.response.PageResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 获得所有塘口信息的View层
 */
public interface CellListView extends BaseView {
    void deleteFishPondSuccess();
    void deleteFishPondFailure(String error);

    void getFishPondSuccess(List<FishPondResponse> FishPondResponseList);
    void getFishPondFailure(String error);

}
