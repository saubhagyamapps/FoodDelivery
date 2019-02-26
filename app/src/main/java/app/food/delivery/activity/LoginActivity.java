package app.food.delivery.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.Login;
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

import app.food.delivery.R;
import app.food.delivery.sessionmanager.SessionManager;
import app.food.delivery.util.Constant;

public class LoginActivity extends AppCompatActivity {

    EditText edt_Email, edt_Password;
    Button btn_Login;
    TextView txt_ForgotPwd;
    private static final String TAG = "LoginActivity";
    SignInButton SignIn_Google;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 007;
    String personName, email;
    SessionManager sessionManager;
    int Flag;
    LoginButton facebook_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);
        //checkLoginSession();
        initialization();
        signUp();
    }

    private void initialization() {
        edt_Email = findViewById(R.id.etEmail);
        edt_Password = findViewById(R.id.etPassword);
        btn_Login = findViewById(R.id.btn_login);
        txt_ForgotPwd = findViewById(R.id.txt_forgot);
        facebook_Login = findViewById(R.id.login_button);
        SignIn_Google = findViewById(R.id.signup_google);
        sessionManager = new SessionManager(getApplicationContext());

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

   /* private void checkLoginSession() {
        if (sessionManager.checkLogin()) {
            Flag = 1;
        } else {
            Flag = 0;

        }
    }*/



    private void signUp() {
        findViewById(R.id.txtSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


            /*Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            intent.putExtra("name", personName);
            intent.putExtra("email", email);
            intent.putExtra("images", personPhotoUrl);
            startActivity(intent);*/
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

    }
}
