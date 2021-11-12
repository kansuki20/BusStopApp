package com.example.busapp.model;

public class BusListItem {
    private String busStopName;
    private String busStopNumber;

    public BusListItem(String busStopName, String busStopNumber) {
        this.busStopName = busStopName;
        this.busStopNumber = busStopNumber;
    }

    public String getBusStopName() {
        return busStopName;
    }

    public void setBusStopName(String busStopName) {
        this.busStopName = busStopName;
    }

    public String getBusStopNumber() {
        return busStopNumber;
    }

    public void setBusStopNumber(String busStopNumber) {
        this.busStopNumber = busStopNumber;
    }
}
