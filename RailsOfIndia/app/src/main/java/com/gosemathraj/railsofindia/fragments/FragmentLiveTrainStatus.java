package com.gosemathraj.railsofindia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.interfaces.OnResponseReceived;
import com.gosemathraj.railsofindia.services.ServiceCalls;
import com.gosemathraj.railsofindia.utility.StringConstants;
import com.gosemathraj.railsofindia.utility.Utils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iamsparsh on 13/2/17.
 */

public class FragmentLiveTrainStatus extends Fragment implements OnResponseReceived{

    @BindView(R.id.train_number)
    EditText trainNumber;

    @BindView(R.id.submit)
    ImageView submit;

    @BindView(R.id.current_status_value)
    TextView currentStatus;

    @BindView(R.id.current_station)
    TextView currentStation;

    @BindView(R.id.adView)
    AdView adView;


    private View view;
    private String currentDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_live_train_status,container,false);
        ButterKnife.bind(this,view);

        try{
            init();
        }catch(Exception e){
            Log.e(StringConstants.EXCEPTION,e.toString());
        }
        return view;
    }

    private void init() {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.train_status_title));
        setOnCLickListener();
        getTodaysDate();
        initAdView();
    }

    private void getTodaysDate() {
        currentDate = new SimpleDateFormat(getString(R.string.date_format)).format(new Date());
    }

    private void initAdView() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(getString(R.string.testAdsDeviceId))
                .build();

        adView.loadAd(adRequest);
    }

    private void setOnCLickListener() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Utils.getInstance().checkForNull(trainNumber.getText().toString())){

                    Utils.getInstance().showProgressDialog(getActivity(),StringConstants.LOADING_MESSAGE);
                    ServiceCalls.getInstance().getDataFromServer(getActivity(),FragmentLiveTrainStatus.this,
                            Utils.getInstance().buildUrl(StringConstants.LIVE_TRAIN_STATUS,
                                    new String[]{
                                            "train",
                                            "doj",
                                            "apikey"
                                    },new String[]{
                                            trainNumber.getText().toString(),
                                            currentDate,
                                            StringConstants.API_KEY
                                    }));

                }else{
                    Utils.getInstance().closeProgressDialog();
                    Utils.getInstance().showAlertDialog(getActivity(),getString(R.string.invalidInput),getString(R.string.invalidInputMsg));
                }
            }
        });
    }

    @Override
    public void onResponseReceivedFromServer(String response) {

        Utils.getInstance().closeProgressDialog();
        try{

            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject != null && jsonObject.getInt("response_code") == 200){

                currentStatus.setText(jsonObject.getString("position"));
                currentStation.setText(jsonObject.getJSONObject("current_station").getJSONObject("station_").getString("name"));

            }else if(jsonObject.getInt("response_code") == 510){
                Log.d(StringConstants.SERVERRESPONSE,response);
                Utils.getInstance().showAlertDialog(getActivity(),getString(R.string.liveStatusError),getString(R.string.liveStatusErrorMsg));
            }else{
                Log.d(StringConstants.SERVERRESPONSE,response);
                Utils.getInstance().showAlertDialog(getActivity(),getString(R.string.liveStatusError),getString(R.string.something_wrong));
            }
        }catch(Exception e){
            Log.e(StringConstants.EXCEPTION,e.toString());
        }
    }
}
