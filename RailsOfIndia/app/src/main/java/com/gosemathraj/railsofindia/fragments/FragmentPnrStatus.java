package com.gosemathraj.railsofindia.fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.adapters.PnrListAdapter;
import com.gosemathraj.railsofindia.data.Contract;
import com.gosemathraj.railsofindia.interfaces.OnResponseReceived;
import com.gosemathraj.railsofindia.models.Passengers;
import com.gosemathraj.railsofindia.models.PnrStatus;
import com.gosemathraj.railsofindia.retrofit.NetworkCallsRetrofitImpl;
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
 * Created by iamsparsh on 12/2/17.
 */
public class FragmentPnrStatus extends Fragment implements OnResponseReceived,LoaderManager.LoaderCallbacks<Cursor>{

    @BindView(R.id.train_no_or_name)
    EditText trainNoOrName;

    @BindView(R.id.ok)
    ImageView ok;

    @BindView(R.id.resultView)
    TextView resultView;

    @BindView(R.id.adView)
    AdView adView;

    @BindView(R.id.short_pnr_recyclerview)
    RecyclerView recyclerView;

    private View view;
    private CursorLoader cursorLoader;
    private List<PnrStatus> pnrStatusList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ServiceCalls.getInstance().setNetworkCallsimpl(new NetworkCallsRetrofitImpl());
        getActivity().getSupportLoaderManager().initLoader(1,null,this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_pnr_status,container,false);
        ButterKnife.bind(this,view);

