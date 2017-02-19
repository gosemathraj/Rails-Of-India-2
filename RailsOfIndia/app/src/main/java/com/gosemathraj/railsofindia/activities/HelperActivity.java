package com.gosemathraj.railsofindia.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.fragments.FragmentAbout;
import com.gosemathraj.railsofindia.fragments.FragmentCancelledTrains;
import com.gosemathraj.railsofindia.fragments.FragmentFacts;
import com.gosemathraj.railsofindia.fragments.FragmentLiveTrainStatus;
import com.gosemathraj.railsofindia.fragments.FragmentPnrStatus;
import com.gosemathraj.railsofindia.fragments.FragmentRescheduledTrains;
import com.gosemathraj.railsofindia.fragments.FragmentSeatAvailability;
import com.gosemathraj.railsofindia.fragments.FragmentSettings;
import com.gosemathraj.railsofindia.fragments.FragmentTrainNameNumber;
import com.gosemathraj.railsofindia.fragments.FragmentTrainRoute;
import com.gosemathraj.railsofindia.utility.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iamsparsh on 16/2/17.
 */

public class HelperActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private int fragmentId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);
        ButterKnife.bind(this);

        try{
            init(savedInstanceState);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void init(Bundle savedInstanceState) {

        getIntentFromActivity();
        setToolbar();
        resolveFragment(savedInstanceState);
    }

    private void resolveFragment(Bundle savedInstanceState) {
        if(savedInstanceState == null){

            if(fragmentId == 0){
                Utils.getInstance().addFragmentfromActivity(this,new FragmentPnrStatus(),R.id.helper_frame_container);
            }
            if(fragmentId == 1){
                Utils.getInstance().addFragmentfromActivity(this,new FragmentLiveTrainStatus(),R.id.helper_frame_container);
            }
            if(fragmentId == 2){
                Utils.getInstance().addFragmentfromActivity(this,new FragmentTrainRoute(),R.id.helper_frame_container);
            }
            if(fragmentId == 3){
                Utils.getInstance().addFragmentfromActivity(this,new FragmentSeatAvailability(),R.id.helper_frame_container);
            }
            if(fragmentId == 4){
                Utils.getInstance().addFragmentfromActivity(this,new FragmentCancelledTrains(),R.id.helper_frame_container);
            }
            if(fragmentId == 5){
                Utils.getInstance().addFragmentfromActivity(this,new FragmentRescheduledTrains(),R.id.helper_frame_container);
            }
            if(fragmentId == 6){
                Utils.getInstance().addFragmentfromActivity(this,new FragmentFacts(),R.id.helper_frame_container);
            }
            if(fragmentId == 7){
                Utils.getInstance().addFragmentfromActivity(this,new FragmentSettings(),R.id.helper_frame_container);
            }
            if(fragmentId == 8){
                Utils.getInstance().addFragmentfromActivity(this,new FragmentAbout(),R.id.helper_frame_container);
            }
            if(fragmentId == 9){
                Utils.getInstance().addFragmentfromActivity(this,new FragmentTrainNameNumber(),R.id.helper_frame_container);
            }
        }

    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getIntentFromActivity() {

        if(getIntent() != null &&  getIntent().getExtras() != null){
            fragmentId = getIntent().getExtras().getInt("fragmentId");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Utils.getInstance().closeProgressDialog();
    }
}
