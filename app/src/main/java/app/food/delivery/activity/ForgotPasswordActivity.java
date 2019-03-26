package app.food.delivery.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.food.delivery.R;
import app.food.delivery.model.ForgotPasswordModel;
import app.food.delivery.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText forgot_Email;
    Button btn_sendLink;
    String mEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initialization();
        Clicked();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //overridePendingTransition(R.anim.swipe_left_enter, R.anim.swipe_left_exit);

    }

    private void Clicked()
    {
        btn_sendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }

    private void initialization()
    {
        forgot_Email = findViewById(R.id.forgotpwd_email);
        btn_sendLink = findViewById(R.id.send_email);
    }

    private void validation()
    {

        mEmail = forgot_Email.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (mEmail.matches(emailPattern) && mEmail.length() > 0)
        {
                getForgotPassword();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Invalid email address",Toast.LENGTH_SHORT).show();

        }
    }

    private void getForgotPassword()
    {

        Constant.progressDialog(ForgotPasswordActivity.this);
        Call<ForgotPasswordModel> forgotPasswordModelCall = Constant.apiService.getPassword(mEmail);

        forgotPasswordModelCall.enqueue(new Callback<ForgotPasswordModel>() {
            @Override
            public void onResponse(Call<ForgotPasswordModel> call, Response<ForgotPasswordModel> response) {

                if (response.body().getStatus().equals("0"))
                {
                    Toast.makeText(ForgotPasswordActivity.this,response.body().getMessgae(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ForgotPasswordActivity.this, response.body().getMessgae(), Toast.LENGTH_SHORT).show();
                }
                Constant.progressBar.dismiss();
            }

            @Override
            public void onFailure(Call<ForgotPasswordModel> call, Throwable t) {

                Constant.progressBar.dismiss();
                Toast.makeText(ForgotPasswordActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
