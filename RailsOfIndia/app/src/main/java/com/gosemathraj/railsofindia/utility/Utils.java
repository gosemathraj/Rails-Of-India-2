package com.gosemathraj.railsofindia.utility;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.activities.HelperActivity;
import com.gosemathraj.railsofindia.activities.MainActivity;
import com.gosemathraj.railsofindia.data.Contract;
import com.gosemathraj.railsofindia.models.TrainRoute;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by iamsparsh on 12/2/17.
 */

public class Utils {

    public static Utils utils;
    private ProgressDialog progressDialog;
    private Dialog customDialog;

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
                .replace(id,fragment).addToBackStack(null).commit();
    }

    public String buildUrl(String url, String[] urlKeys,String[] urlValues){
        for(int i = 0;i < urlKeys.length; i++){
            url = url + urlKeys[i] + "/" + urlValues[i] + "/";
        }
        return url;
    }

    public void showProgressDialog(Activity activity,String message){

        if(progressDialog != null && progressDialog.isShowing()){
            return;
        }
        if(progressDialog == null){
            progressDialog = new ProgressDialog(activity);
        }

        progressDialog.setMessage(message);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
    }

    public  void closeProgressDialog(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
            progressDialog = null;
        }else{
            progressDialog = null;
        }
    }

    public void showToast(Activity activity,String message){
        Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();
    }

    public void startActivity(Activity activity,Bundle bundle,Class activityToStart){
        Intent intent = new Intent(activity,activityToStart);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    public boolean checkForNull(String ...params){
        for(String temp :params){
            try {
                if (temp.length() <= 0 || temp.equals("") || temp == null) {
                    return false;
                }
            }catch(Exception e){
                Log.e(StringConstants.EXCEPTION,e.toString());
            }

        }
        return true;
    }

    public void saveDataInPreference(Context activity,String preferenceName, String data){

        SharedPreferences sharedPreferences = activity.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("notifications",data);
        editor.commit();
    }

    public String getDataFromPreference(Context activity,String preferenceName){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(preferenceName,Context.MODE_PRIVATE);
        String notifications = sharedPreferences.getString("notifications",null);

        return notifications;
    }

    public void clearSharedPreferences(Context activity,String preferenceName){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(preferenceName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

    }

    public String getPassengersString(String string) {

        String temp = null;
        String x = null;

        try {
            JSONArray jsonArray = new JSONArray(string);
            for(int i = 0;i < jsonArray.length();i++){
                temp = jsonArray.getJSONObject(i).getString("current_status") + jsonArray.getJSONObject(i).getString("booking_status") +"\n";
            }
        } catch (JSONException e) {
            Log.e(StringConstants.EXCEPTION,e.toString());
        }

        return temp;
    }

    public void showAlertDialog(Activity activity,String title,String message){

        AlertDialog dialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    public void openShareIntent(Activity activity,String subject,String message){

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
        activity.startActivity(Intent.createChooser(sharingIntent, activity.getResources().getString(R.string.share_using)));
    }

    public Cursor getDataCursor(Context context) {

        Cursor cursor = context.getContentResolver().query(
                Contract.PnrEntry.CONTENT_URI,
                new String[]{
                        Contract.PnrEntry.COLUMN_PNR_NUMBER,
                        Contract.PnrEntry.COLUMN_TRAIN_START_DATE,
                        Contract.PnrEntry.COLUMN_TRAIN_CLASS,
                        Contract.PnrEntry.COLUMN_TRAIN_NAME,
                        Contract.PnrEntry.COLUMN_RESERVATION_UPTO,
                        Contract.PnrEntry.COLUMN_TOTAL_PASSENGERS,
                        Contract.PnrEntry.COLUMN_SOURCE_STATION,
                        Contract.PnrEntry.COLUMN_DESTINATION_STATION,
                        Contract.PnrEntry.COLUMN_BOARDING_STATION,
                        Contract.PnrEntry.COLUMN_PASSENGERS,
                        Contract.PnrEntry.COLUMN_CHART_PREPARED,
                        Contract.PnrEntry.COLUMN_TRAIN_NUMBER,
                }, null, null, null
        );

        return cursor;
    }

    public boolean isNetworkConnectionAvailable(Activity activity) {

        ConnectivityManager cm = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    public void showNoInternetAlertDialog(final Activity activity, String string, String string1) {

        AlertDialog dialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(string);
        builder.setMessage(string1);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();

                Bundle bundle = new Bundle();
                bundle.putInt(activity.getString(R.string.fragmentId),6);
                Utils.getInstance().startActivity(activity,bundle, HelperActivity.class);
            }
        });
        dialog = builder.create();
        dialog.show();
    }
}
