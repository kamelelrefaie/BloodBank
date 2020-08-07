package com.example.android.bloodbank.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bloodbank.view.activity.BaseActivity;



public class BaseFragment extends Fragment {
    public BaseActivity baseActivity;

    public void setUpActivity() {
        baseActivity = (BaseActivity) getActivity();
        baseActivity.baseFragment = this;

    }

    public void onBack() {
        baseActivity.superBackPressed();
    }

    @Override
    public void onStart() {
        super.onStart();
        setUpActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setUpActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
