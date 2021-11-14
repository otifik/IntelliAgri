package com.jit.AgriIn.uinew.second.reagent;

import com.jit.AgriIn.model.response.InputResponse;
import com.jit.AgriIn.model.response.ReagentInputResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

public interface ReagentInputListView extends BaseView {
    void deleteReagentInputSuccess();
    void deleteReagentInputFailure(String error);

    void getReagentInputSuccess(List<ReagentInputResponse> reagentInputResponseList);
    void getReagentInputFailure(String error);
}
