package com.jit.AgriIn.uinew.second.reagent;

import com.jit.AgriIn.model.response.FishInputResponse;
import com.jit.AgriIn.model.response.FishPondResponse;
import com.jit.AgriIn.model.response.InputResponse;
import com.jit.AgriIn.model.response.ReagentInputResponse;
import com.zxl.baselib.bean.response.BaseResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

public interface ReagentInputAddView extends BaseView {
    void addReagentSuccess(BaseResponse baseResponse);
    void addReagentFailure(String error);

    void getAllUserInputsSuccess(List<InputResponse> inputResponseList);
    void getAllUserInputsFailure(String error);

    void getAllUserFishInputSuccess(List<FishInputResponse> fishInputResponseList);
    void getAllUserFishInputFailure(String error);

    void getAllUserPondsSuccess(List<FishPondResponse> fishPondResponseList);
    void getAllUserPondsFailure(String error);
}