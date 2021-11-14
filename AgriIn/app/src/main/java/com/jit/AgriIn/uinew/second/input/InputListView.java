package com.jit.AgriIn.uinew.second.input;

import com.jit.AgriIn.model.response.FishPondResponse;
import com.jit.AgriIn.model.response.InputResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

public interface InputListView extends BaseView {
    void deleteInputSuccess();
    void deleteInputFailure(String error);

    void getInputSuccess(List<InputResponse> inputResponseList);
    void getInputFailure(String error);
}
