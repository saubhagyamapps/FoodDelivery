package app.food.delivery.retrofit;


import app.food.delivery.model.RegisterModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
  /*
    @FormUrlEncoded
    @POST("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Field("api_key") String apiKey);*/

  @FormUrlEncoded
  @POST("signup")
  Call<RegisterModel> getRegister(@Field("username") String username,
                                  @Field("email") String email,
                                  @Field("mobile") String mobile,
                                  @Field("password") String password,
                                  @Field("firebase_id") String firebase_id,
                                  @Field("device_id") String device_id);
}
