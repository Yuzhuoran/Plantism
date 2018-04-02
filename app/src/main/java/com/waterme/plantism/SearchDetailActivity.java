package com.waterme.plantism;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.waterme.plantism.R;
import com.waterme.plantism.data.PlantDbHelper;
import com.waterme.plantism.data.PlantImageContract;

public class SearchDetailActivity extends AppCompatActivity {

    private PlantDbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        dbHelper = new PlantDbHelper(this);
        /* get data from last intent */
        String species = "";
        String genus = "";
        String fullIntro = "";
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            species = bundle.getString("species");
            genus = bundle.getString("genus");
            fullIntro = bundle.getString("fullIntro");
        }

        /* get image name from database */
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                String.format("SELECT %s FROM %s WHERE %s =? ",
                        PlantImageContract.ImageEntry.COLUMN_IMAGE,
                        PlantImageContract.ImageEntry.TABLE_NAME,
                        PlantImageContract.ImageEntry.COLUMN_SPECIES),
                new String[] {species});
        cursor.close();

        /* set view content */
    }
}


