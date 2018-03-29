package com.waterme.plantism.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.waterme.plantism.R;
import com.waterme.plantism.data.PlantContract;
import com.waterme.plantism.data.PlantDbHelper;
import com.waterme.plantism.model.PlantIntro;
import com.waterme.plantism.utils.DatabaseUtils;


// needs a dbhelper to search
public class SearchActivity extends BaseActivity implements
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
        PlantIntro intro = new PlantIntro(
                "Donkey Burros Tails",
                "Succulents",
                "Burro’s tail is a heat and drought tolerant plant well suited for warm to temperate regions. The thick stems appear woven or plaited with leaves. The succulent is green to gray green or even blue green and may have a slight chalky look. ",
                "Sedum morganianum, or Burro’s tail, is a succulent perennial plant native to Mexico. ",
                "prefers bright, indirect sunlight. It will burn in strong, hot sun.\n" +
                        "\n" +
                        "watering once a month should be plenty, as the leaves hold quite a bit of moisture\n" +
                        "\n" +
                        "this plant should ideally be a lovely blue-green.\n",
                "pitcture",
                100,
                100

        );
        PlantIntro intro2 = new PlantIntro(
                "6778",
                "Succulents",
                "Burro’s tail is a heat and drought tolerant plant well suited for warm to temperate regions. The thick stems appear woven or plaited with leaves. The succulent is green to gray green or even blue green and may have a slight chalky look. ",
                "Sedum morganianum, or Burro’s tail, is a succulent perennial plant native to Mexico. ",
                "prefers bright, indirect sunlight. It will burn in strong, hot sun.\n" +
                        "\n" +
                        "watering once a month should be plenty, as the leaves hold quite a bit of moisture\n" +
                        "\n" +
                        "this plant should ideally be a lovely blue-green.\n",
                "pitcture",
                100,
                100

        );
        dbHelper = new PlantDbHelper(this);
        DatabaseUtils utils = new DatabaseUtils();
        long id = 0;
        //id = utils.insertOneRecord(dbHelper.getWritableDatabase(), intro);
        //Log.d(TAG, "row id is " + id);
        //id = utils.insertOneRecord(dbHelper.getWritableDatabase(), intro2);
        Log.d(TAG, "row id is " + id);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_search, contentFrameLayout);

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
