package com.jit.AgriIn.uinew.fourth;

import com.zxl.baselib.bean.response.BaseResponse;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 获得所有塘口信息的View层
 */
public interface SelfInfotView extends BaseView {

    void querySelfInfoSuccess(SelfInfoBean responses);
    void querySelfInfoFailure(String error);
    void updateInfoSuccess(BaseResponse response);
    void updateInfoFailure(String error);

    void postHeadSuccess(String imagePath);
    void postHeadFailure(String error);

}
