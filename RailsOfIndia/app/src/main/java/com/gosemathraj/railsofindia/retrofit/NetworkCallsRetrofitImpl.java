package com.gosemathraj.railsofindia.retrofit;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.gosemathraj.railsofindia.interfaces.NetworkCallsApi;
import com.gosemathraj.railsofindia.interfaces.RetrofitApi;
import com.gosemathraj.railsofindia.utility.StringConstants;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    @Override
    public void getDataFromServer(final Activity activity, final Fragment fragment, String url, final String Callback) {

        retrofit = new Retrofit.Builder()
                .baseUrl(StringConstants.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi apiCalls = retrofit.create(RetrofitApi.class);

        Call<ResponseBody> call = apiCalls.getDataFromServer(url);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Method method = null;
                try {
                    method = fragment.getClass().getDeclaredMethod(
                            Callback, new Class[]{String.class});
                    method.invoke(fragment, response.body().string());
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(activity,"error ",Toast.LENGTH_LONG).show();
            }
        });
    }
}
