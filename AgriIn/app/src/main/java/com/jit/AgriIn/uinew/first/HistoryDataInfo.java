package com.jit.AgriIn.uinew.first;

import java.util.ArrayList;

/**
 * Created by yuanyuan on 2017/6/1.
 */

public class HistoryDataInfo {
    public String chartName;
    public int lineCount;
    public ArrayList<String> xArray = new ArrayList<>();
    public ArrayList<Double> y1Array = new ArrayList<>();
    public ArrayList<Double> y2Array = new ArrayList<>();

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public ArrayList<String> getxArray() {
        return xArray;
    }

    public void setxArray(ArrayList<String> xArray) {
        this.xArray = xArray;
    }

    public ArrayList<Double> getY1Array() {
        return y1Array;
    }

    public void setY1Array(ArrayList<Double> y1Array) {
        this.y1Array = y1Array;
    }

    public ArrayList<Double> getY2Array() {
        return y2Array;
    }

    public void setY2Array(ArrayList<Double> y2Array) {
        this.y2Array = y2Array;
    }
}
