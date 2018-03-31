package com.waterme.plantism;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.waterme.plantism.data.PlantContract;
import com.waterme.plantism.data.PlantDbHelper;
import com.waterme.plantism.model.PlantIntro;
import com.waterme.plantism.utils.DatabaseUtils;


// needs a dbhelper to search
public class SearchResultActivity extends BaseActivity implements
            SearchView.OnQueryTextListener{

    private static final String TAG = "Search Activity";
    private static final int ID_INTRO_LOADER = 23;

    PlantDbHelper dbHelper;
    private SearchAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SearchView mSearchView;
    private int mPosition = RecyclerView.NO_POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new PlantDbHelper(this);

        DatabaseUtils utils = new DatabaseUtils();
        long id = 0;
        Log.d(TAG, "row id is " + id);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_search_result, contentFrameLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.ry_search_plant);
        mSearchView = (SearchView) findViewById(R.id.sv_plant);

        hidePlantIntro();
        Log.d(TAG, "query");
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM " + PlantContract.PlantEntry.TABLE_NAME, null);
        mAdapter = new SearchAdapter(this, cursor);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mSearchView.setOnQueryTextListener(this);

        /* get the search query from last search activity */
        Bundle bundle = getIntent().getExtras();
        String query = "";
        if (bundle != null) {
            query = bundle.getString("QUERY");
            /* set the search result and clear the focus */
            mSearchView.setQuery(query, true);
            mSearchView.clearFocus();
        }

        /* show the search results with query */
        showPlantIntro();
    }

    @Override
    public boolean onQueryTextChange(String query) {
        mAdapter.getFilter().filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mAdapter.getFilter().filter(query);
        return true;
    }

    private void showPlantIntro() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hidePlantIntro() {
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

}
