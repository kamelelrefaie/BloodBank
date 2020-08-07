package com.example.android.bloodbank.view.fragment.homeCycle;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.adapter.SpinnerAdapter1;
import com.example.android.bloodbank.data.model.DateTxt;
import com.example.android.bloodbank.data.model.profile.Profile;
import com.example.android.bloodbank.data.model.signUp.SignUp;
import com.example.android.bloodbank.view.fragment.BaseFragment;
import com.example.android.bloodbank.view.fragment.userCycle.CreateAccFragment;
import com.example.android.bloodbank.view.fragment.userCycle.LoginFragment;


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
import static com.example.android.bloodbank.helper.Helper.showCalender;

public class EditProfileFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.fragment_edit_profile_ti_name)
    TextInputLayout fragmentEditProfileTiName;
    @BindView(R.id.fragment_edit_profile_ti_email)
    TextInputLayout fragmentEditProfileTiEmail;
    @BindView(R.id.fragment_edit_profile_tiet_birthday)
    TextInputEditText fragmentEditProfileTietBirthday;
    @BindView(R.id.fragment_edit_profile_ti_birthday)
    TextInputLayout fragmentEditProfileTiBirthday;
    @BindView(R.id.fragment_edit_profile_spinner_bt)
    Spinner fragmentEditProfileSpinnerBt;
    @BindView(R.id.fragment_edit_profile_ll_spinner_bt)
    LinearLayout fragmentEditProfileLlSpinnerBt;
    @BindView(R.id.fragment_edit_profile_tiet_donationd)
    TextInputEditText fragmentEditProfileTietDonationd;
    @BindView(R.id.fragment_edit_profile_ti_donationd)
    TextInputLayout fragmentEditProfileTiDonationd;
    @BindView(R.id.fragment_edit_profile_spinner_gov)
    Spinner fragmentEditProfileSpinnerGov;
    @BindView(R.id.fragment_edit_profile_ll_spinner_gov)
    LinearLayout fragmentEditProfileLlSpinnerGov;
    @BindView(R.id.fragment_edit_profile_spinner_city)
    Spinner fragmentEditProfileSpinnerCity;
    @BindView(R.id.fragment_edit_profile_ll_spinner_city)
    LinearLayout fragmentEditProfileLlSpinnerCity;
    @BindView(R.id.fragment_edit_profile_ti_phone)
    TextInputLayout fragmentEditProfileTiPhone;
    @BindView(R.id.fragment_edit_profile_ti_password)
    TextInputLayout fragmentEditProfileTiPassword;
    @BindView(R.id.fragment_edit_profile_ti_confirma)
    TextInputLayout fragmentEditProfileTiConfirma;
    @BindView(R.id.fragment_edit_profile_but_change)
    Button fragmentEditProfileButChange;

    private View view;
    private SpinnerAdapter1 bloodAdapter;
    private SpinnerAdapter1 govAdapter;
    private SpinnerAdapter1 cityAdapter;
    private AdapterView.OnItemSelectedListener listener;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_edit_profile, container, false);
        unbinder = ButterKnife.bind(this, view);

        bloodAdapter = new SpinnerAdapter1(getActivity());
        getData(getClient().getBloodTypes(), bloodAdapter, "Choose BloodType", fragmentEditProfileSpinnerBt);
        govAdapter = new SpinnerAdapter1(getActivity());
        cityAdapter = new SpinnerAdapter1(getActivity());
        selectCity();
        getData(getClient().getGover(), govAdapter, "Choose Governorate", fragmentEditProfileSpinnerGov, listener);
        return view;
    }

    private AdapterView.OnItemSelectedListener selectCity() {
        listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    getData(getClient().getCity(govAdapter.selectedId), cityAdapter, "Choose City", fragmentEditProfileSpinnerCity);
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

    @OnClick({R.id.fragment_edit_profile_ti_birthday, R.id.fragment_edit_profile_ti_donationd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_edit_profile_ti_birthday:
                DateTxt dateTxt1 = new DateTxt("01", "01", "1950", "01-01-1950");
                showCalender(getActivity(), "birthday", fragmentEditProfileTietBirthday, dateTxt1);
                break;
            case R.id.fragment_register_ti_donationd:
                DateTxt dateTxt2 = new DateTxt("01", "01", "1950", "01-01-1950");
                showCalender(getActivity(), "birthday", fragmentEditProfileTietDonationd, dateTxt2);
                break;
        }
    }

    @OnClick(R.id.fragment_edit_profile_but_change)
    public void onViewClicked() {
        String name = fragmentEditProfileTiName.getEditText().getText().toString();
        String email = fragmentEditProfileTiEmail.getEditText().getText().toString();
        String birthDate = fragmentEditProfileTiBirthday.getEditText().getText().toString();
        int idCity = cityAdapter.selectedId;
        String phone = fragmentEditProfileTiPhone.getEditText().getText().toString();
        String donationDAte = fragmentEditProfileTiDonationd.getEditText().getText().toString();
        String pass = fragmentEditProfileTiPassword.getEditText().getText().toString();
        String confirmPass = fragmentEditProfileTiConfirma.getEditText().getText().toString();
        int bloodId = bloodAdapter.selectedId;
        String apiToken = LoginFragment.getApiToken();
        editProfile(name, email, birthDate, idCity, phone, donationDAte, pass, confirmPass, bloodId, apiToken);
    }

    private void editProfile(String name, String email, String birthDate, int idCity, String phone, String donationDAte, String pass, String confirmPass, int bloodId, String apiToken) {
        getClient().setUpProfile(name, email, birthDate, idCity, phone, donationDAte, pass, confirmPass, bloodId, apiToken).enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {

            }
        });
        }


        }
