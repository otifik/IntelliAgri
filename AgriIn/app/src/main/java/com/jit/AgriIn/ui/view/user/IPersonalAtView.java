package com.jit.AgriIn.ui.view.user;

import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 个人中心的V层
 */
public interface IPersonalAtView extends BaseView {

    void postHeadSuccess(String imagePath);

    void postHeadFailure(String error);

    void logoutSuccess();

}
