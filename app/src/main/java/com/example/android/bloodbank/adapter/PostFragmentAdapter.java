package com.example.android.bloodbank.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.bloodbank.R;
import com.example.android.bloodbank.data.model.postRequest.PostData;
import com.example.android.bloodbank.data.model.postToggleFavourite.PostToggleFavourite;
import com.example.android.bloodbank.data.model.postToggleFavourite.PostToggleFavouriteData;
import com.example.android.bloodbank.view.activity.BaseActivity;
import com.example.android.bloodbank.view.fragment.homeCycle.PostDetailsFragment;
import com.example.android.bloodbank.view.fragment.homeCycle.moreContainer.FavPostFragment;
import com.example.android.bloodbank.view.fragment.userCycle.LoginFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.bloodbank.data.api.AppClient.getClient;
import static com.example.android.bloodbank.helper.Helper.replaceFragment;

public class PostFragmentAdapter extends RecyclerView.Adapter<PostFragmentAdapter.PostsFragmentHolder> {
  private   int  postId ;


    private Context context;
    private BaseActivity activity;
    private List<PostData> postDataList = new ArrayList<>();
    //  private List<Integer> postToggleFavouriteDataList = new ArrayList<>();


    public int getPostId() {
        return postId;
    }

    public PostFragmentAdapter(Activity activity, List<PostData> postDataList) {
        this.context = activity;
        this.activity = (BaseActivity) activity;
        this.postDataList = postDataList;
    }


    @NonNull
    @Override
    public PostsFragmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post,
                parent, false);

        return new PostsFragmentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsFragmentHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(PostsFragmentHolder holder, int position) {
        holder.itemPostTitleTxt.setText(postDataList.get(position).getTitle());
        Glide.with(activity)
                .load(postDataList.get(position).getThumbnailFullPath())
                .into(holder.itemPostMainImg);

        if (!postDataList.get(position).getIsFavourite()) {
            holder.itemPostFavFbtn.setImageResource(R.drawable.ic_favorite_white);

        } else {
            holder.itemPostFavFbtn.setImageResource(R.drawable.ic_fav);
        }
    }


    private void setAction(final PostsFragmentHolder holder, final int position) {

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
  postId=postDataList.get(position).getId();
                replaceFragment(activity.getSupportFragmentManager(), R.id.home_frame, new PostDetailsFragment());
            }
        });

        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return postDataList.size();
    }


    public class PostsFragmentHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_post_main_img)
        ImageView itemPostMainImg;
        @BindView(R.id.item_post_title_txt)
        TextView itemPostTitleTxt;
        @BindView(R.id.item_post_fav_fbtn)
        FloatingActionButton itemPostFavFbtn;

        public int position;
        private View view;

        public PostsFragmentHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }

        @OnClick(R.id.item_post_fav_fbtn)
        public void onViewClicked() {

            postDataList.get(position).setIsFavourite(!postDataList.get(position).getIsFavourite());

            if (!postDataList.get(position).getIsFavourite()) {
                itemPostFavFbtn.setImageResource(R.drawable.ic_favorite_white);
            } else {
                itemPostFavFbtn.setImageResource(R.drawable.ic_fav);
            }

            getClient().setFavPost(LoginFragment.getApiToken(), postDataList.get(getAdapterPosition()).getId()).enqueue(new Callback<PostToggleFavourite>() {
                @Override
                public void onResponse(Call<PostToggleFavourite> call, Response<PostToggleFavourite> response) {
                    try {
                        if (response.body().getStatus() == 1) {
                        } else {

                            postDataList.get(position).setIsFavourite(!postDataList.get(position).getIsFavourite());

                            if (!postDataList.get(position).getIsFavourite()) {
                                itemPostFavFbtn.setImageResource(R.drawable.ic_favorite_white);
                            } else {
                                itemPostFavFbtn.setImageResource(R.drawable.ic_fav);
                            }
                        }

                        Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                    }


                }

                @Override
                public void onFailure(Call<PostToggleFavourite> call, Throwable t) {

                    postDataList.get(position).setIsFavourite(!postDataList.get(position).getIsFavourite());

                    if (!postDataList.get(position).getIsFavourite()) {
                        itemPostFavFbtn.setImageResource(R.drawable.ic_favorite_white);
                    } else {
                        itemPostFavFbtn.setImageResource(R.drawable.ic_fav);
                    }
                }
            });

        }
    }
}
