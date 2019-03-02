package app.food.delivery.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import app.food.delivery.R;
import app.food.delivery.adapter.CartViewAdepter;
import app.food.delivery.model.CartViewModel;
import app.food.delivery.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartViewFragment extends Fragment {
    RecyclerView recyclerViewCart;
    View mView;
    TextView txtItemCount;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_cart_view, container, false);
        initialization();
        getActivity().setTitle("Cart");
        Constant.setSession(getActivity());
        return mView;
    }

    private void initialization() {

        recyclerViewCart = mView.findViewById(R.id.recyclerViewCart);
        txtItemCount = mView.findViewById(R.id.txtItemCount);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewCart.setLayoutManager(mLayoutManager);
        APICALL();
    }

    private void APICALL() {
        Call<CartViewModel> viewModelCall = Constant.apiService.cartViewData(Constant.mUserId);
        viewModelCall.enqueue(new Callback<CartViewModel>() {
            @Override
            public void onResponse(Call<CartViewModel> call, Response<CartViewModel> response) {
                Log.e("Size of getdata", response.body().getResult().size() + "");
                txtItemCount.setText(response.body().getResult().size()+" ITEM");
                mAdapter = new CartViewAdepter(response.body().getResult(), getActivity(),response.body().getPath());
                recyclerViewCart.setHasFixedSize(true);
                mAdapter.notifyDataSetChanged();
                recyclerViewCart.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<CartViewModel> call, Throwable t) {

            }
        });
    }

}
