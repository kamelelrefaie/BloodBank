package com.example.android.bloodbank.view.fragment.homeCycle.moreContainer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.view.fragment.BaseFragment;

public class AboutAppFragment extends BaseFragment {

    public AboutAppFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setUpActivity();
        return inflater.inflate(R.layout.fragment_home_more_about_app, container, false);
    }
}
