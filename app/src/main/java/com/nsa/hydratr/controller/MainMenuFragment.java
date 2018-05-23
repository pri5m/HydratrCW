package com.nsa.hydratr.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nsa.hydratr.R;

/**
 * Manages the interaction of the user with interface components
 * Created by c1712480 on 03/05/2018.
 */

public class MainMenuFragment extends Fragment implements View.OnClickListener{
    private Button buttonNavForm;
    private Button buttonViewResults;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_main_menu, container, false);
        buttonNavForm = v.findViewById(R.id.buttonNavForm);
        buttonNavForm.setOnClickListener(this);
        buttonViewResults = v.findViewById(R.id.buttonViewResults);
        buttonViewResults.setOnClickListener(this);
        return v;
    }

    /**
     * When a button is clicked by the user it takes them to the relevant activity
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonNavForm:
                Intent intentForm = new Intent(getActivity(), WaterFormActivity.class);
                startActivity(intentForm);
                break;
            case R.id.buttonViewResults:
                Intent intentList = new Intent(getActivity(), ListActivity.class);
                startActivity(intentList);
                break;
        }
    }
}
