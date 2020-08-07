package com.example.android.bloodbank.view.fragment.userCycle;

import android.os.Bundle;

import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.data.model.newPassword.NewPassword;
import com.example.android.bloodbank.view.fragment.BaseFragment;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.bloodbank.data.api.AppClient.getClient;
import static com.example.android.bloodbank.helper.Helper.replaceFragment;

public class NewPasswordFragment extends BaseFragment {
    @BindView(R.id.fragment_new_pass_but_change)
    Button fragmentNewPassButChange;
    Unbinder unbinder;
    @BindView(R.id.fragment_new_pass_txt_code)
    TextInputLayout fragmentNewPassTxtCode;
    @BindView(R.id.fragment_new_pass_txt_newpass)
    TextInputLayout fragmentNewPassTxtNewpass;
    @BindView(R.id.fragment_new_pass_confirm_txt_newpass)
    TextInputLayout fragmentNewPassConfirmTxtNewpass;
    @BindView(R.id.fragment_new_pass_txt_phone)
    TextInputLayout fragmentNewPassTxtPhone;
    private View view;

    public NewPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setUpActivity();
        view = inflater.inflate(R.layout.fragment_new_password, container, false);
        unbinder = ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fragment_new_pass_but_change)
    public void onViewClicked() {
        String phone= fragmentNewPassTxtPhone.getEditText().getText().toString();
        String code = fragmentNewPassTxtCode.getEditText().getText().toString();
        String pass = fragmentNewPassTxtNewpass.getEditText().getText().toString();
        String confirmPass = fragmentNewPassConfirmTxtNewpass.getEditText().getText().toString();
        sendNewPass(code, pass, confirmPass,phone);
    }

    private void sendNewPass(String code, String pass, String confirmPass, String phone) {
        getClient().newPass(code, pass, confirmPass,phone).enqueue(new Callback<NewPassword>() {
            @Override
            public void onResponse(Call<NewPassword> call, Response<NewPassword> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    replaceFragment(getActivity().getSupportFragmentManager(), R.id.user_cycle_frame, new LoginFragment());
                } else {
                    Toast.makeText(baseActivity, response.body().getMsg() + "sdsdsds", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<NewPassword> call, Throwable t) {

            }
        });

    }
}
