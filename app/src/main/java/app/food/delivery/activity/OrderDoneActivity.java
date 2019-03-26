package app.food.delivery.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import app.food.delivery.R;
import app.food.delivery.util.Constant;


public class OrderDoneActivity extends AppCompatActivity {
    Button btnBacktoHome;
    private static final String TAG = "OrderDoneActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order_done);
        initialization();

    }


    private void initialization() {

        btnBacktoHome = findViewById(R.id.btnBacktoHome);

        backButtonClick();
    }

    private void backButtonClick() {
        btnBacktoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.intent(OrderDoneActivity.this, NavigationActivity.class);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e(TAG, "onBackPressed: ");
        Constant.intent(OrderDoneActivity.this, NavigationActivity.class);
        finish();
    }
}
