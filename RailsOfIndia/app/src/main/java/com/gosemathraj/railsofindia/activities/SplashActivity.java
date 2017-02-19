package com.gosemathraj.railsofindia.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.utility.Utils;

/**
 * Created by iamsparsh on 12/2/17.
 */

public class SplashActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Utils.getInstance().startActivity(SplashActivity.this,null,MainActivity.class);
                finish();
            }
        },3000);

    }
}
