package com.example.android.bloodbank.view.fragment.homeCycle;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.adapter.SpinnerAdapter1;
import com.example.android.bloodbank.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.android.bloodbank.data.api.AppClient.getClient;
import static com.example.android.bloodbank.helper.GeneralRequest.getData;

public class AddDonationFragment extends BaseFragment {
    @BindView(R.id.add_donation_blood_types_spin)
    Spinner addDonationBloodTypesSpin;
    @BindView(R.id.add_donation_blood_gov_spin)
    Spinner addDonationBloodGovSpin;
    Unbinder unbinder;
    @BindView(R.id.add_donation_city_spin)
    Spinner addDonationCitySpin;
    @BindView(R.id.add_donation_notes_send_btn)
    Button addDonationNotesSendBtn;
    @BindView(R.id.add_donation_name_txti)
    TextInputLayout addDonationNameTxti;
    @BindView(R.id.add_donation_age_txtil)
    TextInputLayout addDonationAgeTxtil;
    @BindView(R.id.add_donation_blood_types_lin)
    LinearLayout addDonationBloodTypesLin;
    @BindView(R.id.add_donation_blood_num_txtil)
    TextInputLayout addDonationBloodNumTxtil;
    @BindView(R.id.add_donation_gov_txtil)
    LinearLayout addDonationGovTxtil;
    @BindView(R.id.add_donation_city_txtil)
    LinearLayout addDonationCityTxtil;
    @BindView(R.id.add_donation_phone_txtl)
    TextInputLayout addDonationPhoneTxtl;
    @BindView(R.id.add_donation_notes_txti)
    TextInputLayout addDonationNotesTxti;
    private View view;
    private SpinnerAdapter1 govAdapter;
    private SpinnerAdapter1 cityAdapter;
    private AdapterView.OnItemSelectedListener listener;
    private SpinnerAdapter1 bloodAdapter;


    public AddDonationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setUpActivity();
        view = inflater.inflate(R.layout.fragment_add_donation, container, false);
        unbinder = ButterKnife.bind(this, view);
        bloodAdapter = new SpinnerAdapter1(getActivity());
        getData(getClient().getBloodTypes(), bloodAdapter, "choose blood", addDonationBloodTypesSpin);
        govAdapter = new SpinnerAdapter1(getActivity());
        cityAdapter = new SpinnerAdapter1(getActivity());
        selectCity();
        getData(getClient().getGover(), govAdapter, "choose gov", addDonationBloodGovSpin, listener);

        return view;
    }

    private AdapterView.OnItemSelectedListener selectCity() {
        listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    getData(getClient().getCity(govAdapter.selectedId), cityAdapter, "Choose city", addDonationCitySpin);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        return listener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.add_donation_notes_send_btn)
    public void onSendBtnClicked() {
        String name = addDonationNameTxti.getEditText().getText().toString();
        String age = addDonationAgeTxtil.getEditText().getText().toString();
        int bloodTypesa = bloodAdapter.selectedId;
        String num = addDonationBloodNumTxtil.getEditText().getText().toString();
        int gov=govAdapter.selectedId;
        int city=cityAdapter.selectedId;
        String mob=addDonationPhoneTxtl.getEditText().getText().toString();
        String notes=addDonationNotesTxti.getEditText().getText().toString();
    }
}
