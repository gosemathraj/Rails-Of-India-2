package com.gosemathraj.railsofindia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.models.Route;
import com.gosemathraj.railsofindia.models.TrainRoute;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iamsparsh on 17/2/17.
 */

public class TrainRouteAdapter extends RecyclerView.Adapter<TrainRouteAdapter.TrainRouteViewHolder> {

    private Context context;
    private List<Route> routeList;

    public TrainRouteAdapter(Context context, List<Route> routeList) {
        this.context = context;
        this.routeList = routeList;
    }

    @Override
    public TrainRouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_train_route_recyclerview,parent,false);
        return new TrainRouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrainRouteViewHolder holder, int position) {

        Route route = routeList.get(position);
        if(route != null){

            holder.name.setText("Station : " + route.getFullname());
            holder.code.setText("Code : " +route.getCode());
            holder.day.setText("Day : " + String.valueOf(route.getDay()));
            holder.arrival.setText("Arrival : " + route.getScharr());
            holder.departure.setText("Departure : " + route.getSchdep());
        }

    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }

    class TrainRouteViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.station_name)
        TextView name;

        @BindView(R.id.code)
        TextView code;

        @BindView(R.id.day)
        TextView day;

        @BindView(R.id.arrival)
        TextView arrival;

        @BindView(R.id.departure)
        TextView departure;

        public TrainRouteViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }
}
