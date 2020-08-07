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

public class SpinnerAdapter1 extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    public List<GeneralResponseData> list = new ArrayList<>();
    public int selectedId = 0;

    public SpinnerAdapter1(Context applicationContext) {
        this.context = applicationContext;
        inflater = (LayoutInflater.from(applicationContext));
    }

    public void setData(List<GeneralResponseData> list, String hint) {
        this.list = new ArrayList<>();
        this.list.add(new GeneralResponseData(0, hint));
        this.list.addAll(list);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.custom_spinner_items, null);
        TextView spinnerTV = convertView.findViewById(R.id.item_spinner_txt);
//        ImageView spinnerIv = convertView.findViewById(R.id.ivSpinnerLayout);
        spinnerTV.setText(list.get(position).getName());
        selectedId = list.get(position).getId();
        return convertView;
    }
}

