package com.gosemathraj.railsofindia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.models.Availability;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iamsparsh on 18/2/17.
 */

public class SeatAvailabilityAdapter extends RecyclerView.Adapter<SeatAvailabilityAdapter.AvailabilityViewHolder> {

    private Context context;
    private List<Availability> availabilityList;

    public SeatAvailabilityAdapter(Context context, List<Availability> availabilityList) {
        this.context = context;
        this.availabilityList = availabilityList;
    }

    @Override
    public AvailabilityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_seat_availability_recyclerview,parent,false);
        return new AvailabilityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AvailabilityViewHolder holder, int position) {

        Availability availability = availabilityList.get(position);
        if(availability != null){

            holder.srNo.setText(String.valueOf(position + 1));
            holder.date.setText(availability.getDate());
            holder.status.setText(availability.getStatus());
        }

    }

    @Override
    public int getItemCount() {
        return availabilityList.size();
    }

    class AvailabilityViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.sr_no)
        TextView srNo;

        @BindView(R.id.date)
        TextView date;

        @BindView(R.id.status)
        TextView status;

        public AvailabilityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
