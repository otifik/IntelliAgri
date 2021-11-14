package com.jit.AgriIn.uinew.role_admin;

import com.jit.AgriIn.model.response.EquipResponse;
import com.jit.AgriIn.model.response.EquipType;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe:
 */
public interface EquipAddView extends BaseView {

    void addEquipSuccess(EquipResponse equipResponse);

    void addEquipFailure(String error);

    void getEquipTypeSuccess(List<EquipType> equipTypeList);

    void getEquipTypeFailure(String error);
}
