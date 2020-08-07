package com.example.android.bloodbank.view.activity.splashCycle;

import android.os.Bundle;


import com.example.android.bloodbank.R;
import com.example.android.bloodbank.view.activity.BaseActivity;
import com.example.android.bloodbank.view.fragment.splashCycle.SplashFragment;

import static com.example.android.bloodbank.helper.Helper.replaceFragment;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        replaceFragment(getSupportFragmentManager(), R.id.splash_frame,new SplashFragment());
    }
}
