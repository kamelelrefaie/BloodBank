package com.example.android.bloodbank.view.fragment.homeCycle.homeContainer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.adapter.ViewPagerWithFragmentAdapter;
import com.example.android.bloodbank.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeNavigationFragment extends BaseFragment {
    @BindView(R.id.fragment_home_tap)
    TabLayout fragmentHomeTap;
    @BindView(R.id.fragment_home_vp)
    ViewPager fragmentHomeVp;
    Unbinder unbinder;

    private View view;
    private ViewPagerWithFragmentAdapter viewPagerWithFragmentAdapter;

    public HomeNavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setUpActivity();
        view = inflater.inflate(R.layout.fragment_home_navigation, container, false);
        unbinder = ButterKnife.bind(this, view);

        viewPagerWithFragmentAdapter = new ViewPagerWithFragmentAdapter(getChildFragmentManager());

        viewPagerWithFragmentAdapter.addPager(new DonationsFragment(), "Donations");
        viewPagerWithFragmentAdapter.addPager(new PostsFragment(), "Articles");

        fragmentHomeVp.setAdapter(viewPagerWithFragmentAdapter);
        fragmentHomeTap.setupWithViewPager(fragmentHomeVp);



        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
