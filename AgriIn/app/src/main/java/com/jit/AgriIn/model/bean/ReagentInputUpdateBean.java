package com.jit.AgriIn.model.bean;

import com.jit.AgriIn.model.response.InputResponse;
import com.jit.AgriIn.model.response.ReagentInputResponse;

public class ReagentInputUpdateBean {
    private int itemPosition;

    private ReagentInputResponse reagentInputResponse;

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public ReagentInputResponse getReagentInputResponse() {
        return reagentInputResponse;
    }

    public void setReagentInputResponse(ReagentInputResponse reagentInputResponse) {
        this.reagentInputResponse = reagentInputResponse;
    }
}
