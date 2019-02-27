package app.food.delivery.retrofit;


import java.util.List;

import app.food.delivery.model.FoodAddModel;
import app.food.delivery.model.FoodListModel;
import app.food.delivery.model.SigninModel;


import app.food.delivery.model.ForgotPasswordModel;
import app.food.delivery.model.RegisterModel;
import app.food.delivery.model.SigninModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

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

    @Multipart
    @POST("food_post")
    Call<FoodAddModel> addFood(@Part("user_id") RequestBody partMap,
                               @Part("foodname") RequestBody foodname,
                               @Part("description") RequestBody description,
                               @Part("price") RequestBody price,
                               @Part MultipartBody.Part[] surveyImage);

  @FormUrlEncoded
  @POST("get_food")
  Call<FoodListModel> getFoodList(@Field("page") int page);


}
