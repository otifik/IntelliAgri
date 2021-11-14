package com.jit.AgriIn.model.bean;

import com.jit.AgriIn.model.response.RobotMainResponse;

/**
 * @author crazyZhangxl on 2018/9/28.
 * Describe:
 */
public class RobotUpdateBean {

    private int itemIndex;
    private RobotMainResponse mRobotMainResponse;

    public int getItemIndex() {
        return itemIndex;
    }

    public void setItemIndex(int itemIndex) {
        this.itemIndex = itemIndex;
    }

    public RobotMainResponse getRobotMainResponse() {
        return mRobotMainResponse;
    }

    public void setRobotMainResponse(RobotMainResponse robotMainResponse) {
        mRobotMainResponse = robotMainResponse;
    }
}
