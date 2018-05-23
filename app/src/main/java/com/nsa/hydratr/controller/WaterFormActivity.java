package com.nsa.hydratr.controller;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nsa.hydratr.R;
import com.nsa.hydratr.database.LogDatabase;
import com.nsa.hydratr.model.WaterLog;
import com.nsa.hydratr.viewModel.WaterFormViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Has a form whereby the user can input their information for toady's date and then add it
 * to the database
 * Created by c1712480 on 03/05/2018.
 */

public class WaterFormActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener{

    private LogDatabase db;
    private WaterFormViewModel mViewModel;
    private List<WaterLog> listOfLogs;

    private TextView waterQuantity;
    private RadioButton negative;
    private RadioButton neutral;
    private RadioButton positive;
    private RadioGroup radioGroup;
    private SharedPreferences sharedPreferences;

    private Button submit;
    private Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_form);

        db = Room.databaseBuilder(
                getApplicationContext(),
                LogDatabase.class,
                "LogDatabase"
        ).build();

        waterQuantity = findViewById(R.id.waterQuantity);
        negative = findViewById(R.id.radioNegative);
        neutral = findViewById(R.id.radioNeutral);
        positive = findViewById(R.id.radioPositive);
        radioGroup = findViewById(R.id.radioGroup);
        sharedPreferences = getPreferences(MODE_PRIVATE);
        initValues();

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
        reset = findViewById(R.id.reset);
        reset.setOnLongClickListener(this);

        listOfLogs = new ArrayList<>();

        mViewModel = ViewModelProviders.of(this).get(WaterFormViewModel.class);
        mViewModel.getLogs().observe(WaterFormActivity.this, new android.arch.lifecycle.Observer<List<WaterLog>>() {
            @Override
            public void onChanged(@Nullable List<WaterLog> logs) {
                if(logs != null){
                    listOfLogs.addAll(logs);
                }
            }
        });

        TextView dateDisplay = findViewById(R.id.date_display);
        showCurrentDate(dateDisplay);
    }

    /**
     * Adds the date to a text view to be displayed on the activity
     * @param view
     */
    public void showCurrentDate (TextView view){
        view.setText(getCurrentDate());
    }

    /**
     * Retrieves the current date from the phone
     * @return Returns the date as a string
     * Adapted from: https://stackoverflow.com/questions/40310773/android-studio-textview-show-date
     */
    public String getCurrentDate(){
        String dateString = null;
        if(Calendar.getInstance().getTime() != null){
            Date currentDate = Calendar.getInstance().getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            dateString = dateFormat.format(currentDate);
        }
        return dateString;
    }

    /**
     * When the submit button is pressed, shared preferences are remembered and a water log
     * object is added to the database.
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if(sharedPreferences != null){
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("edit_quantity", waterQuantity.getText().toString());
                    edit.putInt("radio_group", radioGroup.getCheckedRadioButtonId());
                    edit.apply();
                }
                addNewLogToDB();
                break;
            default:
                break;
        }
    }

    /**
     * Writes a WaterLog object to the Room database and produces a toast when successful
     */
    public void addNewLogToDB(){
        String date = getCurrentDate();
        String quantity = waterQuantity.getText().toString();
        Boolean negativeSelect = negative.isChecked();
        Boolean neutralSelect = neutral.isChecked();
        Boolean positiveSelect = positive.isChecked();

        mViewModel.insertLog(
                new WaterLog(date,
                        quantity,
                        negativeSelect,
                        neutralSelect,
                        positiveSelect)
        );
        Toast.makeText(this, "Submit successful", Toast.LENGTH_SHORT).show();
    }

    /**
     * When the reset button has been long pressed then the number input and radio buttons are
     * reset
     * @param view
     * @return
     */
    @Override
    public boolean onLongClick(View view) {
        switch(view.getId()){
            case R.id.reset:
                if(sharedPreferences != null){
                    sharedPreferences.edit().clear().apply();
                    Toast.makeText(this, "Options have been reset", Toast.LENGTH_SHORT).show();
                    initValues();
                    break;
                }
            default:
                break;
        }
        return true;
    }

    /**
     * Initial setup of default values of each of the widgets
     * Set up so that they are all false or empty
     * Used on reset to change the values back to their default
     */
    private void initValues(){
        if(sharedPreferences != null){
            waterQuantity.setText(sharedPreferences.getString("edit_quantity", ""));
            negative.setChecked(sharedPreferences.getBoolean("radio_negative", false));
            neutral.setChecked(sharedPreferences.getBoolean("radio_neutral", false));
            positive.setChecked(sharedPreferences.getBoolean("radio_positive", false));
            int radioButtonChecked = sharedPreferences.getInt("radio_group", -1);
            if(radioButtonChecked == -1) {
                this.radioGroup.clearCheck();
            }else{
                ((AppCompatRadioButton) this.radioGroup.findViewById(radioButtonChecked)).setChecked(true);
            }
        }
    }

    /**
     * Unregisters the listener when the user leaves the activity
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (this.sharedPreferences != null)
            this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    /**
     * Registers a listener object when the user returns to the activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (this.sharedPreferences != null)
            this.sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    /**
     * Allows shared preference change listeners to be applied
     * @param sharedPreferences
     * @param key
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }
}
