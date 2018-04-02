package com.waterme.plantism;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waterme.plantism.R;
import com.waterme.plantism.data.PlantDbHelper;
import com.waterme.plantism.data.PlantImageContract;

import java.util.ArrayList;
import java.util.List;

public class SearchDetailActivity extends AppCompatActivity {
    private LinearLayout mGallery;
    private List<Integer> mImgIds = new ArrayList<Integer>();
    private LayoutInflater mInflater;
    private String species,genus,fullIntro,image;
    private PlantDbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_detail);
        mInflater = LayoutInflater.from(this);
        dbHelper = new PlantDbHelper(this);
        /* get data from last intent */
        species = "";
        genus = "";
        fullIntro = "";
        image="";
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            species = bundle.getString("species");
            genus = bundle.getString("genus");
            fullIntro = bundle.getString("fullIntro");
            image = bundle.getString("image");
            Log.d("receved image",image);
        }
        initData();
        initView();
        /* get image name from database */
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                String.format("SELECT %s FROM %s WHERE %s =? ",
                        PlantImageContract.ImageEntry.COLUMN_IMAGE,
                        PlantImageContract.ImageEntry.TABLE_NAME,
                        PlantImageContract.ImageEntry.COLUMN_SPECIES),
                new String[]{species});
        cursor.close();
    }

    private void initData()
    {
        for(int i=1;i<5;i++){
            String name=image+"_"+String.valueOf(i);
            Log.d("Image Name",name);
            Integer imageId = getResources().getIdentifier(image+"_"+String.valueOf(i),"drawable",getPackageName());
            if(image!=null) mImgIds.add(imageId);
        }
    }

    private void initView()
    {
        mGallery = (LinearLayout) findViewById(R.id.id_gallery);

        for (int i = 0; i < mImgIds.size(); i++)
        {

            View view = mInflater.inflate(R.layout.acitvitiy_plant_gallery_item, mGallery, false);
            ImageView img = (ImageView) view.findViewById(R.id.imageView_plant_gallery);
            img.setImageResource(mImgIds.get(i));
            mGallery.addView(view);
        }
    }
        /* set view content */

}


