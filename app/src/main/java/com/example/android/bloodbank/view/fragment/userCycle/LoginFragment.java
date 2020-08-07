package com.example.android.bloodbank.view.fragment.userCycle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.data.model.login.Login;
import com.example.android.bloodbank.view.activity.homeCycle.HomeActivity;
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

public class LoginFragment extends BaseFragment {
    @BindView(R.id.fragment_login_but_login)
    Button fragmentLoginButLogin;
    Unbinder unbinder;
    @BindView(R.id.fragment_login_txt_forgetPassword)
    TextView fragmentLoginTxtForgetPassword;
    @BindView(R.id.fragment_login_txt_phone)
    TextInputLayout fragmentLoginTxtPhone;
    @BindView(R.id.fragment_login_txt_password)
    TextInputLayout fragmentLoginTxtPassword;
    @BindView(R.id.fragmrnt_login_but_create)
    Button fragmrntLoginButCreate;
    private static String API_TOKEN;
    @BindView(R.id.fragmrnt_login_remember_cb)
    CheckBox fragmrntLoginRememberCb;

    public static String getApiToken() {
        return API_TOKEN;
    }

    private SharedPreferences sharedPreferences;

    private static final String NAME_LOG = "";

    private View view;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setUpActivity();
        view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onBack() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fragment_login_but_login)
    public void onLoginClicked() {
        String phone = fragmentLoginTxtPhone.getEditText().getText().toString();
        String password = fragmentLoginTxtPassword.getEditText().getText().toString();
        getLogin(phone, password);
    }

    private void getLogin(String phone, String password) {
        getClient().onLogin(phone, password).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                try {

                    if (response.body().getStatus() == 1) {
                        API_TOKEN = response.body().getData().getApiToken();
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), HomeActivity.class));

                    } else {
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), HomeActivity.class));
                    }

                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });
    }


    @OnClick(R.id.fragment_login_txt_forgetPassword)
    public void onForgetPassClicked() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.user_cycle_frame, new ForgetPassFragment());

    }

    @OnClick(R.id.fragmrnt_login_but_create)
    public void onCreateClicked() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.user_cycle_frame, new CreateAccFragment());
    }

}
