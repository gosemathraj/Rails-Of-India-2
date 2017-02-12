package com.gosemathraj.railsofindia.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by iamsparsh on 11/2/17.
 */

public interface RetrofitApi {

    @GET
    Call<ResponseBody> getDataFromServer(@Url String url);
}
