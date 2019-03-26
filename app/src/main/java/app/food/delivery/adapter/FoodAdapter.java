package app.food.delivery.adapter;

import android.content.Context;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import app.food.delivery.R;
import app.food.delivery.interfacep.BookClick;
import app.food.delivery.model.FoodListModel;

public class FoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    View view;
    private static final String TAG = "FoodAdapter";
    private List<FoodListModel.ResultBean> foodlistdata;
    Context mContex;
    BookClick bookClick;

    public FoodAdapter(Context mContex, BookClick bookClick) {
        foodlistdata = new ArrayList<>();
        this.mContex = mContex;
        this.bookClick = bookClick;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.foodlist, parent, false);
        viewHolder = new MovieVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        switch (getItemViewType(i)) {
            case ITEM:
                final MovieVH movieVH = (MovieVH) viewHolder;
                movieVH.txt_Name.setText(foodlistdata.get(i).getFood_name());
                movieVH.txt_Desc.setText(foodlistdata.get(i).getCategory_name());
                movieVH.txt_price.setText("₹ " + foodlistdata.get(i).getPrice());
                Log.e(TAG, "onBindViewHolder: " + foodlistdata.get(i).getFood_name());
                String mainChapterNumber = foodlistdata.get(i).getImages().split(",", 2)[0];
                Log.e(TAG, "onBindViewHolder: " + mainChapterNumber);
                Glide.with(mContex).load("http://192.168.1.200/food_deliveryapp/public/food_images/" + mainChapterNumber)
                        .thumbnail(0.5f)
                        .crossFade()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(movieVH.imageView);

                movieVH.cardViewFood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bookClick.bookClick(String.valueOf(foodlistdata.get(i).getFood_id()),foodlistdata.get(i).getPrice());

                    }
                });
            case LOADING:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return foodlistdata == null ? 0 : foodlistdata.size();

    }

    @Override
    public int getItemViewType(int position) {
        return (position == foodlistdata.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    public void add(FoodListModel.ResultBean r) {
        foodlistdata.add(r);
        notifyItemInserted(foodlistdata.size() - 1);
    }

    public void addAll(List<FoodListModel.ResultBean> Results) {
        for (FoodListModel.ResultBean result : Results) {
            add(result);
        }
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new FoodListModel.ResultBean());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = foodlistdata.size() - 1;
        FoodListModel.ResultBean result = getItem(position);

        if (result != null) {
            foodlistdata.remove(position);
            notifyItemRemoved(position);
        }
    }

    public FoodListModel.ResultBean getItem(int position) {
        return foodlistdata.get(position);
    }

    public class MovieVH extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView txt_Name, txt_price, txt_Desc;
        TextView btn_AddFood;
        CardView cardViewFood;


        public MovieVH(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.cardViewImage);
            txt_Name = itemView.findViewById(R.id.txtFoodName);
            txt_price = itemView.findViewById(R.id.txtFoodPrice1);
            txt_Desc = itemView.findViewById(R.id.txtFoodType);
            btn_AddFood = itemView.findViewById(R.id.btnAddFood);
            cardViewFood = itemView.findViewById(R.id.cardViewFood);


        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}