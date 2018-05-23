package com.nsa.hydratr.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.nsa.hydratr.database.LogDatabase;
import com.nsa.hydratr.model.WaterLog;

import java.util.List;

/**
 * Extracts information from the database and then packages it is a LiveData object which is then
 * sent to be used in the activity
 * Created by c1712480 on 03/05/2018.
 */

public class ListViewModel extends AndroidViewModel {

    private MutableLiveData<List<WaterLog>> logs;
    private LogDatabase mDatabase;

    /**
     * Gets reference to the database
     * @param application
     */
    public ListViewModel(@NonNull Application application) {
        super(application);
        mDatabase = LogDatabase.getDatabase(this.getApplication());
    }

    /**
     * Returns a LiveData list of all logs from the database
     * @return
     */
    public LiveData<List<WaterLog>> getLogs(){
        return mDatabase.logDao().getAllLogs();
    }
}
