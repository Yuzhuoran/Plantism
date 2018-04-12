package com.waterme.plantism;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.waterme.plantism.R;
import com.waterme.plantism.model.MyTextView;
import com.waterme.plantism.model.Plant;

import java.util.HashMap;
import java.util.Map;

public class AddPlantActivity extends AppCompatActivity {
    private static final String TAG = "add plant activity";
    private String uid;
    private static final String PLANTID = "PLANTID";
    private static final String CATEGORY = "CATEGORY";
    private static final String USER_CHILD = "userInfo";
    private static final String SENSOR_CHILD = "sensorList";
    private static final String USER_SENSORS_CHILD = "sensors";
    private static final String USER_PLANTS_CHILD = "plants";
    private static final String USER_REALTIME_CHILD = "now";
    private FirebaseDatabase mFirebaseDatabase;
    private MyTextView text1;
    private MyTextView text2;
    private MyTextView button;
    private String sensor_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
        text1 = findViewById(R.id.tv_add_plant1);
        text2 = findViewById(R.id.tv_add_plant2);
        text2.setStyle("light");
        button = findViewById(R.id.button_add_plant);
        Bundle bundle = getIntent().getExtras();
        sensor_id = "";
        SharedPreferences sharedPre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        uid = sharedPre.getString("uid","");

        if (bundle != null) {
            sensor_id = bundle.getString("sensor_id");
            /* set the search result and clear the focus */
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sensor_id!=null) {
                    Intent intent = new Intent(AddPlantActivity.this, SearchActivity.class);
                    intent.putExtra("sensor_id", sensor_id);
                    startActivity(intent);
                }
            }
        });

    }
    //TODO


}
