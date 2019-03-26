package app.food.delivery.activity;

import android.app.SearchManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import app.food.delivery.MyApplication;
import app.food.delivery.R;
import app.food.delivery.adapter.SearchAdapter;
import app.food.delivery.fragment.DetailFoodFragment;
import app.food.delivery.model.SearchModel;
import app.food.delivery.util.Constant;
import app.food.delivery.util.MyDividerItemDecoration;

public class SearchActivity extends AppCompatActivity implements SearchAdapter.ContactsAdapterListener {
    private static final String TAG = SearchActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<SearchModel> searchModelList;
    private SearchAdapter mAdapter;
    private SearchView searchView;

    // url to fetch contacts json
    private static final String URL = Constant.BASE_URL + "search_action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main_acivity);
        //overridePendingTransition(R.anim.swipe_left_enter, R.anim.swipe_left_exit);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search");
        recyclerView = findViewById(R.id.recycler_view);
        searchModelList = new ArrayList<>();
        mAdapter = new SearchAdapter(this, searchModelList, this);

        whiteNotificationBar(recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(mAdapter);

        fetchContacts();
    }

    private void fetchContacts() {
        JsonArrayRequest request = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            Toast.makeText(getApplicationContext(), "Couldn't fetch the contacts! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        List<SearchModel> items = new Gson().fromJson(response.toString(), new TypeToken<List<SearchModel>>() {
                        }.getType());

                        // adding contacts to contacts list
                        searchModelList.clear();
                        searchModelList.addAll(items);

                        // refreshing recycler view
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        MyApplication.getInstance().addToRequestQueue(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setIconified(false);
        Log.e(TAG, "onCreateOptionsMenu: Search menuuuuuuu");
        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }
        if (id == android.R.id.home) {
            //overridePendingTransition(R.anim.swipe_left_exit, R.anim.swipe_left_enter);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        //overridePendingTransition(R.anim.swipe_left_exit, R.anim.swipe_left_enter);
        Log.e(TAG, "onBackPressed: ");
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public void onContactSelected(SearchModel searchModel) {
        Log.e(TAG, "onContactSelected: " + searchModel.getId());



        Intent intent=new Intent(SearchActivity.this,NavigationActivity.class);
        intent.putExtra("Food_id",searchModel.getId());
        intent.putExtra("Food_price",searchModel.getPrice());
        startActivity(intent);

        Toast.makeText(getApplicationContext(), "Selected: " + searchModel.getFood_name() + ", " + searchModel.getCategory_name(), Toast.LENGTH_LONG).show();
    }
}
