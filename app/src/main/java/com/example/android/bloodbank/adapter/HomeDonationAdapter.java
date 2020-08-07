package com.example.android.bloodbank.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.data.model.donationRequests.DonationData;
import com.example.android.bloodbank.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeDonationAdapter extends RecyclerView.Adapter<HomeDonationAdapter.HomeDonationViewHolder> {

    private Context context;
    private BaseActivity activity;
    private List<DonationData> donationData = new ArrayList<>();


    public HomeDonationAdapter(Activity activity, List<DonationData> donationData) {
        this.context = activity;
        this.activity = (BaseActivity) activity;
        this.donationData = donationData;
    }


    @Override
    public HomeDonationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_donation,
                parent, false);

        return new HomeDonationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeDonationViewHolder holder, int position) {
        DonationData data = donationData.get(position);
        holder.itemDonationTvBloodType.setText(data.getBloodType().getName());
        holder.itemDonationTvAdress.setText(data.getHospitalAddress());
        holder.itemDonationTvName.setText(data.getPatientName());
        holder.itemDonationTvCity.setText(data.getCity().getName());

        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(HomeDonationViewHolder holder, int position) {

    }

    private void setAction(HomeDonationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return donationData.size();
    }

    public class HomeDonationViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.item_donation_tv_name)
        TextView itemDonationTvName;
        @BindView(R.id.item_donation_tv_adress)
        TextView itemDonationTvAdress;
        @BindView(R.id.item_donation_tv_city)
        TextView itemDonationTvCity;
        @BindView(R.id.item_donation_tv_blood_type)
        TextView itemDonationTvBloodType;
        public HomeDonationViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}


