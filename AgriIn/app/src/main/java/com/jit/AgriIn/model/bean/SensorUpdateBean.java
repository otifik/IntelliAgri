package com.jit.AgriIn.model.bean;

import com.jit.AgriIn.model.response.SensorResponse;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 用于更新生产单元的bean
 *
 */
public class SensorUpdateBean {
    /**
     * 标示 对应生产单元的条目的ID
     */
    private int itemPosition;
    /**
     * 条目的具体数据
     */
    private SensorResponse mSensorResponse;

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public SensorResponse getSensorResponse() {
        return mSensorResponse;
    }

    public void setSensorResponse(SensorResponse sensorResponse) {
        mSensorResponse = sensorResponse;
    }
}
