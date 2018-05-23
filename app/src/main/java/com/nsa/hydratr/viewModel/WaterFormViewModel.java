package com.nsa.hydratr.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.nsa.hydratr.database.LogDatabase;
import com.nsa.hydratr.model.WaterLog;

import java.util.List;

/**
 * Completes Async database tasks and then provides the activity with the information from the database
 * Created by c1712480 on 03/05/2018.
 */

public class WaterFormViewModel extends AndroidViewModel {

    private MutableLiveData<List<WaterLog>> logs;
    private LogDatabase mDatabase;

    /**
     * Gets reference to the database
     * @param application
     */
    public WaterFormViewModel(Application application){
        super(application);
        mDatabase = LogDatabase.getDatabase(this.getApplication());
    }

    /**
     * Returns a LiveData list of all logs in the database
     * @return
     */
    public LiveData<List<WaterLog>> getLogs(){
        return mDatabase.logDao().getAllLogs();
    }

    /**
     * Inserts a log into the database
     * @param waterLog
     */
    public void insertLog(WaterLog waterLog){
        new insertAsyncTask(mDatabase).execute(waterLog);
    }

    /**
     * An async task for adding a log to the database to free up the  UI
     */
    private static class insertAsyncTask extends AsyncTask<WaterLog, Void, Void>{
        private LogDatabase db;

        insertAsyncTask(LogDatabase logDatabase){
            db = logDatabase;
        }

        @Override
        protected Void doInBackground(final WaterLog... params){
            db.logDao().insertLogs(params[0]);
            return null;
        }
    }
}