package com.jit.AgriIn.ui.view.expert_baike;

import com.jit.AgriIn.model.response.PageResponse;
import com.jit.AgriIn.model.response.RoleUserInfo;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/11/1.
 * Describe:专家用户的View
 */
public interface ECustomerFgView extends BaseView {

    void queryCustomersSuccess(PageResponse<RoleUserInfo> customerInfoPageResponse);

    void queryCustomerFailure(String error);
}