        try{
            init();
        }catch(Exception e){
            Log.e(StringConstants.EXCEPTION,e.toString());
        }
        return view;
    }

    private void init() {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.pnr_title));
        initAdView();
        setOnClickListener();
    }

    private void initAdView() {

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(getString(R.string.testAdsDeviceId))
                .build();

        adView.loadAd(adRequest);
    }

    private void setOnClickListener() {

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Utils.getInstance().checkForNull(trainNoOrName.getText().toString())){

                    Utils.getInstance().showProgressDialog(getActivity(),StringConstants.LOADING_MESSAGE);
                    ServiceCalls.getInstance().getDataFromServer(getActivity(),FragmentPnrStatus.this,
                            Utils.getInstance().buildUrl(StringConstants.PNR_STATUS,
                                    new String[]{
                                            "pnr",
                                            "apikey"
                                    },new String[]{
                                            trainNoOrName.getText().toString(),
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
        try {
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject != null && jsonObject.getInt("response_code") == 200){

                PnrStatus pnrStatus = new PnrStatus();
                pnrStatus.setTrainStartDate(String.valueOf(jsonObject.getJSONObject("train_start_date").getInt("day")) +
                        String.valueOf(jsonObject.getJSONObject("train_start_date").getInt("month")) +
                        String.valueOf(jsonObject.getJSONObject("train_start_date").getInt("year")));
                pnrStatus.setClassLevel(jsonObject.getString("class"));
                pnrStatus.setTotalPassengers(jsonObject.getInt("total_passengers"));
                pnrStatus.setTrainName(jsonObject.getString("train_name"));
                pnrStatus.setReservationUpto(jsonObject.getJSONObject("reservation_upto").getString("name") + jsonObject.getJSONObject("reservation_upto").getString("name"));
                pnrStatus.setSourceStation(jsonObject.getJSONObject("from_station").getString("name") + jsonObject.getJSONObject("from_station").getString("code"));
                pnrStatus.setDestinationStation(jsonObject.getJSONObject("to_station").getString("name") + jsonObject.getJSONObject("to_station").getString("code"));
                pnrStatus.setBoardingStation(jsonObject.getJSONObject("boarding_point").getString("name") + jsonObject.getJSONObject("boarding_point").getString("code"));
                pnrStatus.setPassengers(setPassengersList(jsonObject.getJSONArray("passengers")));
                pnrStatus.setChartPrepared(jsonObject.getString("chart_prepared"));
                pnrStatus.setPnrNumber(jsonObject.getString("pnr"));
                pnrStatus.setTrainNumber(jsonObject.getString("train_num"));

                savePnrStatus(pnrStatus,jsonObject.getJSONArray("passengers").toString());

                Bundle bundle = new Bundle();
                bundle.putSerializable("pnrStatus",pnrStatus);

                FragmentPnrStatusDetails pnrStatusDetails = new FragmentPnrStatusDetails();
                pnrStatusDetails.setArguments(bundle);
                Utils.getInstance().replaceFragmentfromActivity(getActivity(),pnrStatusDetails,R.id.helper_frame_container);

            }else if(jsonObject.getInt("response_code") == 410){
                Log.d(StringConstants.SERVERRESPONSE,response);
                Utils.getInstance().showAlertDialog(getActivity(),getString(R.string.pnrError),getString(R.string.pnrNotGenerated));
            }else{
                Log.d(StringConstants.SERVERRESPONSE,response);
                Utils.getInstance().showAlertDialog(getActivity(),getString(R.string.pnrError),getString(R.string.serverNotResponding));
            }
        } catch (JSONException e) {
            Log.e(StringConstants.EXCEPTION,e.toString());
            Utils.getInstance().showAlertDialog(getActivity(),getString(R.string.pnrError),getString(R.string.something_wrong));
        }
    }

    private void savePnrStatus(PnrStatus pnrStatus,String passengers) {

        ContentValues values = new ContentValues();
        values.put(Contract.PnrEntry.COLUMN_TRAIN_START_DATE,pnrStatus.getTrainStartDate());
        values.put(Contract.PnrEntry.COLUMN_TRAIN_CLASS,pnrStatus.getClassLevel());
        values.put(Contract.PnrEntry.COLUMN_TOTAL_PASSENGERS,pnrStatus.getTotalPassengers());
        values.put(Contract.PnrEntry.COLUMN_TRAIN_NAME,pnrStatus.getTrainName());
        values.put(Contract.PnrEntry.COLUMN_RESERVATION_UPTO,pnrStatus.getReservationUpto());
        values.put(Contract.PnrEntry.COLUMN_SOURCE_STATION,pnrStatus.getSourceStation());
        values.put(Contract.PnrEntry.COLUMN_DESTINATION_STATION,pnrStatus.getDestinationStation());
        values.put(Contract.PnrEntry.COLUMN_BOARDING_STATION,pnrStatus.getBoardingStation());
        values.put(Contract.PnrEntry.COLUMN_PASSENGERS,passengers);
        values.put(Contract.PnrEntry.COLUMN_CHART_PREPARED,pnrStatus.getChartPrepared());
        values.put(Contract.PnrEntry.COLUMN_PNR_NUMBER,pnrStatus.getPnrNumber());
        values.put(Contract.PnrEntry.COLUMN_TRAIN_NUMBER,pnrStatus.getTrainNumber());

        Uri uri = getActivity().getContentResolver().insert(Contract.PnrEntry.CONTENT_URI, values);
        Toast.makeText(getActivity().getBaseContext(), "New record inserted", Toast.LENGTH_LONG)
                .show();
    }

    private List<Passengers> setPassengersList(JSONArray passengers) {

        List<Passengers> passengersList = new ArrayList<>();
        for(int i = 0;i < passengers.length();i++){

            Passengers passengers1 = new Passengers();
            try {
                passengers1.setCurrentStatus(passengers.getJSONObject(i).getString("current_status"));
                passengers1.setBookingStatus(passengers.getJSONObject(i).getString("booking_status"));
                passengers1.setCoachPOsition(passengers.getJSONObject(i).getInt("coach_position"));

                passengersList.add(passengers1);
            } catch (JSONException e) {
                Log.e(StringConstants.EXCEPTION,e.toString());
            }
        }
        return passengersList;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        cursorLoader = new CursorLoader(getActivity(),Contract.PnrEntry.CONTENT_URI,null,null,null,null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        data.moveToFirst();

        StringBuilder res = new StringBuilder();
        while (!data.isAfterLast()) {
            PnrStatus pnrStatus = new PnrStatus();
            try {

                pnrStatus.setPassengers(setPassengersList(new JSONArray(data.getString(data.getColumnIndex(Contract.PnrEntry.COLUMN_PASSENGERS)))));
                pnrStatus.setPnrNumber(data.getString(data.getColumnIndex(Contract.PnrEntry.COLUMN_PNR_NUMBER)));
                pnrStatus.setTrainNumber(data.getString(data.getColumnIndex(Contract.PnrEntry.COLUMN_TRAIN_NUMBER)));
                pnrStatus.setChartPrepared(data.getString(data.getColumnIndex(Contract.PnrEntry.COLUMN_CHART_PREPARED)));
                pnrStatus.setBoardingStation(data.getString(data.getColumnIndex(Contract.PnrEntry.COLUMN_BOARDING_STATION)));
                pnrStatus.setDestinationStation(data.getString(data.getColumnIndex(Contract.PnrEntry.COLUMN_DESTINATION_STATION)));
                pnrStatus.setSourceStation(data.getString(data.getColumnIndex(Contract.PnrEntry.COLUMN_SOURCE_STATION)));
                pnrStatus.setReservationUpto(data.getString(data.getColumnIndex(Contract.PnrEntry.COLUMN_RESERVATION_UPTO)));
                pnrStatus.setTrainName(data.getString(data.getColumnIndex(Contract.PnrEntry.COLUMN_TRAIN_NAME)));
                pnrStatus.setTotalPassengers(Integer.parseInt(data.getString(data.getColumnIndex(Contract.PnrEntry.COLUMN_TOTAL_PASSENGERS))));
                pnrStatus.setClassLevel(data.getString(data.getColumnIndex(Contract.PnrEntry.COLUMN_TRAIN_CLASS)));
                pnrStatus.setTrainStartDate(data.getString(data.getColumnIndex(Contract.PnrEntry.COLUMN_TRAIN_START_DATE)));
            }catch (JSONException e) {
                Log.e(StringConstants.EXCEPTION,e.toString());
            }
            pnrStatusList.add(pnrStatus);
            data.moveToNext();
        }
        setUpRecycerView();
    }

    private void setUpRecycerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new PnrListAdapter(getActivity(),pnrStatusList));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
