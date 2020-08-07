package com.example.android.bloodbank.view.fragment.homeCycle;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.adapter.NotificationFragmentAdapter;
import com.example.android.bloodbank.data.model.notifications.Notifications;
import com.example.android.bloodbank.data.model.notifications.NotificationsData;
import com.example.android.bloodbank.helper.OnEndLess;
import com.example.android.bloodbank.view.fragment.BaseFragment;
import com.example.android.bloodbank.view.fragment.userCycle.LoginFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.bloodbank.data.api.AppClient.getClient;
import static com.example.android.bloodbank.helper.Helper.replaceFragment;

public class NotificationFragment extends BaseFragment {
    @BindView(R.id.notification_fragment_rv_notification_list)
    RecyclerView notificationFragmentRvNotificationList;
    Unbinder unbinder;
    @BindView(R.id.notification_fragment_visabile_rel)
    RelativeLayout notificationFragmentVisabileRel;
    @BindView(R.id.notification_fragment_donate_btn)
    Button notificationFragmentDonateBtn;

    private LinearLayoutManager linearLayoutManager;
    private View view;
    private NotificationFragmentAdapter notificationFragmentAdapter;
    private List<NotificationsData> notificationsDataList = new ArrayList<>();
    private Integer maxPage = 0;
    private OnEndLess onEndLess;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setUpActivity();
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        unbinder = ButterKnife.bind(this, view);

        init();

        return view;
    }

    private void init() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        notificationFragmentRvNotificationList.setLayoutManager(linearLayoutManager);
        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        getNotifications(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;

                }
            }
        };
        notificationFragmentRvNotificationList.addOnScrollListener(onEndLess);
        notificationFragmentAdapter = new NotificationFragmentAdapter(getActivity(), notificationsDataList);
        notificationFragmentRvNotificationList.setAdapter(notificationFragmentAdapter);
        getNotifications(1);
    }

    private void getNotifications(int page) {
        getClient().getNotifications(LoginFragment.getApiToken(), page).enqueue(new Callback<Notifications>() {
            @Override
            public void onResponse(Call<Notifications> call, Response<Notifications> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        if (response.body().getData().getTotal() != 0) {
                            maxPage = response.body().getData().getLastPage();
                            notificationsDataList.addAll(response.body().getData().getData());
                            notificationFragmentAdapter.notifyDataSetChanged();

                        } else {
                            notificationFragmentVisabileRel.setVisibility(View.VISIBLE);
                        }
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<Notifications> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.notification_fragment_donate_btn)
    public void onViewClicked() {
        replaceFragment(getActivity().getSupportFragmentManager(),R.id.home_frame,new AddDonationFragment());
    }
}
