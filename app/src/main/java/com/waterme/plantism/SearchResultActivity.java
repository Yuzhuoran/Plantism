package com.waterme.plantism;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

    void initialize_database(PlantDbHelper dbHelper){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        DatabaseUtils utils = new DatabaseUtils();
        PlantIntro intro1 = new PlantIntro(
                "Donkey Burros Tails",
                "Succulents",
                "Burro’s tail is a heat and drought tolerant plant well suited for warm to temperate regions. The thick stems appear woven or plaited with leaves. The succulent is green to gray green or even blue green and may have a slight chalky look. ",
                "Sedum morganianum, or Burro’s tail, is a succulent perennial plant native to Mexico. ",
                "prefers bright, indirect sunlight. It will burn in strong, hot sun.\n" +
                        "\n" +
                        "watering once a month should be plenty, as the leaves hold quite a bit of moisture\n" +
                        "\n" +
                        "this plant should ideally be a lovely blue-green.\n",
                "donkey1",
                100,
                100

        );
        PlantIntro intro2 = new PlantIntro(
                "Afra",
                "Succulents",
                "Tree is not something you can grow at home ",
                "Tree is tall ",
                "prefers bright, indirect sunlight. It will not burn in strong, hot sun.\n" +
                        "\n" +
                        "Don't ever water it\n" +
                        "\n" +
                        "This plant should be planted in the yard\n",
                "afra",
                100,
                100

        );

        PlantIntro intro3 = new PlantIntro(
                "Desert Rose",
                "Succulents",
                "Tree is not something you can grow at home ",
                "Tree is tall ",
                "prefers bright, indirect sunlight. It will not burn in strong, hot sun.\n" +
                        "\n" +
                        "Don't ever water it\n" +
                        "\n" +
                        "This plant should be planted in the yard\n",
                "desert",
                100,
                100

        );

        PlantIntro intro4 = new PlantIntro(
                "Lety's Sevederia",
                "Succulents",
                "Tree is not something you can grow at home ",
                "Tree is tall ",
                "prefers bright, indirect sunlight. It will not burn in strong, hot sun.\n" +
                        "\n" +
                        "Don't ever water it\n" +
                        "\n" +
                        "This plant should be planted in the yard\n",
                "lety",
                100,
                100

        );

        /* Insert ContentValues into database and get first row ID back */
        long firstRowId1 = utils.insertOneRecord(database, intro1);
        long firstRowId2 = utils.insertOneRecord(database, intro2);
        long firstRowId3 = utils.insertOneRecord(database, intro3);
        long firstRowId4 = utils.insertOneRecord(database, intro4);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* Insert ContentValues into database and get first row ID back */
        super.onCreate(savedInstanceState);
        //this.deleteDatabase(PlantDbHelper.DATABASE_NAME);
        dbHelper = new PlantDbHelper(this);
        //initialize_database(dbHelper);
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
