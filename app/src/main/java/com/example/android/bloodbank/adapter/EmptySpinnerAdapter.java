package com.example.android.bloodbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.data.model.generalResponse.GeneralResponseData;

import java.util.ArrayList;
import java.util.List;

public class EmptySpinnerAdapter extends BaseAdapter {
    private Context context;
    private List<GeneralResponseData> generalResponseDataList = new ArrayList<>();
    private LayoutInflater inflter;
    public int selectedId = 0;


    public EmptySpinnerAdapter(Context applicationContext) {
        this.context = applicationContext;
        this.generalResponseDataList = generalResponseDataList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public void setData(List<GeneralResponseData> generalResponseDataList, String hint) {
        generalResponseDataList.add(new GeneralResponseData(0, hint));
        this.generalResponseDataList = generalResponseDataList;
    }

    @Override
    public int getCount() {
        return generalResponseDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);

        TextView names = (TextView) view.findViewById(R.id.item_spinner_txt);

        names.setText(generalResponseDataList.get(i).getName());
        selectedId = generalResponseDataList.get(i).getId();

        return view;
    }

}
