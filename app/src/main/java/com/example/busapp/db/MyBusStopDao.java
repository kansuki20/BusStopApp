package com.example.busapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MyBusStopDao {
    @Query("SELECT * FROM mybusstop")
    List<MyBusStop> getAll();
    @Query("SELECT * FROM mybusstop WHERE busStopNumber LIKE :busStopNumber")
    MyBusStop getOne(String busStopNumber);
    @Insert
    void insert(MyBusStop myBusStop);
    @Query("DELETE FROM MyBusStop WHERE busStopNumber LIKE :busStopNumber")
    void deleteOne(String busStopNumber);
}
