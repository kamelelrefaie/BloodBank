package com.example.android.bloodbank.helper;

import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.android.bloodbank.adapter.SpinnerAdapter1;
import com.example.android.bloodbank.adapter.SpinnerAdapterCateg;
import com.example.android.bloodbank.data.model.category.Category;
import com.example.android.bloodbank.data.model.generalResponse.GeneralResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  GeneralRequest {
    public static void getData(Call<GeneralResponse> call, final SpinnerAdapter1 adapter, final String hint, final  Spinner spinner) {
        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        adapter.setData(response.body().getData(), hint);
                        spinner.setAdapter(adapter);
                    }
                } catch (Exception e) {
                }
            }
            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
            }
        });
    }

    public static void getDataPost(Call<Category> call, final SpinnerAdapterCateg adapter, final String hint, final  Spinner spinner) {
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        adapter.setData(response.body().getData(), hint);
                        spinner.setAdapter(adapter);
                    }
                } catch (Exception e) {
                }
            }
            @Override
            public void onFailure(Call<Category> call, Throwable t) {
            }
        });
    }
    public static void getData(Call<GeneralResponse> call, final SpinnerAdapter1 adapter, final String hint, final Spinner spinner, final AdapterView.OnItemSelectedListener listener) {
        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        adapter.setData(response.body().getData(), hint);
                        spinner.setAdapter(adapter);
                        spinner.setOnItemSelectedListener(listener);
                    }
                } catch (Exception e) {
                }
            }
            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
            }
        });
    }
}