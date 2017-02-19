package com.gosemathraj.railsofindia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.activities.SearchActivity;
import com.gosemathraj.railsofindia.adapters.TrainsDaysAdapter;
import com.gosemathraj.railsofindia.interfaces.OnResponseReceived;
import com.gosemathraj.railsofindia.models.Days;
import com.gosemathraj.railsofindia.models.TrainNameNumber;
import com.gosemathraj.railsofindia.services.ServiceCalls;
import com.gosemathraj.railsofindia.utility.StringConstants;
import com.gosemathraj.railsofindia.utility.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iamsparsh on 19/2/17.
 */

public class FragmentTrainNameNumber extends Fragment implements OnResponseReceived{

    @BindView(R.id.train_name_no)
    EditText trainNumber;

    @BindView(R.id.submit)
    ImageView submit;

    @BindView(R.id.adView)
    AdView adView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_train_no_name,container,false);
        ButterKnife.bind(this,view);

        try{
            init();
        }catch(Exception e){
            Log.e(StringConstants.EXCEPTION,e.toString());
        }

        return view;
    }

    private void init() {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.train_name_number));
        setOnCLickListener();
        initAdView();
    }

    private void setOnCLickListener() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Utils.getInstance().checkForNull(trainNumber.getText().toString())){

                    Utils.getInstance().showProgressDialog(getActivity(), StringConstants.LOADING_MESSAGE);
                    ServiceCalls.getInstance().getDataFromServer(getActivity(),FragmentTrainNameNumber.this,
                            Utils.getInstance().buildUrl(StringConstants.Train_No_Or_Name,
                                    new String[]{
                                            "train",
                                            "apikey"
                                    },new String[]{
                                            trainNumber.getText().toString(),
                                            StringConstants.API_KEY
                                    }));

                }else{
                    Utils.getInstance().closeProgressDialog();
                    Utils.getInstance().showAlertDialog(getActivity(),getString(R.string.invalidInput),getString(R.string.invalidInputMsg));
                }
            }
        });
    }

    private void initAdView() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(getString(R.string.testAdsDeviceId))
                .build();

        adView.loadAd(adRequest);
    }

    @Override
    public void onResponseReceivedFromServer(String response) {

        Utils.getInstance().closeProgressDialog();
        try {
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject != null && jsonObject.getInt("response_code") == 200){

                TrainNameNumber trainNameNumber = new TrainNameNumber();
                trainNameNumber.setTrainName(jsonObject.getJSONObject("train").getString("name"));
                trainNameNumber.setTrainNumber(jsonObject.getJSONObject("train").getString("number"));

                trainNameNumber.setDaysList(setDaysList(jsonObject.getJSONObject("train").getJSONArray("days")));

                Bundle bundle = new Bundle();
                bundle.putSerializable(getString(R.string.trainNameNumber),trainNameNumber);

                FragmentTrainNameNumberDetails fragmentTrainNameNumberDetails = new FragmentTrainNameNumberDetails();
                fragmentTrainNameNumberDetails.setArguments(bundle);

                Utils.getInstance().replaceFragmentfromActivity(getActivity(),fragmentTrainNameNumberDetails,R.id.helper_frame_container);

            }else{
                Log.d(StringConstants.SERVERRESPONSE,response);
                Utils.getInstance().showAlertDialog(getActivity(),getString(R.string.error),getString(R.string.something_wrong));
            }
        } catch (JSONException e) {
            Log.e(StringConstants.EXCEPTION,e.toString());
        }
    }

    private List<Days> setDaysList(JSONArray jsonArray) {

        List<Days> daysList = new ArrayList<>();
        for(int i = 0;i <jsonArray.length();i++){

            Days days = new Days();
            try {
                days.setDayCode(jsonArray.getJSONObject(i).getString("day-code"));
                days.setRuns(jsonArray.getJSONObject(i).getString("runs"));

                daysList.add(days);
            } catch (JSONException e) {
                Log.e(StringConstants.EXCEPTION,e.toString());
            }
        }
        return daysList;
    }
}
