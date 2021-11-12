package com.example.busapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BusStopDao {
    @Query("SELECT * FROM BusStop")
    List<BusStop> getAll();
    @Insert
    void insert(BusStop busStop);
    @Insert
    void insertAll(BusStop... busStops);
    @Delete
    void delete(BusStop busStop);
    @Query("DELETE FROM BusStop")
    void deleteAll();

}
