package com.gosemathraj.railsofindia.interfaces;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Created by iamsparsh on 12/2/17.
 */

public interface NetworkCallsApi {

    void getDataFromServer(Activity activity, Fragment fragment, String url, String Callback);
}
