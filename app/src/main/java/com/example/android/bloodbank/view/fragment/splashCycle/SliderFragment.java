package com.example.android.bloodbank.view.fragment.splashCycle;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.adapter.SlideAdapter;
import com.example.android.bloodbank.view.activity.userCycle.UserCycleActivity;
import com.example.android.bloodbank.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SliderFragment extends BaseFragment {
//intialize value
    private ImageView[] dots;
    private SlideAdapter slideAdapter;
    private int[] layouts;
    private View view;
    @BindView(R.id.fragment_slider_vp)
    ViewPager fragmentSliderVp;
    @BindView(R.id.fragment_slider_lineardots)
    LinearLayout fragmentSliderLineardots;
    Unbinder unbinder;
    @BindView(R.id.fragment_slider_img_next)
    ImageView fragmentSliderImgNext;

    public SliderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // to make status bar transpatent
        setStatusBar();
        setUpActivity();
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_slider, container, false);
        unbinder = ButterKnife.bind(this, view);
        // set adapter
        layouts = new int[]{R.layout.fragment_slide1, R.layout.fragment_slide1, R.layout.fragment_slide1};
        slideAdapter = new SlideAdapter(getActivity(), layouts);
        fragmentSliderVp.setAdapter(slideAdapter);
        createDots(0);
        fragmentSliderVp.addOnPageChangeListener(changeListener);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /*
    to set status bar
     */
    public void setStatusBar() {
        if (Build.VERSION.SDK_INT > 19) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
/*
   to create dots
 */
    public void createDots(int currentPosition) {
        if (fragmentSliderLineardots != null) {
            fragmentSliderLineardots.removeAllViews();
        }
        dots = new ImageView[layouts.length];
        for (int i = 0; i < layouts.length; i++) {
            dots[i] = new ImageView(getContext());
            if (i == currentPosition) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dots));
            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.inactive_dots));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);
            fragmentSliderLineardots.addView(dots[i], params);
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0 || position == 1) {

                fragmentSliderImgNext.setEnabled(true);
                fragmentSliderImgNext.setImageResource(R.drawable.arrow);

            } else {

                fragmentSliderImgNext.setEnabled(true);
                fragmentSliderImgNext.setImageResource(R.drawable.check);
            }
            createDots(position);

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    @OnClick(R.id.fragment_slider_img_next)
    public void onViewClicked() {
        if (fragmentSliderVp.getCurrentItem() == 0) {
            fragmentSliderVp.setCurrentItem(1);

        } else if (fragmentSliderVp.getCurrentItem() == 1) {
            fragmentSliderVp.setCurrentItem(2);

        } else {
            startActivity(new Intent(getActivity(), UserCycleActivity.class));
            getActivity().finish();

        }

    }

    @Override
    public void onBack() {
        getActivity().finish();
    }
}
