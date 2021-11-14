package com.jit.AgriIn.ui.view.culture;

import com.jit.AgriIn.model.response.DownLogResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

/**
 * @author crazyZhangxl on 2018/10/11.
 * Describe:
 */
public interface LogDdListAtView  extends BaseView {

    void queryDdListSuccess(List<DownLogResponse> downLogResponseList);

    void queryDdListFailure(String error);

    void delDdListSuccess();

    void delDdListFailure(String error);
}
