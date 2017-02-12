package com.gosemathraj.railsofindia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.retrofit.NetworkCallsRetrofitImpl;
import com.gosemathraj.railsofindia.services.ServiceCalls;
import com.gosemathraj.railsofindia.utility.StringConstants;
import com.gosemathraj.railsofindia.utility.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iamsparsh on 12/2/17.
 */
public class PnrStatusFragment extends Fragment {

    EditText trainNoOrName;

    Button ok;

    private View view;
    private String[] urlKeys;
    private String[] urlValues;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ServiceCalls.getInstance().setNetworkCallsimpl(new NetworkCallsRetrofitImpl());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_pnr_status,container,false);

        trainNoOrName = (EditText) view.findViewById(R.id.train_no_or_name);
        ok = (Button) view.findViewById(R.id.ok);

        try{
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    setUrlKeysAndValues();
                    ServiceCalls.getInstance().getDataFromServer(getActivity(),PnrStatusFragment.this
                            ,Utils.getInstance().buildUrl(StringConstants.Train_No_Or_Name,urlKeys,urlValues),"onTrainInfoResponse");
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
        return view;
    }

    private void init() {



    }

    private void setUrlKeysAndValues() {

        urlKeys = new String[]{"train", "apikey"};
        urlValues = new String[]{trainNoOrName.getText().toString(), StringConstants.API_KEY};
    }

    public void onTrainInfoResponse(String response){

        String x = response;
        try {
            JSONObject jsonObject = new JSONObject(x);
            String name = jsonObject.getJSONObject("train").getString("name");
            String temp = jsonObject.getJSONObject("train").getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
