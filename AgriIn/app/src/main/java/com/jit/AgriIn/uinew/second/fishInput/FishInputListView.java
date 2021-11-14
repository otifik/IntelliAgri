package com.jit.AgriIn.uinew.second.fishInput;

import com.jit.AgriIn.model.response.FishInputResponse;
import com.jit.AgriIn.model.response.FishPondResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

public interface FishInputListView extends BaseView {
    void getFishInputSuccess(List<FishInputResponse> fishInputResponseList);
    void getFishInputFailure(String error);

    void deleteFishInputSuccess();
    void deleteFishInputFailure(String error);

    void getFishPondSuccess(List<FishPondResponse> fishPondResponseList);
    void getFishPondFailure(String error);
}
