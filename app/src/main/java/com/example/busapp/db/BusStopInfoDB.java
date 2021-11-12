package com.example.busapp.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BusStopInfoDB {
    @ColumnInfo(name = "bus_stop_name")
    String busStopName;
    @PrimaryKey
    String busStopNumber;
}
