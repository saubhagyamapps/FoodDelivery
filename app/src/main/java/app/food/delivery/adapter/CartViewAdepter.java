package app.food.delivery.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.List;

import app.food.delivery.R;
import app.food.delivery.interfacep.DeleteRefresh;
import app.food.delivery.model.AddToCartModel;
import app.food.delivery.model.CartViewModel;
import app.food.delivery.model.RemoveCartModel;
import app.food.delivery.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartViewAdepter extends RecyclerView.Adapter<CartViewAdepter.ViewHolder> {
    private Context context;
    private int count = 1;
    List<CartViewModel.ResultBean> result;
    String path;
    private static final String TAG = "CartViewAdepter";
    DeleteRefresh deleteRefresh;
    String mPluseMinValue;
    int total;
    double totalPrice;
    TextView txtTotalAmount;
    String mPrice;

    public CartViewAdepter(List<CartViewModel.ResultBean> result, Context context,
                           String path, TextView txtTotalAmount, String mPrice,
                           DeleteRefresh deleteRefresh) {
        this.context = context;
        this.result = result;
        this.path = path;
        this.deleteRefresh = deleteRefresh;
        this.txtTotalAmount = txtTotalAmount;
        this.mPrice = mPrice;
        Constant.setSession(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_view_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mPluseMinValue = result.get(position).getTotal_price();
        holder.txtFoodName.setText(result.get(position).getFood_name());
        holder.txtFoodCategory.setText(result.get(position).getCategory_name());
        //double price=Double.parseDouble(result.get(position).getTotal_price())*Double.parseDouble(result.get(position).getQuantity());
        holder.txtFoodPrice1.setText("₹ " + result.get(position).getTotal_price());
        holder.btnPluseMin.setNumber(result.get(position).getQuantity());
        total=Integer.parseInt(result.get(position).getTotal_price());
        String mainChapterNumber = result.get(position).getImages().split(",", 2)[0];
        Log.e(TAG, "onBindViewHolder: " + path + mainChapterNumber);
        Glide.with(context).load(path + "" + mainChapterNumber)
                .thumbnail(0.5f)
                .crossFade()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.cardViewImage);
        btnClick(holder.btnDelet, position, result.get(position).getId(), holder.cardViewFood);
        getPulseMinValu(holder.btnPluseMin, holder.txtFoodPrice1, Integer.parseInt(result.get(position).getPrice()), result.get(position).getId(),position);
      /*  totalPrice = totalPrice + Double.parseDouble(result.get(position).getTotal_price());
        txtTotalAmount.setText(String.valueOf(totalPrice));*/
    }

    private void btnClick(ImageView btnDelet, final int position, final String id, final CardView cardViewFood) {
        btnDelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCartAPICALL(id, position, cardViewFood);
            }
        });
    }

    private void removeCartAPICALL(String id, final int position, final CardView cardViewFood) {
        Call<RemoveCartModel> cartModelCall = Constant.apiService.removeCart(Constant.mUserId, id);
        cartModelCall.enqueue(new Callback<RemoveCartModel>() {
            @Override
            public void onResponse(Call<RemoveCartModel> call, Response<RemoveCartModel> response) {
                Constant.toast(response.body().getMessage(), context);
                //  cardViewFood.setVisibility(View.GONE);
                deleteRefresh.deleteClick(position);
            }

            @Override
            public void onFailure(Call<RemoveCartModel> call, Throwable t) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtFoodName, txtFoodCategory, txtFoodPrice1;
        ElegantNumberButton btnPluseMin;
        public Button mAdd, mView;
        ImageView cardViewImage;
        ImageView btnDelet;
        CardView cardViewFood;

        public ViewHolder(View itemView) {
            super(itemView);
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            btnDelet = itemView.findViewById(R.id.btnDelet);
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            txtFoodCategory = itemView.findViewById(R.id.txtFoodCategory);
            txtFoodPrice1 = itemView.findViewById(R.id.txtFoodPrice1);
            btnPluseMin = itemView.findViewById(R.id.btnPluseMin);
            cardViewImage = itemView.findViewById(R.id.cardViewImage);
            cardViewFood = itemView.findViewById(R.id.cardViewFood);

        }
    }

    private void getPulseMinValu(final ElegantNumberButton btnPluseMin, final TextView txtFoodPrice1, final int price, final String id, int position) {
        btnPluseMin.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPluseMinValue = btnPluseMin.getNumber();
                total = price * Integer.parseInt(mPluseMinValue);
                txtFoodPrice1.setText("₹ " + String.valueOf(total));
                Log.e(TAG, "item Count: " + mPluseMinValue);
                Log.e(TAG, "price: " + price);
              //  txtTotalAmount.setText(String.valueOf(totalPrice+price));
                AddtoCartAPICALL(id);

            }
        });
        btnPluseMin.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Log.e(TAG, String.format("oldValue: %d   newValue: %d", oldValue, newValue));
                if(newValue>oldValue){
                    total = price * Integer.parseInt(mPluseMinValue);
                //    txtTotalAmount.setText(String.valueOf(totalPrice+price));
                }else {

                }
            }
        });
    }

    private void AddtoCartAPICALL(String id) {
        Constant.progressDialog(context);
        Call<AddToCartModel> add = Constant.apiService.addtoCartItem(Constant.mUserId, id, mPluseMinValue, total);
        add.enqueue(new Callback<AddToCartModel>() {
            @Override
            public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                Animation animation;
                animation = AnimationUtils.loadAnimation(context,
                        R.anim.in_out_enter);
                txtTotalAmount.startAnimation(animation);
                txtTotalAmount.setText(response.body().getTotal()+".0");
                Constant.progressBar.dismiss();

            }

            @Override
            public void onFailure(Call<AddToCartModel> call, Throwable t) {
                Constant.progressBar.dismiss();
            }
        });
    }
}