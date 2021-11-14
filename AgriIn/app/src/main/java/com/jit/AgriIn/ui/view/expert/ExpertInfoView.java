package com.jit.AgriIn.ui.view.expert;

import com.jit.AgriIn.model.response.PageResponse;
import com.jit.AgriIn.model.response.RoleUserInfo;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/10/12.
 * Describe: 百科View层
 */
public interface ExpertInfoView extends BaseView {

    void queryExpertInfoSuccess(PageResponse<RoleUserInfo> pageResponse);

    void queryExpertInfoFailure(String error);
}
