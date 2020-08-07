package com.example.android.bloodbank.view.activity.userCycle;


import android.os.Bundle;
import android.support.annotation.Nullable;


import com.example.android.bloodbank.R;
import com.example.android.bloodbank.view.activity.BaseActivity;
import com.example.android.bloodbank.view.fragment.userCycle.LoginFragment;



import static com.example.android.bloodbank.helper.Helper.replaceFragment;

public class UserCycleActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cycle);
        replaceFragment(getSupportFragmentManager(), R.id.user_cycle_frame,new LoginFragment());
    }
}
