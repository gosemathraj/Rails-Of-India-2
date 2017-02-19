package com.gosemathraj.railsofindia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.models.Passengers;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iamsparsh on 18/2/17.
 */

public class PnrPassengersAdapter extends RecyclerView.Adapter<PnrPassengersAdapter.PassengersViewHolder>{

    private Context context;
    private List<Passengers> passengersList;

    public PnrPassengersAdapter(Context context, List<Passengers> passengersList) {
        this.context = context;
        this.passengersList = passengersList;
    }

    @Override
    public PassengersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view  = LayoutInflater.from(context).inflate(R.layout.layout_pnr_details_recyclerview,parent,false);
        return new PassengersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PassengersViewHolder holder, int position) {

        Passengers p = passengersList.get(position);
        holder.passengerNumber.setText("Passenger Number" + position + 1);
        holder.coachPosition.setText(String.valueOf(p.getCoachPOsition()));
        holder.currentStatus.setText(p.getCurrentStatus());
        holder.bookingStatus.setText(p.getBookingStatus());
    }

    @Override
    public int getItemCount() {
        return passengersList.size();
    }

    class PassengersViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.coach_position)
        TextView coachPosition;

        @BindView(R.id.current_status)
        TextView currentStatus;

        @BindView(R.id.booking_status)
        TextView bookingStatus;

        @BindView(R.id.passenger_number)
        TextView passengerNumber;

        public PassengersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
