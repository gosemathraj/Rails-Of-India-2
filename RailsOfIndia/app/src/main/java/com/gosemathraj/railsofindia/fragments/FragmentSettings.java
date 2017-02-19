package com.gosemathraj.railsofindia.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.data.Contract;
import com.gosemathraj.railsofindia.utility.StringConstants;
import com.gosemathraj.railsofindia.utility.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iamsparsh on 18/2/17.
 */

public class FragmentSettings extends Fragment {

    @BindView(R.id.clear_pnr)
    TextView clearPnr;

    @BindView(R.id.clear_notifications)
    TextView clearNotifications;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings,container,false);
        ButterKnife.bind(this,view);

        try{
            init();
        }catch(Exception e){
            Log.e(StringConstants.EXCEPTION,e.toString());
        }
        return view;
    }

    private void init() {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.settings));
        setOnClickListener();
    }

    private void setOnClickListener() {

        clearPnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPnrAlertDialog();
            }
        });

        clearNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showNotificationsAlertDialog();
            }
        });
    }

    private void showNotificationsAlertDialog() {

        AlertDialog dialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Clear Notifications");
        builder.setMessage("Do you really want to clear notifications ?");
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Utils.getInstance().clearSharedPreferences(getActivity(),"Notifications");
                Utils.getInstance().showToast(getActivity(),"All Notifications Cleared");
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    private void showPnrAlertDialog() {

        AlertDialog dialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Clear Saved Preferences");
        builder.setMessage("Do you really want to clear saved Pnrs ?");
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                getActivity().getContentResolver().delete(Contract.PnrEntry.CONTENT_URI,null,null);
                Utils.getInstance().showToast(getActivity(),"All Saved Pnrs Cleared");
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }
}
