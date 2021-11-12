package com.example.busapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MyBusStop.class}, version = 1)
public abstract class MyBusStopDB extends RoomDatabase {
    public abstract MyBusStopDao myBusStopDao();
}