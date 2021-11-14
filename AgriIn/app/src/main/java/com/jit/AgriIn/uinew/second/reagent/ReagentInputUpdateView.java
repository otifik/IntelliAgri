package com.jit.AgriIn.uinew.second.reagent;

import com.zxl.baselib.ui.base.BaseView;

public interface ReagentInputUpdateView extends BaseView {
    void updateReagentInputSuccess();

    void updateReagentInputFailure(String error);
}
