package com.gosemathraj.railsofindia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gosemathraj.railsofindia.R;

/**
 * Created by iamsparsh on 18/2/17.
 */

public class FragmentAbout extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.about));
        return view;
    }
}
