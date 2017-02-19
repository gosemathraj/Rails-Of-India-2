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
import com.gosemathraj.railsofindia.adapters.PnrPassengersAdapter;
import com.gosemathraj.railsofindia.models.PnrStatus;
import com.gosemathraj.railsofindia.utility.StringConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iamsparsh on 18/2/17.
 */

public class FragmentPnrStatusDetails extends Fragment{

    @BindView(R.id.passengers_details_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.train_number)
    TextView trainNumber;

    @BindView(R.id.train_name)
    TextView trainName;

    @BindView(R.id.source)
    TextView source;

    @BindView(R.id.destination)
    TextView destination;

    @BindView(R.id.reserved_upto)
    TextView reservedUpto;

    private PnrStatus pnrStatus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pnr_status_details,container,false);
        ButterKnife.bind(this,view);

        try{
            init();
        }catch(Exception e){
            Log.e(StringConstants.EXCEPTION,e.toString());
        }
        return view;
    }

    private void init() {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.pnr_details));
        getPnrData();
        setData();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new PnrPassengersAdapter(getActivity(),pnrStatus.getPassengers()));
    }

    private void setData() {

        trainName.setText(pnrStatus.getTrainName());
        trainNumber.setText(pnrStatus.getTrainNumber());
        source.setText(pnrStatus.getSourceStation());
        destination.setText(pnrStatus.getDestinationStation());
        reservedUpto.setText(pnrStatus.getReservationUpto());
    }

    private void getPnrData() {

        Bundle bundle = getArguments();
        if(bundle != null){
            pnrStatus = (PnrStatus) bundle.getSerializable(getString(R.string.pnrStatus));
        }
    }
}
