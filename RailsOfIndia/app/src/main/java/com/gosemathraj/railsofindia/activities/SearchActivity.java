package com.gosemathraj.railsofindia.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.adapters.TrainsDaysAdapter;
import com.gosemathraj.railsofindia.models.Days;
import com.gosemathraj.railsofindia.utility.StringConstants;
import com.gosemathraj.railsofindia.utility.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iamsparsh on 17/2/17.
 */

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.train_number)
    TextView trainNO;

    @BindView(R.id.train_name)
    TextView trainName;

    @BindView(R.id.train_days_recyclerview)
    RecyclerView recyclerview;

    private List<Days> daysList = new ArrayList<>();
    private String trainNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        try{
            init();
        }catch(Exception e){
            e.printStackTrace();;
        }

    }

    private void init() {
        setToolbar();
        getIntentData();
        getDetailsAboutTrain();
    }

    private void getDetailsAboutTrain() {

        new FetchTrainDetails().execute();
    }

    private void getIntentData() {

        if(getIntent() != null){
            trainNumber = getIntent().getExtras().getString(getString(R.string.search_String));
        }
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.results));
    }

    private class FetchTrainDetails extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String bufferString = null;

            try {
                URL url = new URL(StringConstants.Train_No_Or_Name + "train/" + trainNumber + "/apikey/" + StringConstants.API_KEY);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                bufferString = buffer.toString();
                return bufferString;
            } catch (IOException e) {
                Log.e("SearchActivity", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(getString(R.string.SearchActivity), getString(R.string.ErrorStream), e);
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            String x = s;
            try {
                JSONObject jsonObject = new JSONObject(x);
                if(jsonObject != null && jsonObject.getInt("response_code") == 200){

                    trainName.setText("Train Name : " + jsonObject.getJSONObject("train").getString("name"));
                    trainNO.setText("Train Number : " + jsonObject.getJSONObject("train").getString("number"));
                    setDaysList(jsonObject.getJSONObject("train").getJSONArray("days"));

                    recyclerview.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                    recyclerview.setAdapter(new TrainsDaysAdapter(SearchActivity.this,daysList));

                }else{
                    Log.d(StringConstants.SERVERRESPONSE,s);
                    Utils.getInstance().showAlertDialog(SearchActivity.this,getString(R.string.error),getString(R.string.something_wrong));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void setDaysList(JSONArray jsonArray) {

        for(int i = 0;i <jsonArray.length();i++){

            Days days = new Days();
            try {
                days.setDayCode(jsonArray.getJSONObject(i).getString("day-code"));
                days.setRuns(jsonArray.getJSONObject(i).getString("runs"));

                daysList.add(days);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
