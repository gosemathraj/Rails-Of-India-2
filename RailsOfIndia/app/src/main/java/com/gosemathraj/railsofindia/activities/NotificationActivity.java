package com.gosemathraj.railsofindia.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.adapters.NotificationsAdapter;
import com.gosemathraj.railsofindia.utility.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iamsparsh on 17/2/17.
 */

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.notifications_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.no_datd_found)
    TextView noDataFound;

    private String notifications;
    private List<String> notificationsList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifcation);
        ButterKnife.bind(this);

        try{
            init();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void init() {
        setToolbar();
        getNotificationsFromSharedPreferences();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.notifications));
    }

    private void getNotificationsFromSharedPreferences() {

        notifications = Utils.getInstance().getDataFromPreference(this,getString(R.string.Notifications));
        if(Utils.getInstance().checkForNull(notifications) && notifications != null){
            getNotificationsList();
        }else{
            noDataFound.setVisibility(View.VISIBLE);
            Utils.getInstance().showToast(this,getString(R.string.no_notifications_found));
        }
    }

    private void getNotificationsList() {

        String temp[] = notifications.split("=");
        for(int i = 0;i < temp.length;i++){
            notificationsList.add(temp[i]);
        }

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new NotificationsAdapter(this,notificationsList));
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
