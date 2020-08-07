package com.example.android.bloodbank.view.fragment.userCycle;

import android.os.Bundle;

import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.data.model.resetPassword.ResetPasswors;
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

public class ForgetPassFragment extends BaseFragment {
    @BindView(R.id.fragment_forget_ti_phone)
    TextInputLayout fragmentForgetTiPhone;
    @BindView(R.id.fragment_forget_but_send)
    Button fragmentForgetButSend;
    Unbinder unbinder;
    private View view;

    public ForgetPassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_forget, container, false);


        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fragment_forget_but_send)
    public void onViewClicked() {
        String phone= fragmentForgetTiPhone.getEditText().getText().toString();
        sendPhone(phone);
    }

    private void sendPhone(String phone) {
        getClient().toReset(phone).enqueue(new Callback<ResetPasswors>() {
            @Override
            public void onResponse(Call<ResetPasswors> call, Response<ResetPasswors> response) {
                if (response.body().getStatus()==1) {
                    Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    replaceFragment(getActivity().getSupportFragmentManager(), R.id.user_cycle_frame, new NewPasswordFragment());
                }else{
                    Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResetPasswors> call, Throwable t) {

            }
        });
    }
}
