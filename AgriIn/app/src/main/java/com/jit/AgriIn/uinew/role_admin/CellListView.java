package com.jit.AgriIn.uinew.role_admin;

import com.jit.AgriIn.model.response.CellResponse;
import com.jit.AgriIn.model.response.PageResponse;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/11/1.
 * Describe:专家用户的View
 */
public interface CellListView extends BaseView {

    void queryCellSuccess(PageResponse<CellResponse> cellResponseList);

    void queryCellFailure(String error);
}
