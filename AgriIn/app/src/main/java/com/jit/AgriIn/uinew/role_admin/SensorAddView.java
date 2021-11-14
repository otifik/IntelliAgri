package com.jit.AgriIn.uinew.role_admin;

import com.jit.AgriIn.model.response.SensorResponse;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe:
 */
public interface SensorAddView extends BaseView {

    void addSensorSuccess(SensorResponse sensorResponse);

    void addSensorFailure(String error);

    void getDefSensorTypeSuccess(String[] sensorType);

    void getDefSensorTypeFailure(String error);
}
