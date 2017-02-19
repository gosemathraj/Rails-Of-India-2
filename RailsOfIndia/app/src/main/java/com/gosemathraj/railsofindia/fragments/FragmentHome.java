package com.gosemathraj.railsofindia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.activities.HelperActivity;
import com.gosemathraj.railsofindia.utility.StringConstants;
import com.gosemathraj.railsofindia.utility.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iamsparsh on 14/2/17.
 */

public class FragmentHome extends Fragment implements View.OnClickListener{


    @BindView(R.id.pnr_linear) LinearLayout pnrLinear;
    @BindView(R.id.live_train_status_linear) LinearLayout trainStatusLinear;
    @BindView(R.id.train_route_linear) LinearLayout trainRouteLinear;
    @BindView(R.id.seat_availability_linear) LinearLayout seatAvailabilityLinear;
    @BindView(R.id.train_name_number_linear) LinearLayout trainNameNoLinear;

    private int fragmentId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,view);

        try{
            init();
        }catch(Exception e){
            Log.e(StringConstants.EXCEPTION,e.toString());
        }
        return view;
    }

    private void init() {

        setOnClickListener();
    }

    private void setOnClickListener() {

        pnrLinear.setOnClickListener(this);
        trainStatusLinear.setOnClickListener(this);
        trainRouteLinear.setOnClickListener(this);
        seatAvailabilityLinear.setOnClickListener(this);
        trainNameNoLinear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.pnr_linear){
            fragmentId = 0;
        }

        if(id == R.id.live_train_status_linear){
            fragmentId = 1;
        }

        if(id == R.id.train_route_linear){
            fragmentId = 2;
        }

        if(id == R.id.seat_availability_linear){
            fragmentId = 3;
        }

        if(id == R.id.train_name_number_linear){
            fragmentId = 9;
        }


        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.fragmentId),fragmentId);
        Utils.getInstance().startActivity(getActivity(),bundle, HelperActivity.class);
    }
}
