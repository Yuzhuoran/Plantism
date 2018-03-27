package com.waterme.plantism;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.widget.FrameLayout;

import com.waterme.plantism.data.PlantDbHelper;


// needs a dbhelper to search
public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener{

    PlantDbHelper dbHelper = new PlantDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFramLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_search, contentFramLayout);


    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // start new activity
        return false;
    }
}
