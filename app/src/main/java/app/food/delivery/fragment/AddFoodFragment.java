package app.food.delivery.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gun0912.tedpicker.Config;
import com.gun0912.tedpicker.ImagePickerActivity;

import java.io.InputStream;
import java.util.ArrayList;

import app.food.delivery.R;

public class AddFoodFragment extends Fragment {
    View mView;
    EditText food_Name, food_Desc, food_Price;
    ImageView food_Image_One, food_Image_Two, food_Image_Three;
    LinearLayout Food_Image_Layout;
    Button btn_Add_Image;
    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    private static final String TAG = "AddFoodFragment";

    private ArrayList<Uri> image_uris;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_add_food, container, false);
        getActivity().setTitle("Add Food");
        initialization();
        return mView;

    }


    private void initialization() {
        food_Name = mView.findViewById(R.id.food_name);
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
    }


    private void getImages() {

        Config config = new Config();
        config.setSelectionMin(3);
        config.setSelectionLimit(3);
        ImagePickerActivity.setConfig(config);
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);

        Food_Image_Layout.setVisibility(View.VISIBLE);

    }

    @Override
    public void onActivityResult(int requestCode, int resuleCode, Intent intent) {
        super.onActivityResult(requestCode, resuleCode, intent);

        if (requestCode == INTENT_REQUEST_GET_IMAGES && resuleCode == Activity.RESULT_OK) {

            image_uris = intent.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
            Log.e(TAG, "onActivityResult: " + image_uris);

            for (int i = 0; i < image_uris.size(); i++) {
                if (i == 0) {
                    try {

                        Uri selectedImage = intent.getData();
                        String[] filePathColumn = { MediaStore.Images.Media.DATA };

                        Cursor cursor =getActivity(). getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();


                        food_Image_One.setImageBitmap(BitmapFactory.decodeFile(picturePath));

                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(image_uris.get(i));
                      /*  final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        food_Image_One.setImageBitmap(selectedImage);*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (i == 1) {
                    try {

                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(image_uris.get(i));
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        food_Image_Two.setImageBitmap(selectedImage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {

                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(image_uris.get(i));
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        food_Image_Three.setImageBitmap(selectedImage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
