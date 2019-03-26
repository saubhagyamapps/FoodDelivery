package app.food.delivery.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import app.food.delivery.R;
import app.food.delivery.retrofit.ApiClient;
import app.food.delivery.retrofit.ApiInterface;
import app.food.delivery.sessionmanager.SessionManager;

public class Constant {

    public static final String BASE_URL = "http://192.168.1.200/food_deliveryapp/";

    public static String mImagesPath;
    public static ProgressDialog progressBar;
    public static Button btnNext;
    public static TextView txtTitle;
    public static LinearLayout screenShortLayout;
    public static ImageView ssImagesView;
    public static SessionManager session;
    public static String mUserId;
    public static String mUserName;
    public static String mUserMobile;
    public static String mUserFirebaseID;
    public static String mUserEmail;
    public static String mAddress;
    public static String mImages;
    public static String mGender;
    public static String mPassword;
    public static String mDeviceId;

    public static ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);

    public static void progressDialog(Context applicationContext) {
        progressBar = new ProgressDialog(applicationContext);
        progressBar.setCancelable(false);
        progressBar.setTitle(applicationContext.getResources().getString(R.string.progress_dialog_tital));
        progressBar.setMessage(applicationContext.getResources().getString(R.string.progress_dialog_msg));
        progressBar.show();

    }

    public static void setSession(Context applicationContext) {
        session = new SessionManager(applicationContext);
        HashMap<String, String> user = session.getUserDetails();
        mUserId = user.get(SessionManager.KEY_ID);
        mUserName = user.get(SessionManager.KEY_NAME);
        mUserMobile = user.get(SessionManager.KEY_MOBILE);
        mUserFirebaseID = user.get(SessionManager.KEY_FIREBASE_ID);
        mUserEmail = user.get(SessionManager.KEY_EMAIL);
        mAddress = user.get(SessionManager.KEY_ADDRESS);
        mImages = user.get(SessionManager.KEY_IMAGES);
        mGender = user.get(SessionManager.KEY_GENDER);
        mPassword = user.get(SessionManager.KEY_PASSWORD);
        mDeviceId = user.get(SessionManager.KEY_DEVICE_ID);
    }

    public static void toast(String message, Context applicationContext) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show();
    }

    public static void intent(Context classOne, Class classTwo) {
        Intent intent = new Intent(classOne, classTwo);
        classOne.startActivity(intent);

    }

    public static void hideKeyboard(Activity activity, View viewToHide) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(viewToHide.getWindowToken(), 0);
    }

}
