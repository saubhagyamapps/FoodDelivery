package app.food.delivery.activity;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.iid.FirebaseInstanceId;

import app.food.delivery.R;
import app.food.delivery.model.RegisterModel;
import app.food.delivery.retrofit.ApiClient;
import app.food.delivery.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    EditText reg_Username, reg_Email, reg_Mobile, reg_Password;
    Button btn_Register;
    LoginButton Signup_facebook;
    SignInButton SignInButton;
    String mDeviceId, mFirebaseId, mUserName, mEmail, mMobile, mPassword;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initialization();
        Clicked();
    }

    private void Clicked() {
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValue();

            }
        });
    }

    private void initialization() {
        mDeviceId = Settings.Secure.getString(RegistrationActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        mFirebaseId = FirebaseInstanceId.getInstance().getToken();
        reg_Username = findViewById(R.id.input_username);
        reg_Email = findViewById(R.id.input_email);
        reg_Mobile = findViewById(R.id.input_mobile);
        reg_Password = findViewById(R.id.input_password);
        btn_Register = findViewById(R.id.btn_register);

    }

    private void getValue() {
        mUserName = reg_Username.getText().toString().trim();
        mEmail = reg_Email.getText().toString().trim();
        mMobile = reg_Mobile.getText().toString().trim();
        mPassword = reg_Password.getText().toString().trim();
        Validation();

    }

    private void Validation() {
        if (mUserName.equalsIgnoreCase("")) {
            reg_Username.setError("Required");
            reg_Username.setFocusable(true);
        } else if (mEmail.equalsIgnoreCase("")) {
            reg_Email.setError("Required");
            reg_Email.setFocusable(true);
        } else if (mMobile.equalsIgnoreCase("")) {
            reg_Mobile.setError("Required");
            reg_Mobile.setFocusable(true);
        } else if (mPassword.equalsIgnoreCase("")) {
            reg_Password.setError("Required");
            reg_Password.setFocusable(true);
        } else {
            Registration();
        }
    }

    private void Registration() {
        Call<RegisterModel> registerModelCall = apiInterface.getRegister(mUserName, mEmail, mMobile, mPassword, mFirebaseId, mDeviceId);

        registerModelCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {

                if (response.body().getStatus().equals("0")) {
                    Toast.makeText(RegistrationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegistrationActivity.this, "Signup failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {

                Log.e("RegisterActivity", "Failed");
            }
        });
    }

}


