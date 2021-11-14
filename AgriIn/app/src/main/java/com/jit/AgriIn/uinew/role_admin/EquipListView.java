package com.jit.AgriIn.uinew.role_admin;

import com.jit.AgriIn.model.response.EquipResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 获得所有塘口信息的View层
 */
public interface EquipListView extends BaseView {
    void deleteEquipSuccess();
    void deleteEquipFailure(String error);

    void getEquipSuccess(List<EquipResponse> equipResponseList);
    void getEquipFailure(String error);

}
