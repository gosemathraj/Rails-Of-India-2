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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.interfaces.OnResponseReceived;
import com.gosemathraj.railsofindia.models.Route;
import com.gosemathraj.railsofindia.models.TrainRoute;
import com.gosemathraj.railsofindia.services.ServiceCalls;
import com.gosemathraj.railsofindia.utility.StringConstants;
import com.gosemathraj.railsofindia.utility.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iamsparsh on 15/2/17.
 */

public class FragmentTrainRoute extends Fragment implements OnResponseReceived{


    @BindView(R.id.train_number)
    EditText trainNumber;

    @BindView(R.id.submit)
    ImageView submit;

    @BindView(R.id.adView)
    AdView adView;

    private List<Route> routeList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_train_route,container,false);
        ButterKnife.bind(this,view);

        try{
            init();
        }catch(Exception e){
            Log.e(StringConstants.EXCEPTION,e.toString());
        }
        return view;
    }

    private void init() {
        initAdView();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.train_route));
        setOnCLickListener();
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

                    Utils.getInstance().showProgressDialog(getActivity(), StringConstants.LOADING_MESSAGE);
                    ServiceCalls.getInstance().getDataFromServer(getActivity(),FragmentTrainRoute.this,
                            Utils.getInstance().buildUrl(StringConstants.TRAIN_ROUTE,
                                    new String[]{
                                            "train",
                                            "apikey"
                                    },new String[]{
                                            trainNumber.getText().toString(),
                                            StringConstants.API_KEY
                                    }));

                }else{
                    Utils.getInstance().closeProgressDialog();
                    Utils.getInstance().showAlertDialog(getActivity(),getString(R.string.invalidInput),getString(R.string.invalidInput));
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

                TrainRoute trainRoute = new TrainRoute();
                trainRoute.setTrainNumber(jsonObject.getJSONObject("train").getString("number"));
                trainRoute.setTrainName(jsonObject.getJSONObject("train").getString("name"));
                for(int i = 0;i < jsonObject.getJSONArray("route").length();i++){

                    Route route = new Route();
                    route.setCode(jsonObject.getJSONArray("route").getJSONObject(i).getString("code"));
                    route.setDay(jsonObject.getJSONArray("route").getJSONObject(i).getInt("day"));
                    route.setFullname(jsonObject.getJSONArray("route").getJSONObject(i).getString("fullname"));
                    route.setLat(jsonObject.getJSONArray("route").getJSONObject(i).getDouble("lat"));
                    route.setLng(jsonObject.getJSONArray("route").getJSONObject(i).getDouble("lng"));
                    route.setScharr(jsonObject.getJSONArray("route").getJSONObject(i).getString("scharr"));
                    route.setSchdep(jsonObject.getJSONArray("route").getJSONObject(i).getString("schdep"));

                    routeList.add(route);
                }
                trainRoute.setRoute(routeList);

                Bundle bundle = new Bundle();
                bundle.putSerializable(getString(R.string.trainRoute),trainRoute);

                FragmentTrainRouteDetails fragmentTrainRouteDetails = new FragmentTrainRouteDetails();
                fragmentTrainRouteDetails.setArguments(bundle);

                Utils.getInstance().replaceFragmentfromActivity(getActivity(),fragmentTrainRouteDetails,R.id.helper_frame_container);
            }else{
                Log.d(StringConstants.SERVERRESPONSE,response);
                Utils.getInstance().showAlertDialog(getActivity(),getString(R.string.trainRouteError),getString(R.string.something_wrong));
            }
        }catch(Exception e){
            Log.e(StringConstants.EXCEPTION,e.toString());
        }
    }
}
