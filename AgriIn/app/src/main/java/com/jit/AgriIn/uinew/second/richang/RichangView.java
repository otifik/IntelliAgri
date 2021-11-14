package com.jit.AgriIn.uinew.second.richang;

import com.jit.AgriIn.model.response.TypeThrowResponse;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 获得所有塘口信息的View层
 */
public interface RichangView extends BaseView {
    void deleteRichangSuccess();
    void deleteRichangFailure(String error);

    void getRichangSuccess(TypeThrowResponse typeThrowResponse);
    void getRichangFailure(String error);

}
