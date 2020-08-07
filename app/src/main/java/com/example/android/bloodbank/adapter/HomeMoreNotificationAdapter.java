package com.example.android.bloodbank.adapter;

import android.app.Activity;
import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.data.model.generalResponse.GeneralResponseData;
import com.example.android.bloodbank.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeMoreNotificationAdapter extends RecyclerView.Adapter<HomeMoreNotificationAdapter.HomeMoreNotificationViewHolder> {
    private Context context;
    private BaseActivity activity;
    private List<GeneralResponseData> generalResponseData = new ArrayList<>();
    private List<String> oldIds = new ArrayList<>();
   public List<Integer> newIds = new ArrayList<>();

    public HomeMoreNotificationAdapter(Context context, Activity activity, List<GeneralResponseData> generalResponseData, List<String> oldIds) {
        this.context = context;
        this.activity = (BaseActivity) activity;
        this.generalResponseData = generalResponseData;
        this.oldIds = oldIds;
    }

    @Override
    public HomeMoreNotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_check_box,
                parent, false);

        return new HomeMoreNotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeMoreNotificationViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(HomeMoreNotificationViewHolder holder, int position) {
        holder.itemCheckboxNotification.setText(generalResponseData.get(position).getName());
        if (oldIds.contains(String.valueOf(generalResponseData.get(position).getId()))) {
            holder.itemCheckboxNotification.setChecked(true);
            newIds.add(generalResponseData.get(position).getId());
        } else {
            holder.itemCheckboxNotification.setChecked(false);
        }
    }

    private void setAction(HomeMoreNotificationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return generalResponseData.size();
    }

    @OnClick(R.id.item_checkbox_notification)
    public void onViewClicked() {
    }

    public class HomeMoreNotificationViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.item_checkbox_notification)
        CheckBox itemCheckboxNotification;

        public HomeMoreNotificationViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.item_checkbox_notification)
        public void onViewClicked() {

            if (!itemCheckboxNotification.isChecked()) {
                for (int i = 0; i < newIds.size(); i++) {
                    if (newIds.get(i).equals(generalResponseData.get(getAdapterPosition()).getId())) {
                        newIds.remove(i);
                        break;
                    }

                }

            } else {
                newIds.add(generalResponseData.get(getAdapterPosition()).getId());


            }
        }
    }
}
