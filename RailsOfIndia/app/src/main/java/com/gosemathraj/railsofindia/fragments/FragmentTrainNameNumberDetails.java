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
import com.gosemathraj.railsofindia.adapters.TrainsDaysAdapter;
import com.gosemathraj.railsofindia.models.SeatAvailability;
import com.gosemathraj.railsofindia.models.TrainNameNumber;
import com.gosemathraj.railsofindia.utility.StringConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iamsparsh on 19/2/17.
 */

public class FragmentTrainNameNumberDetails extends Fragment {

    @BindView(R.id.train_name)
    TextView trainName;

    @BindView(R.id.train_number)
    TextView trainNumber;

    @BindView(R.id.train_days_recyclerview)
    RecyclerView recyclerview;

    private TrainNameNumber trainNameNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_train_no_name_details,container,false);
        ButterKnife.bind(this,view);

        try{
            init();
        }catch(Exception e){
            Log.e(StringConstants.EXCEPTION,e.toString());
        }

        return view;
    }

    private void init() {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.train_name_number_details));
        getData();
        setData();
    }

    private void setData() {

        trainName.setText(trainNameNumber.getTrainName());
        trainNumber.setText(trainNameNumber.getTrainNumber());

        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(new TrainsDaysAdapter(getActivity(),trainNameNumber.getDaysList()));
    }

    private void getData() {

        Bundle bundle = getArguments();
        if(bundle != null){
            trainNameNumber = (TrainNameNumber) bundle.getSerializable("trainNameNumber");
        }

    }
}
