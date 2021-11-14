package com.jit.AgriIn.uinew.second.fishInput;

import com.jit.AgriIn.model.response.FishInputResponse;
import com.jit.AgriIn.model.response.FishPondResponse;
import com.zxl.baselib.bean.response.BaseResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

public interface FishInputAddView extends BaseView {
    void addFishInputSuccess(BaseResponse baseResponse);
    void addFishInputFailure(String error);

    void getAllUserPondsSuccess(List<FishPondResponse> baseResponseList);
    void getAllUserPondsFailure(String error);
}
