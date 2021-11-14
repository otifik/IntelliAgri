package com.jit.AgriIn.model.bean;

import com.jit.AgriIn.model.response.ConfigMainResponse;

/**
 * @author crazyZhangxl on 2018/9/29.
 * Describe:
 */
public class ConfigUpdateBean {
    private int index;
    private ConfigMainResponse mConfigMainResponse;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ConfigMainResponse getConfigMainResponse() {
        return mConfigMainResponse;
    }

    public void setConfigMainResponse(ConfigMainResponse configMainResponse) {
        mConfigMainResponse = configMainResponse;
    }
}
