package com.nsa.hydratr.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.nsa.hydratr.model.WaterLog;

/**
 * Database- interacts with the DAO
 * Created by c1712480 on 03/05/2018.
 */

@Database(entities = {WaterLog.class}, version = 3)
public abstract class LogDatabase extends RoomDatabase {
    private static LogDatabase INSTANCE;

    public static LogDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    LogDatabase.class, "log_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
    public abstract LogDAO logDao();
}
