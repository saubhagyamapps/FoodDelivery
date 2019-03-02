package app.food.delivery.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.List;

import app.food.delivery.R;
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

    public CartViewAdepter(List<CartViewModel.ResultBean> result, Context context, String path) {
        this.context = context;
        this.result = result;
        this.path = path;
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

        holder.txtFoodName.setText(result.get(position).getFood_name());
        holder.txtFoodCategory.setText(result.get(position).getCategory_name());
        holder.txtFoodPrice1.setText(result.get(position).getPrice());
        holder.txtFoodPrice1.setText(result.get(position).getPrice());
        holder.btnPluseMin.setNumber(result.get(position).getQuantity());
        String mainChapterNumber = result.get(position).getImages().split(",", 2)[0];
        Log.e(TAG, "onBindViewHolder: " + path + mainChapterNumber);
        Glide.with(context).load(path + "" + mainChapterNumber)
                .thumbnail(0.5f)
                .crossFade()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.cardViewImage);
        btnClick(holder.btnDelet, position, result.get(position).getId(),holder.cardViewFood);
    }

    private void btnClick(ImageView btnDelet, final int position, final String id, final CardView cardViewFood) {
        btnDelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCartAPICALL(id,position,cardViewFood);
            }
        });
    }

    private void removeCartAPICALL(String id, int position, final CardView cardViewFood) {
        Call<RemoveCartModel> cartModelCall = Constant.apiService.removeCart(Constant.mUserId, id);
        cartModelCall.enqueue(new Callback<RemoveCartModel>() {
            @Override
            public void onResponse(Call<RemoveCartModel> call, Response<RemoveCartModel> response) {
                Constant.toast(response.body().getMessage(), context);
                cardViewFood.setVisibility(View.GONE);
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
}