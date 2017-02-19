package com.gosemathraj.railsofindia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gosemathraj.railsofindia.activities.MapsActivity;
import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.adapters.TrainRouteAdapter;
import com.gosemathraj.railsofindia.models.TrainRoute;
import com.gosemathraj.railsofindia.utility.StringConstants;
import com.gosemathraj.railsofindia.utility.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iamsparsh on 17/2/17.
 */

public class FragmentTrainRouteDetails extends Fragment {

    @BindView(R.id.train_route_recyclerview)
    RecyclerView trainRouteRecyclerView;

    @BindView(R.id.map_route)
    FloatingActionButton fab;

    private TrainRoute trainRoute;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         View view = inflater.inflate(R.layout.fragment_train_route_details,container,false);
        ButterKnife.bind(this,view);

        try{
            init();
        }catch(Exception e){
            Log.e(StringConstants.EXCEPTION,e.toString());
        }

        return view;
    }

    private void init() {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.train_route_details));
        getData();
        setUpRecyclerView();
        setOnClickListener();
    }

    private void setOnClickListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putSerializable(getString(R.string.trainRoute),trainRoute);

                Utils.getInstance().startActivity(getActivity(),bundle,MapsActivity.class);
            }
        });
    }

    private void setUpRecyclerView() {

        trainRouteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        trainRouteRecyclerView.setAdapter(new TrainRouteAdapter(getActivity(),trainRoute.getRoute()));
    }

    private void getData() {
        Bundle bundle = getArguments();
        if(bundle != null){
            trainRoute = (TrainRoute) bundle.getSerializable(getString(R.string.trainRoute));
        }
    }
}
