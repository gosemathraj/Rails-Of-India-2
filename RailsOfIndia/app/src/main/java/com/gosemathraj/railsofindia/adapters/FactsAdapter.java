package com.gosemathraj.railsofindia.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
 * Created by iamsparsh on 19/2/17.
 */

public class FactsAdapter extends RecyclerView.Adapter<FactsAdapter.FactsViewHolder>{

    private Activity context;
    private List<String> factsList;

    public FactsAdapter(Activity context, List<String> factsList) {
        this.context = context;
        this.factsList = factsList;
    }

    @Override
    public FactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_facts_recyclerview,parent,false);
        return new FactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FactsViewHolder holder, final int position) {

        holder.facts.setText(factsList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.getInstance().openShareIntent(context,context.getString(R.string.indian_rail_facts),factsList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return factsList.size();
    }

    class FactsViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.facts)
        TextView facts;

        public FactsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
