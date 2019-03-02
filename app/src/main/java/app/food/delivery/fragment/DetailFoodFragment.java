package app.food.delivery.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import app.food.delivery.R;
import app.food.delivery.adapter.SlidingImage_Adapter;
import app.food.delivery.model.AddToCartModel;
import app.food.delivery.model.DetailFoodModel;
import app.food.delivery.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFoodFragment extends Fragment {
    private static final String TAG = "DetailFoodFragment";
    View mView;
    ViewPager viewPager;
    TextView txt_FoodName, txt_Food_Desc, txt_Food_Price;
    String mFoodId;
    CirclePageIndicator indicator;
    private int currentPage = 0;
    private int NUM_PAGES = 0;
    ElegantNumberButton btnPluseMin;
    String mPluseMinValue = "1";
    Button btnAddtoCart;
    int peice;
    int total;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_detail_food, container, false);
        getActivity().setTitle("Detail Foods");
        initialization();
        Bundle args = getArguments();
        mFoodId = args.getString("food_id");
        getFoodDetailData();
        return mView;
    }


    private void initialization() {
        Constant.setSession(getActivity());
        viewPager = mView.findViewById(R.id.pager);
        txt_FoodName = mView.findViewById(R.id.txt_food_name);
        txt_Food_Desc = mView.findViewById(R.id.txt_food_desc);
        txt_Food_Price = mView.findViewById(R.id.txt_food_price);
        btnPluseMin = mView.findViewById(R.id.btnPluseMin);
        btnAddtoCart = mView.findViewById(R.id.btnAddtoCart);
        getPulseMinValu();
        addToCartClick();
    }

    private void addToCartClick() {
        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddtoCartAPICALL();
            }
        });
    }

    private void AddtoCartAPICALL() {
        Call<AddToCartModel> add = Constant.apiService.addtoCartItem(Constant.mUserId, mFoodId, mPluseMinValue, total);
        add.enqueue(new Callback<AddToCartModel>() {
            @Override
            public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                if (response.body().getStatus().equals("0")) {
                    Constant.toast(response.body().getMessage(), getActivity());
                    getFragmentManager().beginTransaction().add(R.id.content_frame, new CartViewFragment()).addToBackStack("fragment").commit();

                }
            }

            @Override
            public void onFailure(Call<AddToCartModel> call, Throwable t) {

            }
        });
    }

    private void getPulseMinValu() {
        btnPluseMin.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPluseMinValue = btnPluseMin.getNumber();
                total = peice * Integer.parseInt(mPluseMinValue);
                txt_Food_Price.setText("₹ " + String.valueOf(total));
                Log.e(TAG, "item Count: " + mPluseMinValue);
            }
        });
    }

    private void getFoodDetailData() {
        Constant.progressDialog(getActivity());
        Call<DetailFoodModel> detailFoodFragmentCall = Constant.apiService.getDetailFood(mFoodId);
        detailFoodFragmentCall.enqueue(new Callback<DetailFoodModel>() {
            @Override
            public void onResponse(Call<DetailFoodModel> call, Response<DetailFoodModel> response) {

                List<DetailFoodModel.ResultBean> resultBeanList = response.body().getResult();

                if (resultBeanList.size() != 0) {
                    List<DetailFoodModel.ResultBean> resultBeans = response.body().getResult();
                    String abc = response.body().getResult().get(0).getImages();

                    String[] array = abc.split(",", 3);

                    List<String> imageList = new ArrayList<>();
                    Collections.addAll(imageList, array);


                    Log.e(TAG, "onResponse: " + imageList.size());
                    Log.e(TAG, "Array: " + array);
                    viewPager.setAdapter(new SlidingImage_Adapter(getActivity(), (ArrayList) imageList));

                    indicator = (CirclePageIndicator) mView.findViewById(R.id.indicator);

                    indicator.setViewPager(viewPager);
                    txt_FoodName.setText(resultBeans.get(0).getFood_name());
                    txt_Food_Desc.setText(resultBeans.get(0).getDescription());
                    txt_Food_Price.setText("₹ " + resultBeans.get(0).getPrice());
                    total = Integer.parseInt(resultBeans.get(0).getPrice());
                    peice = Integer.parseInt(resultBeans.get(0).getPrice());
                    handlerCall(imageList.size());
                    Constant.progressBar.dismiss();
                }
            }


            @Override

            public void onFailure(Call<DetailFoodModel> call, Throwable t) {
                Constant.progressBar.dismiss();

            }
        });
    }

    private void handlerCall(int size) {
        NUM_PAGES = size;

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2000, 2000);

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }
}