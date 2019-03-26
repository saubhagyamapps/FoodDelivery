package app.food.delivery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import app.food.delivery.R;
import app.food.delivery.model.SearchModel;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<SearchModel> searchModelList;
    private List<SearchModel> searchModelListFiltered;
    private ContactsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, price,txtCategory;
        public ImageView thumbnail;
        public RelativeLayout searchViewSelect;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            price = view.findViewById(R.id.price);
            thumbnail = view.findViewById(R.id.thumbnail);
            searchViewSelect = view.findViewById(R.id.searchViewSelect);
            txtCategory = view.findViewById(R.id.txtCategory);
/*
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onContactSelected(searchModelListFiltered.get(getAdapterPosition()));
                }
            });*/
        }
    }


    public SearchAdapter(Context context, List<SearchModel> searchModelList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.searchModelList = searchModelList;
        this.searchModelListFiltered = searchModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final SearchModel searchModel = searchModelListFiltered.get(position);
        holder.name.setText(searchModel.getFood_name());
        holder.price.setText("₹ " + searchModel.getPrice());
        holder.txtCategory.setText(searchModel.getCategory_name());
        holder.searchViewSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onContactSelected(searchModelListFiltered.get(position));

            }
        });
        String mainChapterNumber = searchModel.getImages().split(",", 2)[0];


        Glide.with(context).load("http://192.168.1.200/food_deliveryapp/public/food_images/" + mainChapterNumber)
                .thumbnail(0.5f)
                .crossFade()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return searchModelListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    searchModelListFiltered = searchModelList;
                } else {
                    List<SearchModel> filteredList = new ArrayList<>();
                    for (SearchModel row : searchModelList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or price number match
                        if (row.getFood_name().toLowerCase().contains(charString.toLowerCase()) || row.getCategory_name().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    searchModelListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = searchModelListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                searchModelListFiltered = (ArrayList<SearchModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(SearchModel searchModel);
    }
}
