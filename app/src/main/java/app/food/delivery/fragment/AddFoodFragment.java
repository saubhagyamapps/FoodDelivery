package app.food.delivery.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gun0912.tedpicker.Config;
import com.gun0912.tedpicker.ImagePickerActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import app.food.delivery.R;
import app.food.delivery.model.FoodAddModel;
import app.food.delivery.model.FoodCategoryModel;
import app.food.delivery.util.Constant;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFoodFragment extends Fragment {
    View mView;
    EditText food_Name, food_Desc, food_Price, food_category;
    ImageView food_Image_One, food_Image_Two, food_Image_Three;
    LinearLayout Food_Image_Layout;
    Button btn_Add_Image;
    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    private static final String TAG = "AddFoodFragment";
    String imgesFlag = "0";
    private ArrayList<Uri> image_uris;
    List<String> categoryAdepter;
    CharSequence colors[];
    CardView food_category_card;
    CharSequence[] cs;
    String mFoodCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_add_food, container, false);
        getActivity().setTitle("Add Food");

        initialization();
        return mView;

    }


    private void initialization() {
        Constant.setSession(getActivity());
        food_Name = mView.findViewById(R.id.food_name);
        food_category_card = mView.findViewById(R.id.food_category_card);
        food_category = mView.findViewById(R.id.food_category);
        food_category.setInputType(InputType.TYPE_NULL);
        food_Desc = mView.findViewById(R.id.food_desc);
        food_Price = mView.findViewById(R.id.food_price);
        food_Image_One = mView.findViewById(R.id.food_image_one);
        food_Image_Two = mView.findViewById(R.id.food_image_two);
        food_Image_Three = mView.findViewById(R.id.food_image_three);
        Food_Image_Layout = mView.findViewById(R.id.food_image_layout);
        btn_Add_Image = mView.findViewById(R.id.btn_add_image);

        btn_Add_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImages();
            }
        });
        submitButtonClick();

        categoryAPICALL();
    }


    private void categoryAPICALL() {
        Call<FoodCategoryModel> categoryModelCall = Constant.apiService.getFoodCategory();
        categoryModelCall.enqueue(new Callback<FoodCategoryModel>() {
            @Override
            public void onResponse(Call<FoodCategoryModel> call, Response<FoodCategoryModel> response) {
                Log.e(TAG, "onResponse: " + response.body().getResult());
                List<FoodCategoryModel.ResultBean> databean = response.body().getResult();

                categoryAdepter = new ArrayList<>();
                for (int i = 0; i < databean.size(); i++) {
                    categoryAdepter.add(i, databean.get(i).getCategory_name());
                }
                cs = categoryAdepter.toArray(new CharSequence[categoryAdepter.size()]);
                food_category.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CreateAlertDialog(cs);
                    }
                });

            }

            @Override
            public void onFailure(Call<FoodCategoryModel> call, Throwable t) {

            }
        });
    }

    private void submitButtonClick() {
        mView.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (food_Name.getText().toString().trim().equals("")) {
                    food_Name.setError("Required");
                } else if (food_category.getText().toString().trim().equals("")) {
                    food_category.setError("Required");
                } else if (food_Desc.getText().toString().trim().equals("")) {
                    food_Desc.setError("Required");
                } else if (food_Price.getText().toString().trim().equals("")) {
                    food_Price.setError("Required");
                } else if (imgesFlag.equals("0")) {
                    Constant.toast("Please selected images", getContext());
                } else {
                    APIcall();
                }
            }
        });
    }

    private void getImages() {
        Config config = new Config();
        config.setSelectionMin(3);
        config.setSelectionLimit(3);
        ImagePickerActivity.setConfig(config);
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);

    }

    @Override
    public void onActivityResult(int requestCode, int resuleCode, Intent intent) {
        super.onActivityResult(requestCode, resuleCode, intent);

        if (requestCode == INTENT_REQUEST_GET_IMAGES && resuleCode == Activity.RESULT_OK) {

            image_uris = intent.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
            Log.e(TAG, "onActivityResult: " + image_uris);
            if (!image_uris.isEmpty()) {
                Food_Image_Layout.setVisibility(View.VISIBLE);
                imgesFlag = "1";
            }

            for (int i = 0; i < image_uris.size(); i++) {
                if (i == 0) {
                    try {
                        food_Image_One.setImageBitmap(BitmapFactory.decodeFile(image_uris.get(i).toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (i == 1) {
                    try {

                        food_Image_Two.setImageBitmap(BitmapFactory.decodeFile(image_uris.get(i).toString()));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {

                        food_Image_Three.setImageBitmap(BitmapFactory.decodeFile(image_uris.get(i).toString()));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void APIcall() {

        Constant.progressDialog(getActivity());
        MultipartBody.Part[] surveyImagesParts = new MultipartBody.Part[image_uris.size()];

        for (int index = 0; index < image_uris.size(); index++) {
            Log.d(TAG, "requestUploadSurvey: survey image " + index + "  " + image_uris.get(index));
            File file = new File(image_uris.get(index).toString());
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
            surveyImagesParts[index] = MultipartBody.Part.createFormData("images[]", file.getName(), surveyBody);
        }
        RequestBody id =
                RequestBody.create(MediaType.parse("multipart/form-data"), Constant.mUserId);
        RequestBody food_name =
                RequestBody.create(MediaType.parse("multipart/form-data"), food_Name.getText().toString().trim());
        RequestBody food_p =
                RequestBody.create(MediaType.parse("multipart/form-data"), food_Price.getText().toString().trim());
        RequestBody food_category =
                RequestBody.create(MediaType.parse("multipart/form-data"), mFoodCategory);
        RequestBody food_dec =
                RequestBody.create(MediaType.parse("multipart/form-data"), food_Desc.getText().toString().trim());
        Call<FoodAddModel> addModelCall = Constant.apiService.addFood(id, food_name, food_dec, food_p, food_category, surveyImagesParts);
        addModelCall.enqueue(new Callback<FoodAddModel>() {
            @Override
            public void onResponse(Call<FoodAddModel> call, Response<FoodAddModel> response) {
                Constant.progressBar.dismiss();
                Constant.toast(response.body().getMessgae(), getActivity());
                Log.e(TAG, "onResponse: " + response);
                if (response.body().getStatus().equals("0")) {
                    getFragmentManager().beginTransaction().replace(R.id.content_frame, new FoodFragment()).commit();
                }
            }

            @Override
            public void onFailure(Call<FoodAddModel> call, Throwable t) {
                Constant.progressBar.dismiss();
                Log.e(TAG, "onFailure: ");
            }
        });
    }

    public void CreateAlertDialog(CharSequence[] cs) {


        AlertDialog.Builder alertbox = new AlertDialog.Builder(getActivity());

        alertbox.setTitle("Pick one item")
                .setItems(cs, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int pos) {
                        mFoodCategory = String.valueOf(pos);
                        Log.e(TAG, "onClick: " + pos);
                        food_category.setText(categoryAdepter.get(pos));
                    }
                });
        alertbox.show();
    }
}
