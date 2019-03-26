package app.food.delivery.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import app.food.delivery.R;
import app.food.delivery.activity.NavigationActivity;
import app.food.delivery.adapter.CartViewAdepter;
import app.food.delivery.interfacep.DeleteRefresh;
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
    TextView txtTotalAmount;
    String mPrice, mTotalPrice;
    Button btnCheckout;
    FloatingActionButton addNewFood;
    private static final String TAG = "CartViewFragment";
    String mStringList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_cart_view, container, false);
        getActivity().setTitle("Cart");
        Bundle args = getArguments();
        mPrice = args.getString("price");
        mTotalPrice = args.getString("totalPrice");
        Constant.setSession(getActivity());
        initialization();
        return mView;
    }

    private void initialization() {
        addNewFood = mView.findViewById(R.id.addNewFood);
        btnCheckout = mView.findViewById(R.id.btnCheckout);
        txtTotalAmount = mView.findViewById(R.id.txtTotalAmount);
        recyclerViewCart = mView.findViewById(R.id.recyclerViewCart);
        txtItemCount = mView.findViewById(R.id.txtItemCount);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewCart.setLayoutManager(mLayoutManager);
        txtTotalAmount.setText(mTotalPrice);
        APICALL();
        floatingActionButtonClick();
    }

    private void floatingActionButtonClick() {
        addNewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.intent(getActivity(), NavigationActivity.class);
                getActivity().finish();
            }
        });
    }

    public void APICALL() {
        Call<CartViewModel> viewModelCall = Constant.apiService.cartViewData(Constant.mUserId);
        viewModelCall.enqueue(new Callback<CartViewModel>() {
            @Override
            public void onResponse(Call<CartViewModel> call, final Response<CartViewModel> response) {

                for (int i = 0; i < response.body().getResult().size(); i++) {
                    mStringList = mStringList + "," + response.body().getResult().get(i).getId();
                }
                Log.e(TAG, "String List: "+mStringList.replace("null,",""));
                Log.e("Size of getdata", response.body().getResult().size() + "");
                txtItemCount.setText(response.body().getResult().size() + " ITEM");


                payment(response.body().getResult().size());
                if (response.body().getStatus().equals("1")) {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, new FoodFragment()).commit();
                }
                mAdapter = new CartViewAdepter(response.body().getResult(), getActivity(), response.body().getPath(), txtTotalAmount, mPrice, new DeleteRefresh() {
                    @Override
                    public void deleteClick(int position) {
                        getFragmentManager().beginTransaction().detach(CartViewFragment.this).attach(CartViewFragment.this).commit();
                        //   mAdapter.notifyDataSetChanged();
                        //  recyclerViewCart.removeViewAt(position);
                        // mAdapter.notifyItemRemoved(position);
                     /*   response.body().getResult().remove(position);
                        recyclerViewCart.removeViewAt(position);
                        mAdapter.notifyItemRemoved(position);
                        mAdapter.notifyItemRangeChanged(position, response.body().getResult().size());
*/
                    }

                });
                recyclerViewCart.setHasFixedSize(true);
                mAdapter.notifyDataSetChanged();
                recyclerViewCart.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<CartViewModel> call, Throwable t) {

            }
        });
    }

    private void payment(int size) {
        if (size > 0) {
            btnCheckout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PaymentFragment paymentFragment = new PaymentFragment();
                    Bundle args = new Bundle();
                    args.putString("Food_ids", mStringList.replace("null,",""));
                    paymentFragment.setArguments(args);
                    getFragmentManager().beginTransaction().addToBackStack(null).hide(CartViewFragment.this)
                            .add(R.id.content_frame, paymentFragment).commit();
                }
            });
        }
    }
}
