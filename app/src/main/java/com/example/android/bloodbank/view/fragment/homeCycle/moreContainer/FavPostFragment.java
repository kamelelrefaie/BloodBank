package com.example.android.bloodbank.view.fragment.homeCycle.moreContainer;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.adapter.FavPostAdapter;
import com.example.android.bloodbank.adapter.PostFragmentAdapter;
import com.example.android.bloodbank.data.model.myFavourites.MyFavourites;
import com.example.android.bloodbank.data.model.myFavourites.MyFavouritesData;
import com.example.android.bloodbank.helper.OnEndLess;
import com.example.android.bloodbank.view.fragment.BaseFragment;
import com.example.android.bloodbank.view.fragment.userCycle.LoginFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.bloodbank.data.api.AppClient.getClient;

public class FavPostFragment extends BaseFragment {
    @BindView(R.id.fav_post_fragment_list_rv)
    RecyclerView favPostFragmentListRv;
    Unbinder unbinder;
    private View view;
    private LinearLayoutManager linearLayoutManager;
    private OnEndLess onEndLess;
    private int maxPage=0;
    private List<MyFavouritesData> myFavouritesDataList=new ArrayList<>();
    private FavPostAdapter favPostAdapter;

    public FavPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setUpActivity();
        view = inflater.inflate(R.layout.fragment_home_more_fav_post, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        linearLayoutManager = new LinearLayoutManager(getActivity());

        favPostFragmentListRv.setLayoutManager(linearLayoutManager);
        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        getAllFavPosts(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;

                }
            }
        };
        favPostFragmentListRv.addOnScrollListener(onEndLess);
        favPostAdapter = new FavPostAdapter(getActivity(),myFavouritesDataList );
        favPostFragmentListRv.setAdapter(favPostAdapter);
        getAllFavPosts(1);

    }

    private void getAllFavPosts(int current_page) {
        getClient().getAllFavPost(LoginFragment.getApiToken()).enqueue(new Callback<MyFavourites>() {
            @Override
            public void onResponse(Call<MyFavourites> call, Response<MyFavourites> response) {
              try {
                  if (response.body().getStatus()==1) {
                      maxPage=response.body().getData().getLastPage();
                      myFavouritesDataList.addAll(response.body().getData().getData());
                      favPostAdapter.notifyDataSetChanged();
                  }
              }catch (Exception e){

              }
            }

            @Override
            public void onFailure(Call<MyFavourites> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
