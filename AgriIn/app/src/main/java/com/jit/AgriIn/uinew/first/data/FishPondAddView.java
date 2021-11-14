package com.jit.AgriIn.uinew.first.data;

import com.jit.AgriIn.model.response.FishPondResponse;
import com.zxl.baselib.bean.response.BaseResponse;
import com.zxl.baselib.ui.base.BaseView;

public interface FishPondAddView extends BaseView {
    void addFishPondSuccess(BaseResponse baseResponse);
    void addFishPondFailure(String error);
}
