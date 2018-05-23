package com.nsa.hydratr.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.nsa.hydratr.R;
import com.nsa.hydratr.model.WaterLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays information about a specific log entry
 * Created by c1712480 on 04/05/2018.
 */

public class DataActivity extends AppCompatActivity{

    List<WaterLog> waterLogs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        waterLogs = new ArrayList<>();
        Bundle data = getIntent().getExtras();
        WaterLog waterLog = data.getParcelable("selectedLog");
        waterLogs.add(waterLog);

        TextView numberText = findViewById(R.id.dataDate);
        numberText.setText(waterLog.getDate());

        String waterNumber = waterLogs.get(0).getLtr();
        TextView quantity = findViewById(R.id.number);
        quantity.setText(waterNumber + " ltr");

        TextView statement = findViewById(R.id.textStatement);
        statement.setText(setMessage());
    }

    /**
     * Sets a message according to which radio button the user had selected
     * @return String message
     */
    private String setMessage(){
        String message = null;
        WaterLog waterLog = waterLogs.get(0);
        if(waterLog.getNegative()){
            message = getResources().getString(R.string.i_did_not_drink_enough_today);
        }
        if(waterLog.getNeutral()){
            message = getResources().getString(R.string.i_drank_an_average_amount_today);
        }
        if(waterLog.getPositive()){
            message = getResources().getString(R.string.i_drank_lots_today);
        }
        return message;
    }
}
