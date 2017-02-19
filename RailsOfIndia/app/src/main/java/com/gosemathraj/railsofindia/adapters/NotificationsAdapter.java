package com.gosemathraj.railsofindia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.utility.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iamsparsh on 17/2/17.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsViewholder> {

    private Context context;
    private List<String> notificationsList;

    public NotificationsAdapter(Context context, List<String> notificationsList) {
        this.context = context;
        this.notificationsList = notificationsList;
    }

    @Override
    public NotificationsViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_notifications_recyclerview,parent,false);
        return new NotificationsViewholder(view);
    }

    @Override
    public void onBindViewHolder(NotificationsViewholder holder, int position) {

        String x = notificationsList.get(position);
        if(Utils.getInstance().checkForNull(x) && x != null){
            holder.notification.setText(x.split("&&")[0]);
            holder.date.setText("Date : " + (x.split("&&")[1]).split(",")[0]);
            holder.time.setText("Time : " + (x.split("&&")[1]).split(",")[1]);
        }


    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    class NotificationsViewholder extends RecyclerView.ViewHolder{

        @BindView(R.id.notification)
        TextView notification;

        @BindView(R.id.date)
        TextView date;

        @BindView(R.id.time)
        TextView time;

        public NotificationsViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
