package com.gosemathraj.railsofindia.services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.gosemathraj.railsofindia.utility.Utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by iamsparsh on 17/2/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private String messageList;
    private static final String TAG = "FCM Service";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());


        Calendar calendar = Calendar.getInstance();
        String date = calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR);
        String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

        String newMessage = remoteMessage.getNotification().getBody() + "&&" + date + "," +time;
        String oldMessages = Utils.getInstance().getDataFromPreference(this,"Notifications");

        if(oldMessages != null){
            messageList = oldMessages + "=" + newMessage;
        }else{
            messageList = newMessage;
        }


        Utils.getInstance().saveDataInPreference(this,"Notifications",messageList);
    }
}
