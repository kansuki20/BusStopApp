package com.example.busapp.model;

import java.io.Serializable;

public class BusInfoItem implements Serializable {
    String busNo;
    String firstMin;
    String secondMin;

    public BusInfoItem() {

    }

    public BusInfoItem(String busNo, String firstMin, String secondMin) {
        this.busNo = busNo;
        this.firstMin = firstMin;
        this.secondMin = secondMin;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getFirstMin() {
        return firstMin;
    }

    public void setFirstMin(String firstMin) {
        this.firstMin = firstMin;
    }

    public String getSecondMin() {
        return secondMin;
    }

    public void setSecondMin(String secondMin) {
        this.secondMin = secondMin;
    }
}
