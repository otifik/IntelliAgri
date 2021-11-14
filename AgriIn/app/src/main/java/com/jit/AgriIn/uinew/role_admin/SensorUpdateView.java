package com.jit.AgriIn.uinew.role_admin;

import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe:
 */
public interface SensorUpdateView extends BaseView {
    void updateSensporSuccess();
    void updateSensorFailure(String error);

}
