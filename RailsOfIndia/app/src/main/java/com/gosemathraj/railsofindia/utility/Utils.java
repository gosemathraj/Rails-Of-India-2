package com.gosemathraj.railsofindia.utility;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by iamsparsh on 12/2/17.
 */

public class Utils {

    public static Utils utils;

    public static Utils getInstance(){
        if(utils == null){
            utils = new Utils();
        }
        return utils;
    }

    public void addFragmentfromActivity(FragmentActivity activity, Fragment fragment, int id){
        activity.getSupportFragmentManager().beginTransaction()
                .add(id,fragment).commit();
    }

    public void replaceFragmentfromActivity(FragmentActivity activity, Fragment fragment, int id){
        activity.getSupportFragmentManager().beginTransaction()
                .add(id,fragment).commit();
    }

    public String buildUrl(String url, String[] urlKeys,String[] urlValues){
        for(int i = 0;i < urlKeys.length; i++){
            url = url + urlKeys[i] + "/" + urlValues[i] + "/";
        }
        return url;
    }

}
