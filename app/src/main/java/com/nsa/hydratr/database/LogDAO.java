package com.nsa.hydratr.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.nsa.hydratr.model.WaterLog;

import java.util.List;

/**
 * Data access object for the model and database Classes
 * Created by c1712480 on 03/05/2018.
 */

@Dao
public interface LogDAO {

    @Query("SELECT * FROM WaterLog")
    LiveData<List<WaterLog>> getAllLogs();

    @Insert
    void insertLogs(WaterLog... log);
}
