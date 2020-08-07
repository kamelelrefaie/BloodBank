package com.example.android.bloodbank.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.bloodbank.R;
import com.example.android.bloodbank.data.model.myFavourites.MyFavouritesData;
import com.example.android.bloodbank.data.model.postToggleFavourite.PostToggleFavourite;
import com.example.android.bloodbank.view.activity.BaseActivity;
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

public class FavPostAdapter extends RecyclerView.Adapter<FavPostAdapter.FavPostViewHolder> {

    private Context context;
    private BaseActivity activity;
    private List<MyFavouritesData> myFavouritesDataList = new ArrayList<>();


    public FavPostAdapter(Activity activity, List<MyFavouritesData> myFavouritesDataList) {
        this.context = activity;
        this.activity = (BaseActivity) activity;
        this.myFavouritesDataList = myFavouritesDataList;
    }


    @Override
    public FavPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post,
                parent, false);

        return new FavPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavPostViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(FavPostViewHolder holder, int position) {
        holder.itemPostTitleTxt.setText(myFavouritesDataList.get(position).getTitle());
        Glide.with(activity)
                .load(myFavouritesDataList.get(position).getThumbnailFullPath())
                .into(holder.itemPostMainImg);
        holder.position = position;
    }

    private void setAction(FavPostViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return myFavouritesDataList.size();
    }


    public class FavPostViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.item_post_fav_fbtn)
        FloatingActionButton itemPostFavFbtn;
        @BindView(R.id.item_post_main_img)
        ImageView itemPostMainImg;
        @BindView(R.id.item_post_title_txt)
        TextView itemPostTitleTxt;
        public int position;

        public FavPostViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.item_post_fav_fbtn)
        public void onViewClicked() {
            myFavouritesDataList.get(position).setIsFavourite(!myFavouritesDataList.get(position).getIsFavourite());

            if (!myFavouritesDataList.get(position).getIsFavourite()) {
                itemPostFavFbtn.setImageResource(R.drawable.ic_favorite_white);
            } else {
                itemPostFavFbtn.setImageResource(R.drawable.ic_fav);
            }

            getClient().setFavPost(LoginFragment.getApiToken(), myFavouritesDataList.get(getAdapterPosition()).getId()).enqueue(new Callback<PostToggleFavourite>() {
                @Override
                public void onResponse(Call<PostToggleFavourite> call, Response<PostToggleFavourite> response) {
                    try {
                        if (response.body().getStatus() == 1) {
                        } else {

                            myFavouritesDataList.get(position).setIsFavourite(!myFavouritesDataList.get(position).getIsFavourite());

                            if (!myFavouritesDataList.get(position).getIsFavourite()) {
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

                    myFavouritesDataList.get(position).setIsFavourite(!myFavouritesDataList.get(position).getIsFavourite());

                    if (!myFavouritesDataList.get(position).getIsFavourite()) {
                        itemPostFavFbtn.setImageResource(R.drawable.ic_favorite_white);
                    } else {
                        itemPostFavFbtn.setImageResource(R.drawable.ic_fav);
                    }
                }
            });
        }
    }

}
