package app.food.delivery.retrofit;



import app.food.delivery.model.SigninModel;

import app.food.delivery.model.ForgotPasswordModel;

import app.food.delivery.model.RegisterModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("signin")
    Call<SigninModel> getLoginData(@Field("email") String email,
                              @Field("password") String password,
                              @Field("firebase_id") String firebase_id,
                              @Field("device_id") String device_id);

  @FormUrlEncoded
  @POST("signup")
  Call<RegisterModel> getRegister(@Field("username") String username,
                                  @Field("email") String email,
                                  @Field("mobile") String mobile,
                                  @Field("password") String password,
                                  @Field("firebase_id") String firebase_id,
                                  @Field("device_id") String device_id);


  @FormUrlEncoded
  @POST("forgot_password")
  Call<ForgotPasswordModel> getPassword(@Field("email") String email);

}
