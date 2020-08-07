package com.example.android.bloodbank.view.fragment.homeCycle;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.bloodbank.R;
import com.example.android.bloodbank.adapter.PostFragmentAdapter;
import com.example.android.bloodbank.data.model.client.ClientDatum;
import com.example.android.bloodbank.data.model.postDetails.PostDetails;
import com.example.android.bloodbank.data.model.postRequest.PostData;
import com.example.android.bloodbank.view.fragment.BaseFragment;
import com.example.android.bloodbank.view.fragment.homeCycle.homeContainer.PostsFragment;
import com.example.android.bloodbank.view.fragment.userCycle.LoginFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.bloodbank.data.api.AppClient.getClient;

public class PostDetailsFragment extends BaseFragment {
    @BindView(R.id.post_detail_fragment_img)
    ImageView postDetailFragmentImg;
    @BindView(R.id.post_detail_fragment_title_txt)
    TextView postDetailFragmentTitleTxt;
    @BindView(R.id.post_detail_fragment_fav_btn)
    FloatingActionButton postDetailFragmentFavBtn;
    @BindView(R.id.post_detail_fragment_content_txt)
    TextView postDetailFragmentContentTxt;
    Unbinder unbinder;
    private View view;
    public PostData postData;
    private ClientDatum client;

    public PostDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setUpActivity();
        view = inflater.inflate(R.layout.fragment_post_details, container, false);
        unbinder = ButterKnife.bind(this, view);
postDetailFragmentTitleTxt.setText(postData.getTitle());

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.post_detail_fragment_fav_btn)
    public void onViewClicked() {

    }
}
