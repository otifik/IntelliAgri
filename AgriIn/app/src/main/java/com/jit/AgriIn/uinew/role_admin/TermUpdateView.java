package com.jit.AgriIn.uinew.role_admin;

import com.jit.AgriIn.model.response.PageResponse;
import com.jit.AgriIn.model.response.RoleUserInfo;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe:
 */
public interface TermUpdateView extends BaseView {
    void updateTermSuccess();
    void updateTermFailure(String error);

    void queryCustomersSuccess(PageResponse<RoleUserInfo> customerInfoPageResponse);

    void queryCustomerFailure(String error);
}
