package com.waterme.plantism;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;

public class SearchActivity extends AppCompatActivity implements
        SearchView.OnQueryTextListener{

    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchView = (SearchView) findViewById(R.id.sv_home);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setIconifiedByDefault(false);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
        intent.putExtra("QUERY", query);
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
