package com.example.android.bloodbank.view.fragment.splashCycle;

import android.os.Bundle;
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.view.fragment.BaseFragment;

import static com.example.android.bloodbank.helper.Helper.replaceFragment;

public class SplashFragment extends BaseFragment {

    public SplashFragment() {
        // Required empty public constructor
    }

    private View view;
    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.splash_frame, new SliderFragment());
            }
        },2000);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_splash, container, false);
        return view;
    }

}
