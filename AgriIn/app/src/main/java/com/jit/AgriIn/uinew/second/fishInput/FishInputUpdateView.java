package com.jit.AgriIn.uinew.second.fishInput;

import com.jit.AgriIn.model.response.FishPondResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

public interface FishInputUpdateView extends BaseView {
    void updateFishInputSuccess();

    void updateFishInputFailure(String error);

    void getAllUserPondsSuccess(List<FishPondResponse> baseResponseList);

    void getAllUserPondsFailure(String error);
}
