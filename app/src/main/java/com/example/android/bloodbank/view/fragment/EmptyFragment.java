package com.example.android.bloodbank.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bloodbank.R;

public class EmptyFragment extends BaseFragment {
private View view;
    public EmptyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setUpActivity();
        view= inflater.inflate(R.layout.empty_fragment, container, false);

    return view;
    }

}
