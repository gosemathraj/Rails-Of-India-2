package com.gosemathraj.railsofindia.services;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.gosemathraj.railsofindia.interfaces.NetworkCallsApi;

/**
 * Created by iamsparsh on 12/2/17.
 */

public class ServiceCalls {

    public static ServiceCalls serviceCalls;
    public NetworkCallsApi networkCallsimpl;

    public static ServiceCalls getInstance(){

        if(serviceCalls == null){
            serviceCalls = new ServiceCalls();
        }
        return serviceCalls;
    }

    public void setNetworkCallsimpl(NetworkCallsApi networkCallsimpl) {
        this.networkCallsimpl = networkCallsimpl;
    }

    public String getDataFromServer(Activity activity, Fragment fragment, String url, String callback){
        networkCallsimpl.getDataFromServer(activity,fragment,url,callback);
        return null;
    }
}
