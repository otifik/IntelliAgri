package com.jit.AgriIn.ui.activity.video.api;

import com.videogo.openapi.bean.EZDeviceInfo;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

/**
 * Created by yuanyuan on 2019/1/15.
 */

public interface YSVideoView extends BaseView {
    void getTokenSuccess(String msg,TokenResponse.DataEntity dataEntity);

    void getTokenFailure(String msg);

    void getCameraListSuccess(List<EZDeviceInfo> deviceInfos);

    void getCameraListFailure(String msg);

}
