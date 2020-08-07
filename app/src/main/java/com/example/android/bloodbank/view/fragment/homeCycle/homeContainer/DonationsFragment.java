package com.example.android.bloodbank.view.fragment.homeCycle.homeContainer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.adapter.HomeDonationAdapter;
import com.example.android.bloodbank.adapter.SpinnerAdapter1;
import com.example.android.bloodbank.data.model.donationRequests.DonationData;
import com.example.android.bloodbank.data.model.donationRequests.DonationRequests;
import com.example.android.bloodbank.helper.OnEndLess;
import com.example.android.bloodbank.view.fragment.BaseFragment;
import com.example.android.bloodbank.view.fragment.homeCycle.AddDonationFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.bloodbank.data.api.AppClient.getClient;
import static com.example.android.bloodbank.helper.GeneralRequest.getData;
import static com.example.android.bloodbank.helper.Helper.replaceFragment;

public class DonationsFragment extends BaseFragment {
    @BindView(R.id.fragment_home_donations_gov_spin)
    Spinner fragmentHomeDonationsGovSpin;
    @BindView(R.id.fragment_home_donations_bloodtypes_spin)
    Spinner fragmentHomeDonationsBloodtypesSpin;
    Unbinder unbinder;
    @BindView(R.id.fragment_home_donations_rv)
    RecyclerView fragmentHomeDonationsRv;
    @BindView(R.id.fragment_home_donations_btn_filter)
    ImageButton fragmentHomeDonationsBtnFilter;
    @BindView(R.id.fragment_home_donations_fbtn_add)
    FloatingActionButton fragmentHomeDonationsFbtnAdd;
    private SpinnerAdapter1 govAdapter;
    private SpinnerAdapter1 bloodAdapter;
    private View view;
    private LinearLayoutManager linearLayoutManager;
    private List<DonationData> donatinList = new ArrayList<>();
    private HomeDonationAdapter homeDonationAdapter;
    private Integer maxPage = 0;
    private OnEndLess onEndLess;
    private boolean filter = false;

    public DonationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setUpActivity();
        view = inflater.inflate(R.layout.fragment_donations, container, false);
        unbinder = ButterKnife.bind(this, view);
        govAdapter = new SpinnerAdapter1(getActivity());
        bloodAdapter = new SpinnerAdapter1(getActivity());
        getData(getClient().getBloodTypes(), bloodAdapter, "Choose BloodType", fragmentHomeDonationsBloodtypesSpin);
        getData(getClient().getGover(), govAdapter, "Choose Governorate", fragmentHomeDonationsGovSpin);


        init();

        return view;

    }

    private void init() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentHomeDonationsRv.setLayoutManager(linearLayoutManager);
        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;

                        if (filter) {

                            onFilter(current_page);
                        } else {
                            getDonations(current_page);
                        }

                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }

                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }

        };
        fragmentHomeDonationsRv.addOnScrollListener(onEndLess);
        homeDonationAdapter = new HomeDonationAdapter(getActivity(), donatinList);
        fragmentHomeDonationsRv.setAdapter(homeDonationAdapter);
        fragmentHomeDonationsRv.setLayoutManager(linearLayoutManager);

        getDonations(1);
    }

    private void getDonations(int page) {
        Call<DonationRequests> call = getClient()
                .getDonationsRequests("W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl", page);

        startCall(call, page);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fragment_home_donations_btn_filter)
    public void onViewClicked() {
        onFilter(1);
    }

    private void onFilter(int page) {
        filter = true;
        onEndLess.previousTotal = 0; // The total number of items in the dataset after the last load
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
        donatinList = new ArrayList<>();
        homeDonationAdapter = new HomeDonationAdapter(getActivity(), donatinList);
        fragmentHomeDonationsRv.setAdapter(homeDonationAdapter);
        Call<DonationRequests> call = getClient()
                .getDonationsRequests("W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl", page
                        , bloodAdapter.selectedId, govAdapter.selectedId);

        startCall(call, page);
    }

    private void startCall(Call<DonationRequests> call, int page) {
        call.enqueue(new Callback<DonationRequests>() {
            @Override
            public void onResponse(Call<DonationRequests> call, Response<DonationRequests> response) {
                if (response.body().getStatus() == 1) {

                    maxPage = response.body().getData().getLastPage();
                    donatinList.addAll(response.body().getData().getData());
                    homeDonationAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<DonationRequests> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.fragment_home_donations_fbtn_add)
    public void onfbtnClicked() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_frame, new AddDonationFragment());
    }
}
