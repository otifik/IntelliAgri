package com.jit.AgriIn.uinew.role_admin;

import com.jit.AgriIn.model.response.TypeTermResponse;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 获得所有塘口信息的View层
 */
public interface TermView extends BaseView {
    void getTermSuccess(TypeTermResponse typeTermResponse);
    void getTermFailure(String error);

    void deleteTermSuccess();
    void deleteTermFailure(String error);

}
