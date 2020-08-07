package com.example.android.bloodbank.view.fragment.homeCycle.homeContainer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.adapter.PostFragmentAdapter;
import com.example.android.bloodbank.adapter.SpinnerAdapterCateg;
import com.example.android.bloodbank.data.model.donationRequests.DonationRequests;
import com.example.android.bloodbank.data.model.postRequest.Category;
import com.example.android.bloodbank.data.model.postRequest.PostData;
import com.example.android.bloodbank.data.model.postRequest.PostRequest;
import com.example.android.bloodbank.helper.OnEndLess;
import com.example.android.bloodbank.view.fragment.BaseFragment;
import com.example.android.bloodbank.view.fragment.homeCycle.AddDonationFragment;
import com.example.android.bloodbank.view.fragment.homeCycle.PostDetailsFragment;
import com.example.android.bloodbank.view.fragment.userCycle.LoginFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.bloodbank.data.api.AppClient.getClient;
import static com.example.android.bloodbank.helper.GeneralRequest.getDataPost;
import static com.example.android.bloodbank.helper.Helper.replaceFragment;

public class PostsFragment extends BaseFragment {
    @BindView(R.id.posts_fragment_list_rv)
    RecyclerView postsFragmentListRv;
    Unbinder unbinder;
    @BindView(R.id.posts_fragment_fbtn_add)
    FloatingActionButton postsFragmentFbtnAdd;
    @BindView(R.id.post_fragment_prevention_spin)
    Spinner postFragmentPreventionSpin;
    @BindView(R.id.post_fragment_search_etxt)
    EditText postFragmentSearchEtxt;
    @BindView(R.id.post_fragment_search_img)
    ImageView postFragmentSearchImg;
    private View view;
    private LinearLayoutManager linearLayoutManager;
    public PostFragmentAdapter postFragmentAdapter;
    private List<PostData> postDataList = new ArrayList<>();
    private SpinnerAdapterCateg prevSpin;
    private Integer maxPage = 0;
    private OnEndLess onEndLess;
    private boolean filter = false;


    public PostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setUpActivity();
        view = inflater.inflate(R.layout.fragment_posts, container, false);
        unbinder = ButterKnife.bind(this, view);

        prevSpin = new SpinnerAdapterCateg(getActivity());
        getDataPost(getClient().getCategories(), prevSpin, "الوقايه", postFragmentPreventionSpin);
        init();
        return view;
    }

    private void init() {
        linearLayoutManager = new LinearLayoutManager(getActivity());

        postsFragmentListRv.setLayoutManager(linearLayoutManager);
        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;


                        if (filter) {

                            onFilter(current_page);
                        } else {
                            getAllPosts(current_page);
                        }

                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;

                }
            }
        };
        postsFragmentListRv.addOnScrollListener(onEndLess);
        postFragmentAdapter = new PostFragmentAdapter(getActivity(), postDataList);
        postsFragmentListRv.setAdapter(postFragmentAdapter);
        postsFragmentListRv.setLayoutManager(linearLayoutManager);

        getAllPosts(1);

    }

    private void getAllPosts(int page) {
       Call<PostRequest> call= getClient().getAllPosts(LoginFragment.getApiToken(), page);
       startCall(call,page);
    }


    @OnClick(R.id.post_fragment_search_img)
    public void onViewClicked() {
        onFilter(1);
    }

    private void onFilter(int page) {
        filter = true;
        onEndLess.previousTotal = 0; // The total number of items in the dataset after the last load
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
        postDataList=new ArrayList<>();
        String getTxt= postFragmentSearchEtxt.getText().toString();
        postFragmentAdapter=new PostFragmentAdapter(getActivity(),postDataList);
        postsFragmentListRv.setAdapter(postFragmentAdapter);
        Call<PostRequest> call = getClient()
                .getPostFilter(LoginFragment.getApiToken(), page
                        , getTxt,prevSpin.selectedId);
        startCall(call,page);
    }

    private void startCall(Call<PostRequest> call, int page) {
        call.enqueue(new Callback<PostRequest>() {
            @Override
            public void onResponse(Call<PostRequest> call, Response<PostRequest> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        maxPage = response.body().getData().getLastPage();
                        postDataList.addAll(response.body().getData().getData());
                        postFragmentAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<PostRequest> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.posts_fragment_fbtn_add)
    public void onFBtnClicked() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_frame, new AddDonationFragment());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
