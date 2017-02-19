package com.gosemathraj.railsofindia.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.fragments.FragmentPnrStatusDetails;
import com.gosemathraj.railsofindia.models.PnrStatus;
import com.gosemathraj.railsofindia.utility.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iamsparsh on 18/2/17.
 */

public class PnrListAdapter extends RecyclerView.Adapter<PnrListAdapter.PnrListViewHolder>{

    private Activity context;
    private List<PnrStatus> pnrStatusList;

    public PnrListAdapter(Activity context, List<PnrStatus> pnrStatusList) {
        this.context = context;
        this.pnrStatusList = pnrStatusList;
    }

    @Override
    public PnrListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_pnr_list,parent,false);
        return new PnrListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PnrListViewHolder holder, int position) {
        final PnrStatus pnrStatus = pnrStatusList.get(position);
        if(pnrStatus != null){

            holder.trainNumberName.setText(pnrStatus.getTrainName() + " - " +pnrStatus.getTrainNumber());
            holder.pnrNumber.setText("Pnr Number : " + pnrStatus.getPnrNumber());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(context.getString(R.string.pnrStatus),pnrStatus);

                    FragmentPnrStatusDetails fragmentPnrStatusDetails = new FragmentPnrStatusDetails();
                    fragmentPnrStatusDetails.setArguments(bundle);

                    Utils.getInstance().replaceFragmentfromActivity((FragmentActivity) context,fragmentPnrStatusDetails,R.id.helper_frame_container);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return pnrStatusList.size();
    }

    class PnrListViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.train_name_no)
        TextView trainNumberName;

        @BindView(R.id.pnr_number)
        TextView pnrNumber;

        public PnrListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
