package app.food.delivery.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.Login;

import app.food.delivery.R;
import app.food.delivery.util.Constant;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);
        signUp();
    }

    private void signUp() {
        findViewById(R.id.txtSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.intent(LoginActivity.this,RegistrationActivity.class);
            }
        });
    }
}
