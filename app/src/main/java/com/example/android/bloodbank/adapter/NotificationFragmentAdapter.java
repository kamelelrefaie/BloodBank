package com.example.android.bloodbank.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.data.model.notifications.NotificationsData;
import com.example.android.bloodbank.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationFragmentAdapter extends RecyclerView.Adapter<NotificationFragmentAdapter.NotificationFragmentHolder> {

    private Context context;
    private BaseActivity activity;
    private List<NotificationsData> notificationsDataList = new ArrayList<>();


    public NotificationFragmentAdapter(Activity activity, List<NotificationsData> notificationsDataList) {
        this.context = activity;
        this.activity = (BaseActivity) activity;
        this.notificationsDataList = notificationsDataList;
    }


    @Override
    public NotificationFragmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification_fragment,
                parent, false);

        return new NotificationFragmentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationFragmentHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(NotificationFragmentHolder holder, int position) {
        if (notificationsDataList.get(position).getPivot().getIsRead().equals("0")) {
            holder.itemNotificationImg.setImageResource(R.drawable.ic_notifications_red);
        } else {
            holder.itemNotificationImg.setImageResource(R.drawable.ic_notification_white);
        }
        holder.itemNotificationTvTitlr.setText(notificationsDataList.get(position).getTitle());
    }


    private void setAction(NotificationFragmentHolder holder, int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationsDataList.size();
    }

    public class NotificationFragmentHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.item_notification_img)
        ImageView itemNotificationImg;
        @BindView(R.id.item_notification_tv_titlr)
        TextView itemNotificationTvTitlr;
        @BindView(R.id.item_notification_tv_time)
        TextView itemNotificationTvTime;

        public NotificationFragmentHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }
    }
}

