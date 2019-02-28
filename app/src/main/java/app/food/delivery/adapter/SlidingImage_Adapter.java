package app.food.delivery.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import app.food.delivery.R;


public class SlidingImage_Adapter extends PagerAdapter {

    private static final String TAG = "SlidingImage_Adapter";
    private ArrayList detailfoodlist;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImage_Adapter(Context context, ArrayList detailfoodlist) {
        this.detailfoodlist = detailfoodlist;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return detailfoodlist.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);
        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.sliding_image);

        Log.e(TAG, "instantiateItem: " + detailfoodlist.toString());
        Glide.with(context).load("http://192.168.1.200/food_deliveryapp/public/food_images/" + detailfoodlist.get(position))
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(R.drawable.image_loader)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}