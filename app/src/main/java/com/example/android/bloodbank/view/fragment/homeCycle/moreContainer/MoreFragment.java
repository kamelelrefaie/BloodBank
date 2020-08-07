package com.example.android.bloodbank.view.fragment.homeCycle.moreContainer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.view.activity.userCycle.UserCycleActivity;
import com.example.android.bloodbank.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.android.bloodbank.helper.Helper.replaceFragment;

public class MoreFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.fragment_home_more_log_out_rl)
    RelativeLayout fragmentHomeMoreLogOutRl;
    @BindView(R.id.fragment_home_more_about_rl)
    RelativeLayout fragmentHomeMoreAboutRl;
    private View view;

    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setUpActivity();
        view = inflater.inflate(R.layout.fragment_home_more, container, false);
        unbinder = ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.fragment_home_more_rel_favorite, R.id.fragment_home_more_about_rl
            , R.id.fragment_home_more_log_out_rl, R.id.fragment_home_more_contact_rl
            , R.id.fragment_home_more_rate_rl, R.id.fragment_home_more_notification_settings_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_home_more_rel_favorite:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_frame, new FavPostFragment());
                break;
            case R.id.fragment_home_more_contact_rl:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_frame, new ContactUsFragment());
                break;
            case R.id.fragment_home_more_about_rl:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_frame, new AboutAppFragment());
                break;
            case R.id.fragment_home_more_rate_rl:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(
                        "https://play.google.com/store/apps/details?id=com.example.android"));
                intent.setPackage("com.android.vending");
                startActivity(intent);
                break;
            case R.id.fragment_home_more_notification_settings_rl:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_frame, new NotificationSettingsFragment());
                break;
            case R.id.fragment_home_more_log_out_rl:
                getActivity().finish();
                getActivity().startActivity(new Intent(getActivity(), UserCycleActivity.class));
                break;

        }
    }
}
