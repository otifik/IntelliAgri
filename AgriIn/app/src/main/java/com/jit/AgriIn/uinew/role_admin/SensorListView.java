package com.jit.AgriIn.uinew.role_admin;

import com.jit.AgriIn.model.response.SensorResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 获得所有塘口信息的View层
 */
public interface SensorListView extends BaseView {
    void deleteSensorSuccess();
    void deleteSensorFailure(String error);

    void getSensorSuccess(List<SensorResponse> sensorResponseList);
    void getSensorFailure(String error);

}
