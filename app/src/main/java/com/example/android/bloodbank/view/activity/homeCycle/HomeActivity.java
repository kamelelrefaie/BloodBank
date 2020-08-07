package com.example.android.bloodbank.view.activity.homeCycle;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.view.activity.BaseActivity;
import com.example.android.bloodbank.view.fragment.homeCycle.EditProfileFragment;
import com.example.android.bloodbank.view.fragment.homeCycle.homeContainer.HomeNavigationFragment;
import com.example.android.bloodbank.view.fragment.homeCycle.NotificationFragment;
import com.example.android.bloodbank.view.fragment.homeCycle.moreContainer.MoreFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.bloodbank.helper.Helper.replaceFragment;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.fragment_home_bottomNav)
    BottomNavigationView fragmentHomeBottomNav;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        fragmentHomeBottomNav.setOnNavigationItemSelectedListener(navListener);
        replaceFragment(getSupportFragmentManager(), R.id.home_frame, new HomeNavigationFragment());

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomeNavigationFragment();
                    break;
                case R.id.nav_person:
                    selectedFragment = new EditProfileFragment();
                    break;
                case R.id.nav_bell:
                    selectedFragment = new NotificationFragment();
                    break;
                case R.id.nav_more:
                    selectedFragment = new MoreFragment();
                    break;
            }
            replaceFragment(getSupportFragmentManager(), R.id.home_frame, selectedFragment);
            return true;
        }
    };
}
