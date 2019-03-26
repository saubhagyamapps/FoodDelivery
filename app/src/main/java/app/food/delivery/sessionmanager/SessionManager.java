package app.food.delivery.sessionmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import app.food.delivery.activity.LoginActivity;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "fooddeliveryPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_MOBILE = "mobile_number";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_DEVICE_ID = "device_id";
    public static final String KEY_FIREBASE_ID = "firebse_id";
    public static final String KEY_ADDRESS = "Address";
    public static final String KEY_IMAGES = "images";
    public static final String KEY_GENDER = "gender";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String id,String name, String mEmail,String mobile,
                                   String mPassword, String device_id, String firebase_id,String address,String image,String gender) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, mEmail);
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_PASSWORD, mPassword);
        editor.putString(KEY_DEVICE_ID, device_id);
        editor.putString(KEY_FIREBASE_ID, firebase_id);
        editor.putString(KEY_ADDRESS, address);
        editor.putString(KEY_IMAGES, image);
        editor.putString(KEY_GENDER, gender);
        editor.apply();
    }

    public boolean checkLogin() {
        if (!this.isLoggedIn()) {
            return true;
        } else {
            return false;
        }
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_MOBILE, pref.getString(KEY_MOBILE, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_DEVICE_ID, pref.getString(KEY_DEVICE_ID, null));
        user.put(KEY_FIREBASE_ID, pref.getString(KEY_FIREBASE_ID, null));
        user.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS, null));
        user.put(KEY_IMAGES, pref.getString(KEY_IMAGES, null));
        user.put(KEY_GENDER, pref.getString(KEY_GENDER, null));

        return user;
    }
    public void logoutUser() {
        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        _context.startActivity(i);
    }
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
