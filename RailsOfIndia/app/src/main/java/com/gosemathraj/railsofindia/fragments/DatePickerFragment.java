package com.gosemathraj.railsofindia.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;

import com.gosemathraj.railsofindia.interfaces.OnDateSet;

import java.util.Calendar;

/**
 * Created by iamsparsh on 15/2/17.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public DatePickerFragment() {
    }

    private OnDateSet onDateSet;
    public DatePickerFragment(Fragment fragment) {
        this.onDateSet = (OnDateSet) fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String date = String.valueOf(day) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(year);
        onDateSet.onDateSet(date);
    }
}
