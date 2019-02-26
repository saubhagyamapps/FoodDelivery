package app.food.delivery.activity;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.iid.FirebaseInstanceId;

import app.food.delivery.R;
import app.food.delivery.model.SigninModel;
import app.food.delivery.retrofit.ApiClient;
import app.food.delivery.retrofit.ApiInterface;
import app.food.delivery.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etUserName, etPassword;
    Button btnLogin;


    String mUserName, mPassword, mDeviceId, firebase_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);
        initialization();
        signUp();
    }

    private void initialization() {

        mDeviceId = Settings.System.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);


        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnlogin);
        btnsubmit();
    }

    private void btnsubmit() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
                mUserName = etUserName.getText().toString().trim();
                mPassword = etPassword.getText().toString().trim();


            }
        });
    }

    private void validation() {
        if(mUserName.equals("")){
            etUserName.setError("Required");
        }else if(mPassword.equals("")){
            etPassword.setError("Required");
        }else {
            getLoginCall();
        }
    }

    private void getLoginCall() {
        Call<SigninModel> modelCall = Constant.apiService.getLoginData(mUserName, mPassword, mDeviceId, FirebaseInstanceId.getInstance().getToken());
        modelCall.enqueue(new Callback<SigninModel>() {
            @Override
            public void onResponse(Call<SigninModel> call, Response<SigninModel> response) {
                String result = response.body().getStatus();
                Log.e("LoginActivity", result);

                if (response.body().getStatus().equals(0)) {
                    Toast.makeText(LoginActivity.this, "login success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SigninModel> call, Throwable t) {

            }
        });
    }

    private void signUp() {
        findViewById(R.id.txtSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.intent(LoginActivity.this, RegistrationActivity.class);
            }
        });
    }
}
