package com.example.android.bloodbank.view.activity;



import android.support.v7.app.AppCompatActivity;

import com.example.android.bloodbank.view.fragment.BaseFragment;



public class BaseActivity extends AppCompatActivity {
    public BaseFragment baseFragment;

    @Override
    public void onBackPressed() {
        baseFragment.onBack();
    }

    public void superBackPressed() {
        super.onBackPressed();
    }
}
