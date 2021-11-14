package com.jit.AgriIn.ui.view.culture;

import com.jit.AgriIn.model.response.CultureLogResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

/**
 * @author crazyZhangxl on 2018/10/10.
 * Describe:
 */
public interface CulLogAtView  extends BaseView {

    void queryLogSuccess(List<CultureLogResponse> mCulLogList);

    void queryLogFailure(String error);
}
