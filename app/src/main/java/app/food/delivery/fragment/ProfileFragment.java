package app.food.delivery.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import app.food.delivery.R;
import app.food.delivery.activity.NavigationActivity;
import app.food.delivery.model.ProfileModel;
import app.food.delivery.sessionmanager.SessionManager;
import app.food.delivery.util.Constant;
import belka.us.androidtoggleswitch.widgets.BaseToggleSwitch;
import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    View mView;
    private static final String TAG = "ProfileFragment";
    ImageView acc_profile_image, prof_camera_image;
    EditText edt_profusername, edt_profaddress, edt_profemail, edt_profmobileno;
    TextView txt_profGender;
    Button btn_update;
    String mGoogleimage = "null";
    String setImages;
    MultipartBody.Part body;
    ToggleSwitch toggleSwitch;
    String filePath = "null";
    Call<ProfileModel> modelCall;
    String mUserName, mGender = "Male", mAddress, mEmail, mMobileNo;
    private final static int IMAGE_RESULT = 200;
    Uri picUri;
    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile, container, false);
        Constant.setSession(getActivity());
        sessionManager = new SessionManager(getActivity());
        initialization();
        return mView;
    }

    private void initialization() {
        acc_profile_image = mView.findViewById(R.id.acc_profile_image);
        prof_camera_image = mView.findViewById(R.id.prof_camera_image);
        edt_profusername = mView.findViewById(R.id.edt_profusername);
        edt_profemail = mView.findViewById(R.id.edt_profemail);
        edt_profmobileno = mView.findViewById(R.id.edt_profmobileno);
        edt_profaddress = mView.findViewById(R.id.edt_profaddress);
        txt_profGender = mView.findViewById(R.id.txt_profGender);
        btn_update = mView.findViewById(R.id.btn_update);
        toggleSwitch = mView.findViewById(R.id.gender_switch);
        toggleSwitch.setOnToggleSwitchChangeListener(new BaseToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                switch (position) {
                    case 0:
                        mGender = "Male";
                        break;
                    case 1:
                        mGender = "Female";
                        break;
                }
            }
        });
        getCemaraImages();
        btnsubmit();
        setData();
    }

    private void setData() {
        edt_profaddress.setText(Constant.mAddress);
        edt_profusername.setText(Constant.mUserName);
        edt_profemail.setText(Constant.mUserEmail);
        edt_profmobileno.setText(Constant.mUserMobile);
        if (Constant.mGender != null && !Constant.mGender.isEmpty() && !Constant.mGender.equals("null")) {
            Glide.with(getActivity()).load(Constant.mImages)
                    .thumbnail(0.5f)
                    .crossFade()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(acc_profile_image);
            if (Constant.mGender.equalsIgnoreCase("Male")) {
                toggleSwitch.setCheckedTogglePosition(0);
            } else {
                toggleSwitch.setCheckedTogglePosition(1);
            }
        }


    }

    private void btnsubmit() {
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserName = edt_profusername.getText().toString().trim();
                mAddress = edt_profaddress.getText().toString().trim();
                mEmail = edt_profemail.getText().toString().trim();
                mMobileNo = edt_profmobileno.getText().toString().trim();
                Validation();
            }
        });
    }

    private void Validation() {
        if (Constant.mImages.equals("null")&&filePath.equals("null")) {
            Constant.toast("Please select profile picture", getActivity());
        } else if (mUserName.equals("")) {
            edt_profusername.setError("Required");

        } else if (mGender.equals("")) {
            txt_profGender.setError("Required");

        } else if (mAddress.equals("")) {
            edt_profaddress.setError("Required");

        } else {
            UpdateImage();
        }
    }

    private void getCemaraImages() {
        prof_camera_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
            }
        });
    }

    private Intent getPickImageChooserIntent() {
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getActivity().getPackageManager();

        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getActivity().getExternalFilesDir("");
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == Activity.RESULT_OK) {


            if (requestCode == IMAGE_RESULT) {

                filePath = getImageFilePath(data);
                if (filePath != null) {
                    Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
                    acc_profile_image.setImageBitmap(selectedImage);
                }
            }

        }

    }

    public String getImageFilePath(Intent data) {
        return getImageFromFilePath(data);
    }

    private String getImageFromFilePath(Intent data) {
        boolean isCamera = data == null || data.getData() == null;

        if (isCamera) return getCaptureImageOutputUri().getPath();
        else return getPathFromURI(data.getData());
    }

    private String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("pic_uri", picUri);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            picUri = savedInstanceState.getParcelable("pic_uri");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void UpdateImage() {
        Constant.progressDialog(getActivity());
        try {
            File file = new File(filePath);
            final RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody Id = RequestBody.create(MediaType.parse("multipart/form-data"), Constant.mUserId);
        RequestBody Username = RequestBody.create(MediaType.parse("multipart/form-data"), mUserName);
        RequestBody Gender = RequestBody.create(MediaType.parse("multipart/form-data"), mGender);
        RequestBody Address = RequestBody.create(MediaType.parse("multipart/form-data"), mAddress);

        if (filePath.equals("null")) {
            modelCall = Constant.apiService.getEditProfile(Constant.mUserId, mUserName, mGender, mAddress);
            Log.e(TAG, "Images Null");
        } else {
            modelCall = Constant.apiService.EditProfileWIthImage(Id, Username, Gender, Address, body);
            Log.e(TAG, "Images not Null");

        }

        modelCall.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {

                if (response.body().getStatus().equals("0")) {

                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    sessionManager.createLoginSession(Constant.mUserId, mUserName, mEmail, mMobileNo, Constant.mPassword, Constant.mDeviceId, Constant.mUserFirebaseID
                            , mAddress, response.body().getProfile_image(), mGender);
                    Constant.intent(getActivity(), NavigationActivity.class);
                } else {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                Constant.progressBar.dismiss();
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Constant.progressBar.dismiss();

            }
        });

    }
}
