package com.jit.AgriIn.ui.view.culture;

import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/10/11.
 * Describe:
 */
public interface LogFileAtView extends BaseView {

    void exportFileSuccess(String info);

    void exportFileFailure(String error);
}
