package com.example.android.bloodbank.view.fragment.homeCycle.moreContainer;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.adapter.HomeMoreNotificationAdapter;
import com.example.android.bloodbank.data.model.generalResponse.GeneralResponse;
import com.example.android.bloodbank.data.model.notification.NotificationSetting;
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

public class NotificationSettingsFragment extends BaseFragment {
    @BindView(R.id.fragment_home_more_notification_settings_blood_rv)
    RecyclerView fragmentHomeMoreNotificationSettingsBloodRv;
    Unbinder unbinder;
    @BindView(R.id.fragment_home_more_notification_settings_gov_rv)
    RecyclerView fragmentHomeMoreNotificationSettingsGovRv;
    @BindView(R.id.fragment_home_more_notification_blood_gone_rl)
    RelativeLayout fragmentHomeMoreNotificationBloodGoneRl;
    @BindView(R.id.fragment_home_more_notification_settings_blood_bar)
    RelativeLayout fragmentHomeMoreNotificationSettingsBloodBar;
    @BindView(R.id.fragment_home_more_notification_gov_gone_rl)
    RelativeLayout fragmentHomeMoreNotificationGovGoneRl;
    @BindView(R.id.fragment_home_more_notification_settings_gov_bar)
    RelativeLayout fragmentHomeMoreNotificationSettingsGovBar;
    @BindView(R.id.fragment_home_more_notification_settings_blood_img)
    ImageView fragmentHomeMoreNotificationSettingsBloodImg;
    @BindView(R.id.fragment_home_more_notification_settings_gov_img)
    ImageView fragmentHomeMoreNotificationSettingsGovImg;
    @BindView(R.id.fragment_home_more_notification_settings_save_but)
    Button fragmentHomeMoreNotificationSettingsSaveBut;
    private View view;
    private List<String> bloodTypes = new ArrayList<>();
    private List<String> governorates = new ArrayList<>();
    private HomeMoreNotificationAdapter homeMoreNotificationGovAdapter, homeMoreNotificationBloodAdapter;


    public NotificationSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setUpActivity();
        view = inflater.inflate(R.layout.fragment_home_more_notification_settings, container, false);
        unbinder = ButterKnife.bind(this, view);


        init();
        getNotificationSettings();
        return view;
    }


    private void getNotificationSettings() {
        getClient().getNotificationSettings(LoginFragment.getApiToken()).enqueue(new Callback<NotificationSetting>() {
            @Override
            public void onResponse(Call<NotificationSetting> call, Response<NotificationSetting> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        bloodTypes = response.body().getData().getBloodTypes();
                        governorates = response.body().getData().getGovernorates();
                        getBloodTypes();
                        getGovernorates();
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<NotificationSetting> call, Throwable t) {

            }
        });
    }

    private void getGovernorates() {
        getClient().getGover().enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        homeMoreNotificationBloodAdapter = new HomeMoreNotificationAdapter(getActivity(), getActivity(),
                                response.body().getData(), bloodTypes);
                        fragmentHomeMoreNotificationSettingsBloodRv.setAdapter(homeMoreNotificationBloodAdapter);
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {

            }
        });
    }

    private void getBloodTypes() {
        getClient().getBloodTypes().enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        homeMoreNotificationGovAdapter = new HomeMoreNotificationAdapter(getActivity(), getActivity(),
                                response.body().getData(), governorates);
                        fragmentHomeMoreNotificationSettingsGovRv.setAdapter(homeMoreNotificationGovAdapter);
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {

            }
        });
    }

    private void init() {
        fragmentHomeMoreNotificationSettingsBloodRv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        fragmentHomeMoreNotificationSettingsGovRv.setLayoutManager(new GridLayoutManager(getActivity(), 3));


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fragment_home_more_notification_settings_blood_bar, R.id.fragment_home_more_notification_settings_gov_bar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_home_more_notification_settings_blood_bar:


                if (fragmentHomeMoreNotificationBloodGoneRl.getVisibility() == View.GONE) {
                    fragmentHomeMoreNotificationBloodGoneRl.setVisibility(View.VISIBLE);
                    fragmentHomeMoreNotificationSettingsBloodImg.setImageResource(R.drawable.ic_indeterminate);
                    break;
                } else if (fragmentHomeMoreNotificationBloodGoneRl.getVisibility() == View.VISIBLE) {
                    fragmentHomeMoreNotificationBloodGoneRl.setVisibility(View.GONE);
                    fragmentHomeMoreNotificationSettingsBloodImg.setImageResource(R.drawable.ic_add);
                    break;
                }
                break;
            case R.id.fragment_home_more_notification_settings_gov_bar:
                if (fragmentHomeMoreNotificationGovGoneRl.getVisibility() == View.GONE) {
                    fragmentHomeMoreNotificationGovGoneRl.setVisibility(View.VISIBLE);
                    fragmentHomeMoreNotificationSettingsGovImg.setImageResource(R.drawable.ic_indeterminate);
                    break;
                } else if (fragmentHomeMoreNotificationGovGoneRl.getVisibility() == View.VISIBLE) {
                    fragmentHomeMoreNotificationGovGoneRl.setVisibility(View.GONE);
                    fragmentHomeMoreNotificationSettingsGovImg.setImageResource(R.drawable.ic_add);
                    break;
                }
                break;
        }
    }

    @OnClick(R.id.fragment_home_more_notification_settings_save_but)
    public void onViewClicked() {
        setSave(LoginFragment.getApiToken(), homeMoreNotificationBloodAdapter.newIds, homeMoreNotificationGovAdapter.newIds);
    }

    private void setSave(String apiToken, List<Integer> newIds, List<Integer> newIds1) {
        getClient().setNotificationSettings(apiToken, newIds, newIds1).enqueue(new Callback<NotificationSetting>() {
            @Override
            public void onResponse(Call<NotificationSetting> call, Response<NotificationSetting> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<NotificationSetting> call, Throwable t) {

            }
        });
    }
}
