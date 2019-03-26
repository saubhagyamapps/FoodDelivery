package app.food.delivery.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import app.food.delivery.R;
import app.food.delivery.adapter.FoodAdapter;
import app.food.delivery.adapter.RecivedOerderListAdapter;
import app.food.delivery.adapter.uti.PaginationScrollListenerGridlaout;
import app.food.delivery.interfacep.BookClick;
import app.food.delivery.model.FoodListModel;
import app.food.delivery.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecivedOrderListFragment extends Fragment {

    View mView;

    RecyclerView recyclerView;
    RecivedOerderListAdapter recivedOerderListAdapter;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private static final String TAG = "FoodFragment";
    private int TOTAL_PAGES;
    Fragment mContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_recived_order_list, container, false);
        initialization();
        return mView;
    }

    private void initialization() {
        recyclerView = mView.findViewById(R.id.recyclerViewFood);
        recivedOerderListAdapter = new RecivedOerderListAdapter(getActivity(), new BookClick() {
            @Override
            public void bookClick(String foodId, String price) {
                DetailFoodFragment detailFoodFragment = new DetailFoodFragment();
                Bundle args = new Bundle();
                args.putString("food_id", foodId);
                args.putString("price", price);
                detailFoodFragment.setArguments(args);
                getFragmentManager().beginTransaction().addToBackStack("fragment")
                        .replace(R.id.content_frame, detailFoodFragment, "FoodFragment").commit();

            }
        });
        APICALL();
    }

    private void APICALL() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(recivedOerderListAdapter);
        recyclerView.addOnScrollListener(new PaginationScrollListenerGridlaout((GridLayoutManager) gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (TOTAL_PAGES != 1) {
                            loadNextPage();
                        } else {
                            recivedOerderListAdapter.removeLoadingFooter();
                        }

                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        loadFirstPage();
    }

    private void loadFirstPage() {

        Constant.progressDialog(getActivity());
        Log.d(TAG, "loadFirstPage: ");
        Call<FoodListModel> foodListModelCall = Constant.apiService.getFoodList(currentPage);
        foodListModelCall.enqueue(new Callback<FoodListModel>() {
            @Override
            public void onResponse(Call<FoodListModel> call, Response<FoodListModel> response) {
                TOTAL_PAGES = response.body().getTotalPages();
                List<FoodListModel.ResultBean> result = response.body().getResult();
                recivedOerderListAdapter.addAll(result);

                if (currentPage <= TOTAL_PAGES) recivedOerderListAdapter.addLoadingFooter();
                else isLastPage = true;

                Constant.progressBar.dismiss();
            }

            @Override
            public void onFailure(Call<FoodListModel> call, Throwable t) {

                Constant.progressBar.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);
        Call<FoodListModel> foodListModelCall = Constant.apiService.getFoodList(currentPage);
        foodListModelCall.enqueue(new Callback<FoodListModel>() {
            @Override
            public void onResponse(Call<FoodListModel> call, Response<FoodListModel> response) {
                if (TOTAL_PAGES != 1) {
                    recivedOerderListAdapter.removeLoadingFooter();
                    isLoading = false;

                    List<FoodListModel.ResultBean> results = response.body().getResult();
                    recivedOerderListAdapter.addAll(results);

                    if (currentPage != TOTAL_PAGES) recivedOerderListAdapter.addLoadingFooter();
                    else isLastPage = true;
                } else {
                    recivedOerderListAdapter.removeLoadingFooter();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<FoodListModel> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }
}
