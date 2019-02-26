package app.food.delivery.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.FacebookButtonBase;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;

import app.food.delivery.R;

public class RegistrationActivity extends AppCompatActivity {

    LoginButton Signup_facebook;
    SignInButton SignInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

}
