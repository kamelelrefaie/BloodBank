package com.example.android.bloodbank.data.api;

import android.app.Notification;

import com.example.android.bloodbank.data.model.category.Category;
import com.example.android.bloodbank.data.model.contactUs.ContactUs;
import com.example.android.bloodbank.data.model.donationRequestCreate.DonationRequestCreate;
import com.example.android.bloodbank.data.model.donationRequests.DonationRequests;
import com.example.android.bloodbank.data.model.generalResponse.GeneralResponse;
import com.example.android.bloodbank.data.model.generalResponse.GeneralResponseData;
import com.example.android.bloodbank.data.model.login.Login;
import com.example.android.bloodbank.data.model.myFavourites.MyFavourites;
import com.example.android.bloodbank.data.model.newPassword.NewPassword;
import com.example.android.bloodbank.data.model.notification.NotificationSetting;
import com.example.android.bloodbank.data.model.notifications.Notifications;
import com.example.android.bloodbank.data.model.postDetails.PostDetails;
import com.example.android.bloodbank.data.model.postRequest.PostRequest;
import com.example.android.bloodbank.data.model.postToggleFavourite.PostToggleFavourite;
import com.example.android.bloodbank.data.model.profile.Profile;
import com.example.android.bloodbank.data.model.resetPassword.ResetPasswors;
import com.example.android.bloodbank.data.model.signUp.SignUp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    //login
    @POST("login")
    @FormUrlEncoded
    Call<Login> onLogin(@Field("phone") String phone , @Field("password") String password);

    //resetpass
    @POST("reset-password")
    @FormUrlEncoded
    Call<ResetPasswors> toReset(@Field("phone") String phone);

    //make new pass
    @POST("new-password")
    @FormUrlEncoded
    Call<NewPassword> newPass(
            @Field("pin_code") String pinCode
            , @Field("password") String password
            , @Field("password_confirmation") String conPfass
            , @Field("phone") String phone);

    //make new pass


    @GET("blood-types")
    Call<GeneralResponse> getBloodTypes();

    @GET("governorates")
    Call<GeneralResponse> getGover();

    @GET("cities")
    Call<GeneralResponse> getCity(@Query("governorate_id") int id);


    @POST("signup")
    @FormUrlEncoded
    Call<SignUp> signUp(
            @Field("name") String name
            , @Field("email") String email
            , @Field("birth_date") String birthDate
            , @Field("city_id") int idCity
            , @Field("phone") String phone
            , @Field("donation_last_date") String donationDate
            , @Field("password") String password
            , @Field("password_confirmation") String conPfass
            , @Field("blood_type_id") int bloodId);


    @GET("donation-requests")
    Call<DonationRequests> getDonationsRequests(
            @Query("api_token") String apiToken
            , @Query("page") int page);

    @GET("donation-requests")
    Call<DonationRequests> getDonationsRequests(
            @Query("api_token") String apiToken
            , @Query("page") int page
            , @Query("blood_type_id") int bloodTyped
            , @Query("city_id") int cityId);

    @POST("profile")
    @FormUrlEncoded
    Call<Profile> setUpProfile(
            @Field("name") String name
            , @Field("email") String email
            , @Field("birth_date") String birthDate
            , @Field("city_id") int idCity
            , @Field("phone") String phone
            , @Field("donation_last_date") String donationDate
            , @Field("password") String password
            , @Field("password_confirmation") String conPfass
            , @Field("blood_type_id") int bloodId
            , @Field("api_token") String apiToken);


    @POST("contact")
    @FormUrlEncoded
    Call<ContactUs> setContactUs(
            @Field("title") String title
            , @Field("message") String message
            , @Field("api_token") String apiToken);

    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationSetting> getNotificationSettings(@Field("api_token") String apiToken);

    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationSetting> setNotificationSettings(@Field("api_token") String apiToken
            , @Field("governorates[]") List<Integer> gov
            , @Field("blood_types[]") List<Integer> blood
    );

    @GET("notifications")
    Call<Notifications> getNotifications(
            @Query("api_token") String apiToken
            , @Query("page") int page);

    @GET("posts")
    Call<PostRequest> getAllPosts(
            @Query("api_token") String apiToken
            , @Query("page") int page);


    @POST("post-toggle-favourite")
    @FormUrlEncoded
    Call<PostToggleFavourite> setFavPost(@Field("api_token") String apiToken,
                                         @Field("post_id") int postId);

    @GET("my-favourites")
    Call<MyFavourites> getAllFavPost(
            @Query("api_token") String apiToken);

    @POST("donation-request/create")
    @FormUrlEncoded
    Call<DonationRequestCreate> createDonation(
            @Field("api_token") String apiToken
            ,@Field("name") String name
            , @Field("patient_age") String age
            , @Field("blood_type_id") int bloodId
            , @Field("bags_num") String bagsNum
            , @Field("hospital_name") String hosName
            , @Field("patient_name") String patientName
            , @Field("hospital_address") String hosAdress
            , @Field("city_id") int idCity
            , @Field("phone") String phone
            , @Field("notes") String notes
           //lattide awhas
            );

    @GET("posts")
    Call<PostRequest> getPostFilter(
            @Query("api_token") String apiToken
            ,@Query("page") int page
            ,@Query("keyword") String keyWord,
            @Query("category_id") int id
    );
    @GET("post")
    Call<PostDetails> getPostDetails(
            @Query("api_token") String apiToken
            ,@Query("page") int page
            ,@Query("post_id") int id
    );

    @GET("categories")
    Call<Category> getCategories();
}
