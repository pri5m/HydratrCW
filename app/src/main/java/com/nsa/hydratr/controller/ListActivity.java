package com.nsa.hydratr.controller;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.nsa.hydratr.R;
import com.nsa.hydratr.model.WaterLog;
import com.nsa.hydratr.viewModel.ListViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Has a list of dates from when logs were added to the database
 * Created by c1712480 on 03/05/2018.
 */

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListViewCompat lv;
    ListViewModel mViewModel;
    private ArrayAdapter<String> la;
    private List<String> listOfDates;
    private List<WaterLog> listOfLogs;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listOfLogs = new ArrayList<>();
        listOfDates = new ArrayList<>();

        mViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        mViewModel.getLogs().observe(ListActivity.this, new android.arch.lifecycle.Observer<List<WaterLog>>() {
            @Override
            public void onChanged(@Nullable List<WaterLog> logs) {
                if(logs != null){
                    listOfLogs.addAll(logs);
                    populateListOfDates();
                    la.notifyDataSetChanged();
                }
            }
        });

        lv = findViewById(R.id.log_list);
        la = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                listOfDates);

        lv.setAdapter(la);
        lv.setOnItemClickListener(this);
    }

    /**
     * When a list item is selected a new activity is launched from it and contains the selected object
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        List<WaterLog> waterLogs = listOfLogs;
        Intent intent = new Intent(this, DataActivity.class);
        intent.putExtra("selectedLog", waterLogs.get(position));
        startActivity(intent);
    }

    /**
     * Populates a list of dates from a list of water log entries
     */
    private void populateListOfDates(){
        listOfDates.clear();
        if(listOfLogs != null){
            for(WaterLog waterLog : listOfLogs){
                String date = waterLog.getDate();
                listOfDates.add(date);
            }
        }
    }
}
