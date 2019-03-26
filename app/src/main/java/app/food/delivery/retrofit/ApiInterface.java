package app.food.delivery.retrofit;


import app.food.delivery.model.AddNewAddressModel;
import app.food.delivery.model.AddToCartModel;
import app.food.delivery.model.CartViewModel;
import app.food.delivery.model.DetailFoodModel;
import app.food.delivery.model.FoodAddModel;
import app.food.delivery.model.FoodCategoryModel;
import app.food.delivery.model.FoodListModel;
import app.food.delivery.model.ForgotPasswordModel;
import app.food.delivery.model.OderConformModel;
import app.food.delivery.model.ProfileModel;
import app.food.delivery.model.RegisterModel;
import app.food.delivery.model.RemoveCartModel;
import app.food.delivery.model.ResetPasswordModel;
import app.food.delivery.model.SigninModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
                               @Part("foodcategory_id") RequestBody foodcategory_id,
                               @Part MultipartBody.Part[] surveyImage);

    @FormUrlEncoded
    @POST("get_food")
    Call<FoodListModel> getFoodList(@Field("page") int page);


    @FormUrlEncoded
    @POST("get_detilfood")
    Call<DetailFoodModel> getDetailFood(@Field("id") String id,
                                        @Field("user_id") String user_id);

    @GET("foodcategory")
    Call<FoodCategoryModel> getFoodCategory();

    @FormUrlEncoded
    @POST("reset_password")
    Call<ResetPasswordModel> getResetPassword(@Field("id") String id,
                                              @Field("oldpass") String old_password,
                                              @Field("newpass") String new_password);

    @FormUrlEncoded
    @POST("add_to_cart")
    Call<AddToCartModel> addtoCartItem(@Field("user_id") String user_id,
                                       @Field("food_id") String food_id,
                                       @Field("quantity") String quantity,
                                       @Field("price") int price);

    @FormUrlEncoded
    @POST("remove_cart")
    Call<RemoveCartModel> removeCart(@Field("user_id") String user_id,
                                     @Field("food_id") String food_id);

    @FormUrlEncoded
    @POST("view_cart")
    Call<CartViewModel> cartViewData(@Field("user_id") String user_id);

    @Multipart
    @POST("edit_profile")
    Call<ProfileModel> EditProfileWIthImage(@Part("id") RequestBody id,
                                            @Part("username") RequestBody username,
                                            @Part("gender") RequestBody gender,
                                            @Part("address") RequestBody address,
                                            @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("edit_profile")
    Call<ProfileModel> getEditProfile(@Field("id") String id,
                                      @Field("username") String username,
                                      @Field("gender") String gender,
                                      @Field("address") String address);

    @FormUrlEncoded
    @POST("update_address")
    Call<AddNewAddressModel> updateAddress(@Field("user_id") String id,
                                           @Field("address") String username);

    @FormUrlEncoded
    @POST("verification")
    Call<OderConformModel> oderConform(@Field("food_id") String food_id,
                                       @Field("user_id") String user_id);

}
