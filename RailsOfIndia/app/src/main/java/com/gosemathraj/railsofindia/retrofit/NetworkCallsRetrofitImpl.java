package com.gosemathraj.railsofindia.retrofit;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.interfaces.NetworkCallsApi;
import com.gosemathraj.railsofindia.interfaces.OnResponseReceived;
import com.gosemathraj.railsofindia.interfaces.RetrofitApi;
import com.gosemathraj.railsofindia.utility.StringConstants;
import com.gosemathraj.railsofindia.utility.Utils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by iamsparsh on 12/2/17.
 */

public class NetworkCallsRetrofitImpl implements NetworkCallsApi{

    private Retrofit retrofit;
    private OnResponseReceived onResponseReceived;

    public NetworkCallsRetrofitImpl() {

        retrofit = new Retrofit.Builder()
                .baseUrl(StringConstants.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void getDataFromServer(final Activity activity, final Fragment fragment, String url) {

        if (Utils.getInstance().isNetworkConnectionAvailable(activity)) {

            RetrofitApi apiCalls = retrofit.create(RetrofitApi.class);
            Call<ResponseBody> call = apiCalls.getDataFromServer(url);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {
                        onResponseReceived = (OnResponseReceived) fragment;
                        if(response.body() != null){
                            onResponseReceived.onResponseReceivedFromServer(response.body().string());
                        }else if(response.errorBody() != null){
                            Utils.getInstance().showProgressDialog(activity,"Server error");
                        }
                    } catch (IOException e) {
                        Log.e(StringConstants.EXCEPTION,e.toString());
                    } catch(ClassCastException e){
                        Log.e(StringConstants.EXCEPTION,e.toString());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Utils.getInstance().closeProgressDialog();;
                    Utils.getInstance().showAlertDialog(activity,"Server error","Server not responding");
                }
            });

        }else{
            Utils.getInstance().showNoInternetAlertDialog(activity,activity.getString(R.string.no_internet),activity.getString(R.string.no_internet_info));
        }
    }
}
