package app.food.delivery.retrofit;


import app.food.delivery.model.SigninModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("signin")
    Call<SigninModel> getLoginData (@Field("username") String username,
                              @Field("password") String password,
                              @Field("device_id") String device_id,
                              @Field("firebase_id") String firebase_id);
}
