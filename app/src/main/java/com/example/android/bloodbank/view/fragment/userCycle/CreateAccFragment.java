package com.example.android.bloodbank.view.fragment.userCycle;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.adapter.SpinnerAdapter1;
import com.example.android.bloodbank.data.model.DateTxt;
import com.example.android.bloodbank.data.model.generalResponse.GeneralResponse;
import com.example.android.bloodbank.data.model.signUp.SignUp;
import com.example.android.bloodbank.view.fragment.BaseFragment;


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

public class CreateAccFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.fragment_register_spinner_bt)
    Spinner fragmentRegisterSpinnerBt;
    @BindView(R.id.fragment_register_spinner_gov)
    Spinner fragmentRegisterSpinnerGov;
    @BindView(R.id.fragment_register_spinner_city)
    Spinner fragmentRegisterSpinnerCity;
    @BindView(R.id.fragment_register_ti_birthday)
    TextInputLayout fragmentRegisterTiBirthday;
    @BindView(R.id.fragment_register_ti_donationd)
    TextInputLayout fragmentRegisterTiDonationd;
    @BindView(R.id.fragment_register_tiet_birthday)
    TextInputEditText fragmentRegisterTietBirthday;
    @BindView(R.id.fragment_register_tiet_donationd)
    TextInputEditText fragmentRegisterTietDonationd;
    @BindView(R.id.fragment_register_but_create)
    Button fragmentRegisterButCreate;
    @BindView(R.id.fragment_register_tv_create)
    TextView fragmentRegisterTvCreate;
    @BindView(R.id.fragment_register_ti_name)
    TextInputLayout fragmentRegisterTiName;
    @BindView(R.id.fragment_register_ti_email)
    TextInputLayout fragmentRegisterTiEmail;


    @BindView(R.id.fragment_register_ll_spinner_bt)
    LinearLayout fragmentRegisterLlSpinnerBt;


    @BindView(R.id.fragment_register_ll_spinner_gov)
    LinearLayout fragmentRegisterLlSpinnerGov;

    @BindView(R.id.fragment_register_ll_spinner_city)
    LinearLayout fragmentRegisterLlSpinnerCity;
    @BindView(R.id.fragment_register_ti_phone)
    TextInputLayout fragmentRegisterTiPhone;
    @BindView(R.id.fragment_register_ti_password)
    TextInputLayout fragmentRegisterTiPassword;
    @BindView(R.id.fragment_register_ti_confirma)
    TextInputLayout fragmentRegisterTiConfirma;

    private View view;
    SpinnerAdapter1 govAdapter;
    SpinnerAdapter1 bloodAdapter;
    SpinnerAdapter1 cityAdapter;
    AdapterView.OnItemSelectedListener listener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, view);
        bloodAdapter = new SpinnerAdapter1(getActivity());
        getData(getClient().getBloodTypes(), bloodAdapter, "Choose Blood Type", fragmentRegisterSpinnerBt);
        govAdapter = new SpinnerAdapter1(getActivity());
        cityAdapter = new SpinnerAdapter1(getActivity());
        selectCity();
        getData(getClient().getGover(), govAdapter, "Choose Governorate", fragmentRegisterSpinnerGov, listener);


        return view;
    }

    private AdapterView.OnItemSelectedListener selectCity() {
        listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    getData(getClient().getCity(govAdapter.selectedId), cityAdapter, "Choose City", fragmentRegisterSpinnerCity);
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

    @OnClick({R.id.fragment_register_ti_birthday, R.id.fragment_register_ti_donationd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_register_ti_birthday:
                DateTxt dateTxt1 = new DateTxt("01", "01", "1950", "01-01-1950");
                showCalender(getActivity(), "birthday", fragmentRegisterTietBirthday, dateTxt1);
                break;
            case R.id.fragment_register_ti_donationd:
                DateTxt dateTxt2 = new DateTxt("01", "01", "1950", "01-01-1950");
                showCalender(getActivity(), "birthday", fragmentRegisterTietDonationd, dateTxt2);
                break;
        }
    }

    @OnClick(R.id.fragment_register_but_create)
    public void onViewClicked() {
        String name = fragmentRegisterTiName.getEditText().getText().toString();
        String email = fragmentRegisterTiEmail.getEditText().getText().toString();
        String birthDate = fragmentRegisterTiBirthday.getEditText().getText().toString();
        int idCity = cityAdapter.selectedId;
        String phone = fragmentRegisterTiPhone.getEditText().getText().toString();
        String donationDAte = fragmentRegisterTiDonationd.getEditText().getText().toString();
        String pass = fragmentRegisterTiPassword.getEditText().getText().toString();
        String confirmPass = fragmentRegisterTiConfirma.getEditText().getText().toString();
        int bloodId = bloodAdapter.selectedId;
        signUp(name, email, birthDate, idCity, phone, donationDAte, pass, confirmPass, bloodId);
    }

    private void signUp(String name, String email, String birthDate, int id, String phone, String donationDAte, String pass, String confirmPass, int bloodId) {
        getClient().signUp(name, email, birthDate, id, phone, donationDAte, pass, confirmPass, bloodId).enqueue(new Callback<SignUp>() {
            @Override
            public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    replaceFragment(getActivity().getSupportFragmentManager(), R.id.user_cycle_frame, new LoginFragment());


                } else {
                    Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUp> call, Throwable t) {

            }
        });
    }
}
