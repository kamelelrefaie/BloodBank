package com.example.android.bloodbank.view.fragment.homeCycle.moreContainer;

import android.os.Bundle;
import android.telephony.mbms.StreamingServiceInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.data.model.contactUs.ContactUs;
import com.example.android.bloodbank.data.model.login.Login;
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

public class ContactUsFragment extends BaseFragment {

    @BindView(R.id.fragment_home_more_Contact_US_title_et)
    EditText fragmentHomeMoreContactUSTitleEt;
    @BindView(R.id.fragment_home_more_Contact_US_message_et)
    EditText fragmentHomeMoreContactUSMessageEt;
    @BindView(R.id.fragment_home_more_Contact_US_send_but)
    Button fragmentHomeMoreContactUSSendBut;
    Unbinder unbinder;
    private View view;

    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setUpActivity();
        view = inflater.inflate(R.layout.fragment_home_more_contact_us, container, false);


        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @OnClick(R.id.fragment_home_more_Contact_US_send_but)
    public void onViewClicked() {
        String title = fragmentHomeMoreContactUSTitleEt.getEditableText().toString();
        String message = fragmentHomeMoreContactUSMessageEt.getEditableText().toString();
        String api_token=LoginFragment.getApiToken();
     sendContact(title,message,api_token);
    }

    private void sendContact(String title, String message, String api_token) {
    getClient().setContactUs(title,message,api_token).enqueue(new Callback<ContactUs>() {
        @Override
        public void onResponse(Call<ContactUs> call, Response<ContactUs> response) {
            if (response.body().getStatus()==1) {
                Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<ContactUs> call, Throwable t) {

        }
    });
    }
}

