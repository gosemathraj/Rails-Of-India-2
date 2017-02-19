package com.gosemathraj.railsofindia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.adapters.SeatAvailabilityAdapter;
import com.gosemathraj.railsofindia.models.PnrStatus;
import com.gosemathraj.railsofindia.models.SeatAvailability;
import com.gosemathraj.railsofindia.utility.StringConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iamsparsh on 18/2/17.
 */

public class FragmentSeatAvailabilityDetails extends Fragment {

    @BindView(R.id.availability)
    RecyclerView recyclerView;

    @BindView(R.id.train_name)
    TextView trainName;

    @BindView(R.id.train_number)
    TextView trainNumber;

    @BindView(R.id.class_level)
    TextView classLevel;

    @BindView(R.id.category)
    TextView category;

    @BindView(R.id.source)
    TextView source;

    @BindView(R.id.destination)
    TextView destination;

    private SeatAvailability seatAvailability;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_seat_availability_details,container,false);
        ButterKnife.bind(this,view);

        try{
            init();
        }catch(Exception e){
            Log.e(StringConstants.EXCEPTION,e.toString());
        }
        return view;
    }

    private void init() {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.seat_availability_details));
        getData();
        setdata();
        setUpRecyclerview();
    }

    private void setUpRecyclerview() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new SeatAvailabilityAdapter(getActivity(),seatAvailability.getAvailability()));
    }

    private void setdata() {

        trainName.setText(seatAvailability.getTrainName());
        trainNumber.setText(seatAvailability.getTrainNumber());
        classLevel.setText(seatAvailability.getClassCode());
        category.setText(seatAvailability.getQuotaCode());
        source.setText(seatAvailability.getSource());
        destination.setText(seatAvailability.getDestination());
    }

    private void getData() {

        Bundle bundle = getArguments();
        if(bundle != null){
            seatAvailability = (SeatAvailability) bundle.getSerializable(getString(R.string.seatAvailability));
        }
    }
}
