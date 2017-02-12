package com.gosemathraj.railsofindia.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.fragments.PnrStatusFragment;
import com.gosemathraj.railsofindia.utility.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){

            Utils.getInstance().addFragmentfromActivity(this,new PnrStatusFragment(),R.id.frame_container);
        }
    }
}
