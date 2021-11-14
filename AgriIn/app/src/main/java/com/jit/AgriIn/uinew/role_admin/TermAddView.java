package com.jit.AgriIn.uinew.role_admin;

import com.jit.AgriIn.model.response.ManuInfo;
import com.jit.AgriIn.model.response.PageResponse;
import com.jit.AgriIn.model.response.RoleUserInfo;
import com.jit.AgriIn.model.response.TermResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe:
 */
public interface TermAddView extends BaseView {

    void addTermSuccess(TermResponse termResponse);

    void addTermFailure(String error);

    void getManuSuccess(List<ManuInfo> manuInfoList);

    void getManuFailure(String error);

    void queryCustomersSuccess(PageResponse<RoleUserInfo> customerInfoPageResponse);

    void queryCustomerFailure(String error);
}
