package com.gosemathraj.railsofindia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.models.Days;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iamsparsh on 19/2/17.
 */

public class TrainsDaysAdapter extends RecyclerView.Adapter<TrainsDaysAdapter.DaysViewHolder> {

    private Context context;
    private List<Days> daysList;

    public TrainsDaysAdapter(Context context, List<Days> daysList) {
        this.context = context;
        this.daysList = daysList;
    }

    @Override
    public DaysViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_trains_days_recyclerview,parent,false);
        return new DaysViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DaysViewHolder holder, int position) {

        Days d = daysList.get(position);
        if(d != null){
            holder.day.setText(d.getDayCode());
            holder.dayvalue.setText(d.getRuns());
        }
    }

    @Override
    public int getItemCount() {
        return daysList.size();
    }

    class DaysViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.day)
        TextView day;

        @BindView(R.id.day_value)
        TextView dayvalue;

        public DaysViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
