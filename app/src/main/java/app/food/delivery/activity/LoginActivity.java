package app.food.delivery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.iid.FirebaseInstanceId;

import app.food.delivery.R;
import app.food.delivery.model.SigninModel;
import app.food.delivery.sessionmanager.SessionManager;
import app.food.delivery.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etUserName, etPassword;
    Button btn_Login;
    TextView txt_ForgotPwd;
    private static final String TAG = "LoginActivity";
    SignInButton SignIn_Google;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 007;
    String personName, email;
    SessionManager sessionManager;
    String mImages;
    int Flag;
    LoginButton facebook_Login;
    String mEmail, mPassword, mDeviceId, firebase_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);

        initialization();
        signUp();
    }

    @Override
    protected void onResume() {
        ////overridePendingTransition(R.anim.swipe_left_enter, R.anim.swipe_left_exit);
        super.onResume();
    }

    private void initialization() {
        mDeviceId = Settings.System.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        firebase_id = FirebaseInstanceId.getInstance().getToken();
        etUserName = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btn_Login = findViewById(R.id.btn_login);
        txt_ForgotPwd = findViewById(R.id.txt_forgot);
        facebook_Login = findViewById(R.id.login_button);
        SignIn_Google = findViewById(R.id.signup_google);
        sessionManager = new SessionManager(getApplicationContext());
        btnsubmit();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }


    private void btnsubmit() {
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mEmail = etUserName.getText().toString().trim();
                mPassword = etPassword.getText().toString().trim();
                validation();

            }
        });
    }

    private void validation() {
        if (mEmail.equals("")) {
            etUserName.setError("Required");
        } else if (mPassword.equals("")) {
            etPassword.setError("Required");
        } else {
            getLoginCall();
        }

    }

    private void getLoginCall() {
        Constant.progressDialog(LoginActivity.this);
        Call<SigninModel> modelCall = Constant.apiService.getLoginData(mEmail, mPassword, firebase_id, mDeviceId);
        modelCall.enqueue(new Callback<SigninModel>() {
            @Override
            public void onResponse(Call<SigninModel> call, Response<SigninModel> response) {


                Constant.progressBar.dismiss();
                if (response.body().getStatus().equals("0")) {
                    SigninModel.ResultBean mResponse = response.body().getResult().get(0);
                    if (mResponse.getImage() != null && !mResponse.getImage().isEmpty() && !mResponse.getImage().equals("null")) {
                        mImages = response.body().getPath() + "" + mResponse.getImage();
                    } else {
                        mImages = "null";
                    }

                    sessionManager.createLoginSession(mResponse.getId(), mResponse.getUsername(), mEmail, mResponse.getMobile(),
                            mPassword, firebase_id, mDeviceId, mResponse.getAddress(), mImages, mResponse.getGender());
                    //overridePendingTransition(R.anim.swipe_left_exit, R.anim.swipe_left_enter);

                    Constant.intent(LoginActivity.this, NavigationActivity.class);
                    finish();
                    Toast.makeText(LoginActivity.this, "login success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "" + response.body().getMessgae(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<SigninModel> call, Throwable t) {
                Constant.progressBar.dismiss();

            }
        });
    }

    private void signUp() {
        findViewById(R.id.txtSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //overridePendingTransition(R.anim.swipe_left_exit, R.anim.swipe_left_enter);
                Constant.intent(LoginActivity.this, RegistrationActivity.class);
            }
        });

        SignIn_Google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signOut();
            }
        });

        txt_ForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //overridePendingTransition(R.anim.swipe_left_exit, R.anim.swipe_left_enter);
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                    }
                });
        signIn();

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());


            try {
                personName = acct.getDisplayName();
                email = acct.getEmail();
                Log.e(TAG, "Name: " + personName + ", email: " + email);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Constant.progressBar.dismiss();
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            intent.putExtra("name", personName);
            intent.putExtra("email", email);
            //  intent.putExtra("images", personPhotoUrl);
            startActivity(intent);
        }else {
            Constant.progressBar.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Constant.progressDialog(LoginActivity.this);
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

    }
}
