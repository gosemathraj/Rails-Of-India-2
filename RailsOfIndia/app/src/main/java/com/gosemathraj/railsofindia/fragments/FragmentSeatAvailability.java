package com.gosemathraj.railsofindia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.interfaces.OnDateSet;
import com.gosemathraj.railsofindia.interfaces.OnResponseReceived;
import com.gosemathraj.railsofindia.models.Availability;
import com.gosemathraj.railsofindia.models.Passengers;
import com.gosemathraj.railsofindia.models.SeatAvailability;
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
 * Created by iamsparsh on 15/2/17.
 */

public class FragmentSeatAvailability extends Fragment implements OnResponseReceived,OnDateSet{


    @BindView(R.id.train_number)
    EditText trainNumber;

    @BindView(R.id.source_station)
    EditText sourceStation;

    @BindView(R.id.destination_station)
    EditText destinationStation;

    @BindView(R.id.pick_date)
    TextView pickDate;

    @BindView(R.id.quota)
    Spinner quota;

    @BindView(R.id.class_level)
    Spinner classLevel;

    @BindView(R.id.submit)
    ImageView submit;

    private String quotaString;
    private String classString;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_seat_availability,container,false);
        ButterKnife.bind(this,view);

        try{
            init();
        }catch(Exception e){
            Log.e(StringConstants.EXCEPTION,e.toString());
        }
        return view;
    }

    private void init() {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.seat_availability));
        setOnClickListener();
        initSpinnerData();
        onSpinnerItemSelected();
    }

    private void onSpinnerItemSelected() {
        quota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                quotaString = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        classLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                classString = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initSpinnerData() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.quota));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quota.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.category));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classLevel.setAdapter(adapter1);
    }

    private void setOnClickListener() {

        /*sourceStation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                findSuggestions(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
*/
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.setListener(FragmentSeatAvailability.this);
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Utils.getInstance().checkForNull(trainNumber.getText().toString(),
                sourceStation.getText().toString(),
                        destinationStation.getText().toString(),
                        pickDate.getText().toString())){

                    Utils.getInstance().showProgressDialog(getActivity(), StringConstants.LOADING_MESSAGE);
                    ServiceCalls.getInstance().getDataFromServer(getActivity(),FragmentSeatAvailability.this,
                            Utils.getInstance().buildUrl(StringConstants.SEAT_AVAILABILITY,
                                    new String[]{
                                            "train",
                                            "source",
                                            "dest",
                                            "date",
                                            "class",
                                            "quota",
                                            "apikey"
                                    },new String[]{
                                            trainNumber.getText().toString(),
                                            sourceStation.getText().toString(),
                                            destinationStation.getText().toString(),
                                            pickDate.getText().toString(),
                                            classString.split(":")[1],
                                            quotaString.split("-")[0],
                                            StringConstants.API_KEY
                                    }));
                }else{
                    Utils.getInstance().closeProgressDialog();
                    Utils.getInstance().showAlertDialog(getActivity(),getString(R.string.invalidInput),getString(R.string.invalidInputMsg));
                }
            }
        });
    }

    private void findSuggestions(String sequence) {

        ServiceCalls.getInstance().getDataFromServer(getActivity(),this,
                Utils.getInstance().buildUrl(StringConstants.SUGGEST_STATIONS,
                        new String[]{
                                "name",
                                "apikey"
                        },new String[]{
                                sequence,
                                StringConstants.API_KEY
                        }));
    }

    @Override
    public void onDateSet(String date) {
        pickDate.setText(date);
    }

    @Override
    public void onResponseReceivedFromServer(String response) {
        Utils.getInstance().closeProgressDialog();
        try{

            JSONObject jsonObject = new JSONObject(response);

            if(jsonObject != null && jsonObject.getInt("response_code") == 200){

                SeatAvailability seatAvailability = new SeatAvailability();

                seatAvailability.setTrainNumber(jsonObject.getString("train_number"));
                seatAvailability.setTrainName(jsonObject.getString("train_name"));
                seatAvailability.setClassName(jsonObject.getJSONObject("class").getString("class_name"));
                seatAvailability.setQuotaName(jsonObject.getJSONObject("quota").getString("quota_name"));
                seatAvailability.setAvailability(setSeatListAvailability(jsonObject.getJSONArray("availability")));
                seatAvailability.setSource(jsonObject.getJSONObject("from").getString("name"));
                seatAvailability.setDestination(jsonObject.getJSONObject("to").getString("name"));

                Bundle bundle = new Bundle();
                bundle.putSerializable(getString(R.string.seatAvailability),seatAvailability);

                FragmentSeatAvailabilityDetails fragmentSeatAvailabilityDetails = new FragmentSeatAvailabilityDetails();
                fragmentSeatAvailabilityDetails.setArguments(bundle);
                Utils.getInstance().replaceFragmentfromActivity(getActivity(),fragmentSeatAvailabilityDetails,R.id.helper_frame_container);
            }else{
                Log.d(StringConstants.SERVERRESPONSE,response);
                Utils.getInstance().showAlertDialog(getActivity(),getString(R.string.seatAvailabilityError),getString(R.string.serverNotResponding));
            }
        }catch(Exception e){
            Log.e(StringConstants.EXCEPTION,e.toString());
            Utils.getInstance().showAlertDialog(getActivity(),getString(R.string.seatAvailabilityError),getString(R.string.something_wrong));
        }
    }

    private List<Availability> setSeatListAvailability(JSONArray availability) {

        List<Availability> availabilityList = new ArrayList<>();
        for(int i = 0;i < availability.length();i++){

            Availability availability1 = new Availability();
            try {
                availability1.setDate(availability.getJSONObject(i).getString("date"));
                availability1.setStatus(availability.getJSONObject(i).getString("status"));

                availabilityList.add(availability1);
            } catch (JSONException e) {
                Log.e(StringConstants.EXCEPTION,e.toString());
            }
        }
        return availabilityList;
    }
}
