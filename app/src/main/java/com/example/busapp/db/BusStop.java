package com.example.busapp.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BusStop {
    @PrimaryKey @NonNull
    String busStopNumber;
    @ColumnInfo(name = "bus_stop_name")
    String busStopName;

    public BusStop(String busStopNumber, String busStopName) {
        this.busStopNumber = busStopNumber;
        this.busStopName = busStopName;
    }

    public String getBusStopNumber() {
        return busStopNumber;
    }

    public void setBusStopNumber(String busStopNumber) {
        this.busStopNumber = busStopNumber;
    }

    public String getBusStopName() {
        return busStopName;
    }

    public void setBusStopName(String busStopName) {
        this.busStopName = busStopName;
    }

    @Override
    public String toString() {
        return busStopName + " 정류장의 " + busStopNumber;
    }

}
