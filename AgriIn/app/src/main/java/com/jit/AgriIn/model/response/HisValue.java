package com.jit.AgriIn.model.response;

import java.util.List;

public class HisValue {
    /**
     * snpid : string
     * times : [0]
     * values : [0]
     */

    private int snpid;
    private List<String> times;
    private List<Double> values;



    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }

    public int getSnpid() {
        return snpid;
    }

    public void setSnpid(int snpid) {
        this.snpid = snpid;
    }
}
