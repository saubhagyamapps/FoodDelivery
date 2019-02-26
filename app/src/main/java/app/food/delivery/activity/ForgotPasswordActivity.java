package app.food.delivery.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import app.food.delivery.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText forgot_Email;
    Button btn_sendLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        init();
    }


    private void init()
    {
        forgot_Email = findViewById(R.id.forgotpwd_email);
        btn_sendLink = findViewById(R.id.send_email);
    }
}
