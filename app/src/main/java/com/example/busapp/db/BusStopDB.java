package com.example.busapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {BusStop.class}, version = 1)
public abstract class BusStopDB extends RoomDatabase {
    public abstract BusStopDao busStopDao();
}