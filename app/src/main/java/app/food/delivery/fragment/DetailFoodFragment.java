package app.food.delivery.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.food.delivery.R;
public class DetailFoodFragment extends Fragment {
    View mView;
    ViewPager viewPager;
    TextView txt_FoodName, txt_Food_Desc, txt_Food_Price;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_detail_food, container, false);
        getActivity().setTitle("Detail Foods");
        initialization();
        return mView;
    }


    private void initialization()
    {
        viewPager = mView.findViewById(R.id.pager);
        txt_FoodName = mView.findViewById(R.id.txt_food_name);
        txt_Food_Desc = mView.findViewById(R.id.txt_food_desc);

    }

}
