package com.waterme.plantism;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.waterme.plantism.R;
import com.waterme.plantism.data.PlantDbHelper;
import com.waterme.plantism.data.PlantImageContract;
import com.waterme.plantism.model.MyTextView;
import com.waterme.plantism.model.Plant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchDetailActivity extends BaseActivity {
    private static final String TAG = "Add plant in detail";
    private String uid;
    private static final String PLANTID = "PLANTID";
    private static final String CATEGORY = "CATEGORY";
    private static final String USER_CHILD = "userInfo";
    private static final String SENSOR_CHILD = "sensorList";
    private static final String USER_SENSORS_CHILD = "sensors";
    private static final String USER_PLANTS_CHILD = "plants";
    private static final String USER_REALTIME_CHILD = "now";

    private FirebaseDatabase mFirebaseDatabase;

    private LinearLayout mGallery;
    private List<Integer> mImgIds = new ArrayList<Integer>();
    private LayoutInflater mInflater;
    private String species,genus,fullIntro,image;
    private PlantDbHelper dbHelper;
    private ImageView btnAddit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//      requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_search_detail);
        mInflater = LayoutInflater.from(this);
        dbHelper = new PlantDbHelper(this);
        btnAddit = findViewById(R.id.button_add_it);
        ((MyTextView) findViewById(R.id.tv_guide_genus)).setStyle("light");
        ((MyTextView) findViewById(R.id.tv_guide_intro)).setStyle("light");

        SharedPreferences sharedPre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        uid = sharedPre.getString("uid","");

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        /* get data from last intent */
        species = "";
        genus = "";
        fullIntro = "";
        image="";

        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            species = bundle.getString("species");
            genus = bundle.getString("genus");
            fullIntro = bundle.getString("fullIntro");
            image = bundle.getString("image");
            Log.d("receved image",image);
        }
        //setting intro according to db
        ((MyTextView) findViewById(R.id.tv_guide_genus)).setText(genus);
        ((MyTextView) findViewById(R.id.tv_guide_species)).setText(species);
        ((MyTextView) findViewById(R.id.tv_guide_intro)).setText(fullIntro);
        initData();
        initView();
        btnAddit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add the tuple<sensorid, plantid> to firebase
                if (bundle == null || bundle.getString("sensor") == null) {
                    throw new IllegalArgumentException("no sensor id!");
                }
                /* add a plant */
                addPlants(bundle.getString("species"), "new plant", bundle.getString("sensor"));
                Log.d(TAG, "add plant successful");
                Intent intent = new Intent(SearchDetailActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
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
    private void addPlants(String plantCategory, String plantMyName, String sensorId){
        Log.d(TAG, "Add plant test");
        final DatabaseReference dbRef = mFirebaseDatabase.getReference()
                .child(USER_CHILD)
                .child(uid).child(USER_PLANTS_CHILD);

        /* update plant child */
        Map<String, Object> update = new HashMap<>();
        Map<String, Object> history = new HashMap<>();
        history.put("history", null);
        update.put(plantMyName, new Plant(plantCategory, history, "*"));
        dbRef.updateChildren(update);

        /* update the now child */
        DatabaseReference dbNowRef = mFirebaseDatabase.getReference()
                .child(USER_CHILD)
                .child(uid)
                .child(USER_REALTIME_CHILD);
        update.clear();
        update.put(sensorId + "/plantMyname", plantMyName);
        update.put(sensorId + "/plantCategory", plantCategory);
        update.put(sensorId + "/order", Integer.valueOf(sensorId));

        dbNowRef.updateChildren(update);


    }


}


